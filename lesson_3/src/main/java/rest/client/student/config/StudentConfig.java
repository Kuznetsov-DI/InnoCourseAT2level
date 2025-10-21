package rest.client.student.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:student-config.properties"})
public interface StudentConfig extends Config {
    String baseUrl();
}
