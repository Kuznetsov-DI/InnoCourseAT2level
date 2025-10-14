import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import rest.model.StudentModel;
import utility.Mapper;
import utility.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PostStudentTest extends BaseTest {

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
    @DisplayName("Проверка, что метод post student создает студента без указанного id")
    void postStudentWithoutId() {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));

        var response = studentClient.postStudent(student);
        Assertions.assertEquals(201, response.statusCode());

        id = response.as(Integer.class);

        var createdStudent = studentClient.getStudent(id).as(StudentModel.class);
        Assertions.assertEquals(student, createdStudent);
    }

    @Test
    @DisplayName("Проверка, что метод post student создает студента с указанным id")
    void postStudentWithId() {
        var student = new StudentModel();
        id = 1000;
        student.setId(id);
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));

        var response = studentClient.postStudent(student);
        Assertions.assertEquals(201, response.statusCode());

        var createdStudent = studentClient.getStudent(id).as(StudentModel.class);
        Assertions.assertEquals(student, createdStudent);
    }

    @Test
    @DisplayName("Проверка, что метод post student обновляет информацию о студенте")
    void postStudentUpdate() {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));

        var response = studentClient.postStudent(student);
        Assertions.assertEquals(201, response.statusCode());

        id = response.as(Integer.class);

        var studentUpdate = new StudentModel();
        studentUpdate.setId(id);
        studentUpdate.setName(RandomGenerator.getRandomName());
        studentUpdate.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));

        var responseAfterUpdate = studentClient.postStudent(studentUpdate);
        Assertions.assertEquals(201, responseAfterUpdate.statusCode());

        var updatedStudent = studentClient.getStudent(id).as(StudentModel.class);
        Assertions.assertEquals(studentUpdate, updatedStudent);
        Assertions.assertNotEquals(student, updatedStudent);
    }

    @ParameterizedTest
    @MethodSource("validGradeArguments")
    @DisplayName("Проверка, что метод post student создает студента с заполненным/пустым/null полем оценок")
    void postStudentWithDifferentMarks(List<Integer> marks) {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(marks);

        var response = studentClient.postStudent(student);
        Assertions.assertEquals(201, response.statusCode());
        id = response.as(Integer.class);
    }

    @ParameterizedTest
    @MethodSource("notValidArguments")
    @DisplayName("Проверка, что метод post student возвращает ошибку при нарушении валидации полей")
    void postStudentValidArguments(String field, String value) {
        var student = new StudentModel();
        student.setName(RandomGenerator.getRandomName());
        student.setMarks(List.of(RandomGenerator.getRandomMark(), RandomGenerator.getRandomMark()));
        var studentMap = Mapper.convertObjectToMap(student);
        studentMap.put(field, value);

        var response = studentClient.postStudent(studentMap);
        Assertions.assertEquals(400, response.statusCode());
    }

    static Stream<List<Integer>> validGradeArguments() {
        return Stream.of(
                List.of(RandomGenerator.getRandomMark()),
                new ArrayList<>(),
                null
        );
    }

    static Stream<Arguments> notValidArguments() {
        return Stream.of(
                Arguments.of("name", null),
                Arguments.of("id", "ignat"),
                Arguments.of("marks", List.of(0).toString()),
                Arguments.of("marks", List.of(-1).toString())
        );
    }
}
