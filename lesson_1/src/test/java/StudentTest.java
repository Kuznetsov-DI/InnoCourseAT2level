import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Student student;
    private String name;
    private List<Integer> grades = new ArrayList<>();

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

        assertEquals(name, currentName);
    }

    @Test
    @DisplayName("Метод установки имени работает корректно")
    void nameSetterIsCorrect() {
        String newName = "Федор";

        student.setName(newName);

        assertEquals(newName, student.getName());
        assertNotEquals(name, student.getName());
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

        assertEquals(grades, currentGrades);
    }

    @Test
    @DisplayName("Метод добавление оценки, при валидном значении")
    void setGradeWithValidArgument() {
        student.addGrade(2);
        student.addGrade(5);

        assertTrue(student.getGrades().contains(2));
        assertTrue(student.getGrades().contains(5));
    }

    @Test
    @DisplayName("Метод добавление оценки, при значении меньше допустимого")
    void setGradeWithLessArgument() {
        var thrown = assertThrows(IllegalArgumentException.class, () ->
                student.addGrade(1));

        assertEquals("1 is wrong grade", thrown.getMessage());
    }

    @Test
    @DisplayName("Метод добавление оценки, при значении больше допустимого")
    void setGradeWithMoreArgument() {
        var thrown = assertThrows(IllegalArgumentException.class, () ->
                student.addGrade(6));

        assertEquals("6 is wrong grade", thrown.getMessage());
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

        assertEquals(grades, currentGradesAfterAdd);
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

        assertEquals(student, secondStudent);
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


        assertEquals(expectedHash, actualHash);
    }
}
