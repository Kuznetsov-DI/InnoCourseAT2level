import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StudentTest {

    private Student student;
    private String name;
    private List<Integer> grades;

    @BeforeEach
    void setUp() {
        name = "Инокентий";
        student = new Student(name);
        grades = new ArrayList<>();
    }

    @Test
    @DisplayName("Метод получения имени работает корректно")
    void nameGetterIsCorrect() {
        String currentName = student.getName();

        Assertions.assertEquals(name, currentName);
    }

    @Test
    @DisplayName("Метод установки имени работает корректно")
    void nameSetterIsCorrect() {
        String newName = "Федор";

        student.setName(newName);

        Assertions.assertEquals(newName, student.getName());
        Assertions.assertNotEquals(name, student.getName());
    }

    @Test
    @DisplayName("Метод получения оценок работает корректно")
    void getGradesIsCorrect() {
        grades.add(2);
        grades.add(3);
        grades.add(4);
        for (int grade : grades) {
            student.addGrade(grade);
        }

        List<Integer> currentGrades = student.getGrades();

        Assertions.assertEquals(grades, currentGrades);
    }

    @ParameterizedTest
    @MethodSource("validGradeArguments")
    @DisplayName("Метод добавление оценки, при валидном значении")
    void setGradeWithValidArgument(Integer grade) {
        student.addGrade(grade);

        Assertions.assertTrue(student.getGrades().contains(grade));
    }

    @ParameterizedTest
    @MethodSource("notValidGradeArguments")
    @DisplayName("Метод добавление оценки, при значении не допустимом")
    void setGradeWithNotValidArgument(Integer grade) {
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () ->
                student.addGrade(grade));

        Assertions.assertEquals(String.format("%s is wrong grade", grade), thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка инкапсуляции списка оценок")
    void gradesEncapsulationCheck() {
        grades.add(2);
        grades.add(3);
        grades.add(4);
        for (int grade : grades) {
            student.addGrade(grade);
        }

        List<Integer> currentGradesBeforeAdd = student.getGrades();
        currentGradesBeforeAdd.add(5);

        List<Integer> currentGradesAfterAdd = student.getGrades();

        Assertions.assertEquals(grades, currentGradesAfterAdd);
    }

    @Test
    @DisplayName("Проверки метода equals класса Student")
    void equalsStudentCheck() {
        grades.add(2);
        grades.add(3);
        grades.add(4);
        for (int grade : grades) {
            student.addGrade(grade);
        }

        Student secondStudent = new Student(name);
        for (int grade : grades) {
            secondStudent.addGrade(grade);
        }

        Assertions.assertEquals(student, secondStudent);
    }

    @Test
    @DisplayName("Проверки метода hasCode класса Student")
    void hashCodeStudentCheck() {
        grades.add(2);
        grades.add(3);
        grades.add(4);
        for (int grade : grades) {
            student.addGrade(grade);
        }

        int expectedHash = 7;
        expectedHash = 13 * expectedHash + Objects.hashCode(student.getName());
        expectedHash = 13 * expectedHash + Objects.hashCode(student.getGrades());

        int actualHash = student.hashCode();


        Assertions.assertEquals(expectedHash, actualHash);
    }

    static Stream<Integer> validGradeArguments() {
        return Stream.of(2, 5);
    }

    static Stream<Integer> notValidGradeArguments() {
        return Stream.of(1, 6);
    }
}
