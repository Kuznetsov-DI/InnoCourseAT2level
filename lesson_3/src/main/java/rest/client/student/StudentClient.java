package rest.client.student;

import rest.client.student.config.StudentConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import rest.client.BaseClient;
import rest.model.StudentModel;
import rest.endpoints.Student;

import java.util.Map;

public class StudentClient extends BaseClient {

    private final static StudentConfig config = ConfigFactory.create(StudentConfig.class, System.getenv());
    private final static String ID_FIELD = "id";

    public StudentClient() {
        super(config.baseUrl());
    }

    public Response getStudent(Integer id) {
        return RestAssured.given(getSpecification())
                .when()
                .pathParam(ID_FIELD, id)
                .get(Student.STUDENT_BY_ID)
                .then().extract().response();
    }

    public Response postStudent(StudentModel student) {
        return RestAssured.given(getSpecification())
                .when()
                .body(student)
                .post(Student.STUDENT)
                .then().extract().response();
    }

    public Response postStudent(Map<String, String> student) {
        return RestAssured.given(getSpecification())
                .when()
                .body(student)
                .post(Student.STUDENT)
                .then().extract().response();
    }

    public Response deleteStudent(Integer id) {
        return RestAssured.given(getSpecification())
                .when()
                .pathParam(ID_FIELD, id)
                .delete(Student.STUDENT_BY_ID)
                .then().extract().response();
    }

    public Response topStudent() {
        return RestAssured.given(getSpecification())
                .when()
                .get(Student.TOP_STUDENT)
                .then().extract().response();
    }
}
