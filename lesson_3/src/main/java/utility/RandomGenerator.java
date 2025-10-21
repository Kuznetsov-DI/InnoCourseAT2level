package utility;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomGenerator {

    private final Faker faker = new Faker();

    public String getRandomName(){
        return faker.name().name();
    }

    public Integer getRandomMark(){
        return faker.number().numberBetween(1, 5);
    }
}
