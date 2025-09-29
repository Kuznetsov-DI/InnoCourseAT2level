import lombok.*;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Student {

    @Getter
    @Setter
    private String name;
    private final List<Integer> grades = new ArrayList<>();

    @Setter
    private CloseableHttpClient httpClient;

    public Student(String name) {
        this.name = name;
        this.httpClient = HttpClients.createDefault();
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    @SneakyThrows
    public void addGrade(int grade) {
        HttpGet request = new HttpGet("http://localhost:5352/checkGrade?grade=" + grade);
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        if (!Boolean.parseBoolean(EntityUtils.toString(entity))) {
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
        grades.add(grade);
    }

    @SneakyThrows
    public int raiting() {
        HttpGet request = new HttpGet("http://localhost:5352/educ?sum=" + grades.stream().mapToInt(x -> x).sum());
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return Integer.parseInt(EntityUtils.toString(entity));
    }
}