import org.junit.jupiter.api.*;
import rest.model.StudentModel;
import utility.Mapper;
import utility.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class GetTopStudentTest extends BaseTest {

    private List<Integer> ids;

    @BeforeEach
    void init() {
        ids = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        if (!ids.isEmpty()) {
            ids.forEach(studentClient::deleteStudent);
        }
    }

    @Test
    @DisplayName("Проверка получения студента с самой высокой средней оценкой get topStudent, если в БД более одного студента с разным средним баллом")
    void getTopStudentIfStudentsHaveDifferentAverageMarks() {
        var firstStudent = new StudentModel();
        firstStudent.setName(RandomGenerator.getRandomName());
        firstStudent.setMarks(List.of(3, 4));

        var createdFirstStudentId = studentClient.postStudent(firstStudent).body().as(Integer.class);
        ids.add(createdFirstStudentId);

        var secondStudent = new StudentModel();
        secondStudent.setName(RandomGenerator.getRandomName());
        secondStudent.setMarks(List.of(3, 5));

        var createdSecondStudentId = studentClient.postStudent(secondStudent).body().as(Integer.class);
        ids.add(createdSecondStudentId);

        var response = studentClient.topStudent();
        Assertions.assertEquals(200, response.statusCode());

        var topStudent = Mapper.convertStringToList(response.body().asString(), StudentModel.class).get(0);

        Assertions.assertEquals(secondStudent, topStudent);
        Assertions.assertEquals(createdSecondStudentId, topStudent.getId());
        Assertions.assertNotEquals(firstStudent, topStudent);
        Assertions.assertNotEquals(createdFirstStudentId, topStudent.getId());
    }

    @Test
    @DisplayName("Проверка получения студента с самой высокой средней оценкой get topStudent, если в БД более одного студента с одинаковым средним баллом и количеством оценок")
    void getTopStudentHaveSameAverageMarksAndMarksCount() {
        var firstStudent = new StudentModel();
        firstStudent.setName(RandomGenerator.getRandomName());
        firstStudent.setMarks(List.of(3, 4));

        var createdFirstStudentId = studentClient.postStudent(firstStudent).body().as(Integer.class);
        ids.add(createdFirstStudentId);

        var secondStudent = new StudentModel();
        secondStudent.setName(RandomGenerator.getRandomName());
        secondStudent.setMarks(List.of(4, 3));

        var createdSecondStudentId = studentClient.postStudent(secondStudent).body().as(Integer.class);
        ids.add(createdSecondStudentId);

        var response = studentClient.topStudent();
        Assertions.assertEquals(200, response.statusCode());

        var topStudents = Mapper.convertStringToList(response.body().asString(), StudentModel.class);

        Assertions.assertTrue(topStudents.contains(firstStudent));
        Assertions.assertTrue(topStudents.contains(secondStudent));
    }

    @Test
    @DisplayName("Проверка получения студента с самой высокой средней оценкой get topStudent, если в БД более одного студента с одинаковым средним баллом и разным количеством оценок")
    void getTopStudentHaveSameAverageMarksAndDifferentMarksCount() {
        var firstStudent = new StudentModel();
        firstStudent.setName(RandomGenerator.getRandomName());
        firstStudent.setMarks(List.of(3, 3));

        var createdFirstStudentId = studentClient.postStudent(firstStudent).body().as(Integer.class);
        ids.add(createdFirstStudentId);

        var secondStudent = new StudentModel();
        secondStudent.setName(RandomGenerator.getRandomName());
        secondStudent.setMarks(List.of(2, 3, 4));

        var createdSecondStudentId = studentClient.postStudent(secondStudent).body().as(Integer.class);
        ids.add(createdSecondStudentId);

        var response = studentClient.topStudent();
        Assertions.assertEquals(200, response.statusCode());

        var topStudent = Mapper.convertStringToList(response.body().asString(), StudentModel.class).get(0);

        Assertions.assertEquals(secondStudent, topStudent);
        Assertions.assertEquals(createdSecondStudentId, topStudent.getId());
        Assertions.assertNotEquals(firstStudent, topStudent);
        Assertions.assertNotEquals(createdFirstStudentId, topStudent.getId());
    }

    @Test
    @DisplayName("Проверка получения студента с самой высокой средней оценкой get topStudent, если в БД нет студентов")
    void getTopStudentIfNoHaveStudentsInDb() {
        var response = studentClient.topStudent();
        Assertions.assertEquals(200, response.statusCode());

        Assertions.assertTrue(response.body().asString().isEmpty());
    }

    @Test
    @DisplayName("Проверка получения студента с самой высокой средней оценкой get topStudent, если в БД нет студентов с оценками")
    void getTopStudentIfNoHaveStudentsWithMarksInDb() {
        var response = studentClient.topStudent();
        Assertions.assertEquals(200, response.statusCode());

        Assertions.assertTrue(response.body().asString().isEmpty());
    }
}
