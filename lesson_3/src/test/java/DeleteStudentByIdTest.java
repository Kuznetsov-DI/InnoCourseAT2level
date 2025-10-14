import org.junit.jupiter.api.*;
import rest.model.StudentModel;
import utility.RandomGenerator;

import java.util.List;

public class DeleteStudentByIdTest extends BaseTest{

    @Test
    @DisplayName("Проверка, что метод delete student/{id} удаляет студента")
    void deleteStudentIfExist() {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));

        var id = studentClient.postStudent(student).body().as(Integer.class);

        var response = studentClient.deleteStudent(id);
        Assertions.assertEquals(200, response.statusCode());

        var getResponse = studentClient.getStudent(id);
        Assertions.assertEquals(404, getResponse.statusCode());
    }

    @Test
    @DisplayName("Проверка, что метод delete student/{id} вернет ошибку, если студента не существует")
    void deleteStudentIfNotExist() {
        var response = studentClient.deleteStudent(-1);
        Assertions.assertEquals(404, response.statusCode());
    }
}
