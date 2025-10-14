import org.junit.jupiter.api.*;
import rest.model.StudentModel;
import utility.RandomGenerator;

import java.util.List;

public class GetStudentByIdTest extends BaseTest {

    private Integer id;

    @BeforeEach
    void init() {
        id = null;
    }

    @AfterEach
    void tearDown() {
        if (id != null) {
            studentClient.deleteStudent(id);
        }
    }

    @Test
    @DisplayName("Проверка, что метод get student/{id} возвращает информацию о студенте с оценками")
    void getStudentIfExistWithMarks() {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));

        id = studentClient.postStudent(student).body().as(Integer.class);

        var response = studentClient.getStudent(id);
        Assertions.assertEquals(200, response.statusCode());

        var receivedStudent = response.body().as(StudentModel.class);
        Assertions.assertEquals(student, receivedStudent);
    }

    @Test
    @DisplayName("Проверка, что метод get student/{id} возвращает информацию о студенте без оценок")
    void getStudentIfExistWithoutMarks() {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());

        id = studentClient.postStudent(student).body().as(Integer.class);

        var response = studentClient.getStudent(id);
        Assertions.assertEquals(200, response.statusCode());

        var receivedStudent = response.body().as(StudentModel.class);
        Assertions.assertTrue(receivedStudent.getMarks().isEmpty());
    }

    @Test
    @DisplayName("Проверка, что метод get student/{id} вернет ошибку, если студента не существует")
    void getStudentIfNotExist() {
        var response = studentClient.getStudent(-1);
        Assertions.assertEquals(404, response.statusCode());
    }
}
