package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static final String BASE_URL = "https://api.trello.com/1";
    protected static final String BOARDS = "boards";
    protected static final String LISTS = "lists";
    protected static final String CARDS = "cards";

    protected static final String KEY = "5b4299b216614d1c281ee544177d5b12";
    protected static final String TOKEN = "6cdac238e8dfc8fc6b9bbd1d904291342cf4c99e8fe9a967ff8a3ebaac8e68d5";

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reSpec;

    @BeforeAll
    public static void beforeAll(){
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.setContentType(ContentType.JSON);

        reSpec = reqBuilder.build();
    }
}
