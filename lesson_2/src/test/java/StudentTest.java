import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

public class StudentTest {

    private Student student;
    private List<Integer> grades;
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;

    @BeforeEach
    void setUp() {
        String name = "Инокентий";
        student = new Student(name);
        grades = new ArrayList<>();
        httpClient = Mockito.mock(CloseableHttpClient.class);
        student.setHttpClient(httpClient);
        response = CloseableHttpResponse.adapt(new BasicClassicHttpResponse(200, "OK"));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("validGradeArguments")
    @DisplayName("Метод добавление оценки, при валидном значении")
    void setGradeWithValidArgument(Integer grade) {
        boolean responseValue = true;
        String responseBody = String.valueOf(responseValue);
        var entity = new StringEntity(responseBody, ContentType.TEXT_PLAIN);
        response.setEntity(entity);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(response);

        student.addGrade(grade);

        Assertions.assertTrue(student.getGrades().contains(grade));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("notValidGradeArguments")
    @DisplayName("Метод добавление оценки, при значении не допустимом")
    void setGradeWithNotValidArgument(Integer grade) {
        boolean responseValue = false;
        String responseBody = String.valueOf(responseValue);
        var entity = new StringEntity(responseBody, ContentType.TEXT_PLAIN);
        response.setEntity(entity);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(response);

        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () ->
                student.addGrade(grade));

        Assertions.assertEquals(String.format("%s is wrong grade", grade), thrown.getMessage());
    }

    @Test
    @SneakyThrows
    @DisplayName("Проверка инкапсуляции списка оценок")
    void gradesEncapsulationCheck() {
        boolean responseValue = true;
        String responseBody = String.valueOf(responseValue);
        var entity = new StringEntity(responseBody, ContentType.TEXT_PLAIN);
        response.setEntity(entity);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(response);

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

    static Stream<Integer> validGradeArguments() {
        return Stream.of(2, 5);
    }

    static Stream<Integer> notValidGradeArguments() {
        return Stream.of(1, 6);
    }
}
