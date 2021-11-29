package board;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class BoardTest extends BaseTest {

    @Test
    public void createNewBoard(){

        Response response = given()
                .spec(reSpec)
                .queryParam("name", "My second board")
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
//        Assertions.assertEquals("My second board", jsonPath.get("name"));
        assertThat(jsonPath.getString("name")).isEqualTo("My second board");

        String boardId = jsonPath.get("id");

        given()
                .spec(reSpec)
                .when()
                .delete(BASE_URL + "/" + BOARDS + "/" + boardId)
                .then()
                .statusCode(200);
    }

    @Test
    public void createBoardWithEmptyBoardName(){

        Response response = given()
                .spec(reSpec)
                .queryParam("name", "")
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(400)
                .extract()
                .response();

    }

    @Test
    public void createBoardWhitoutDefalutLists(){

        Response response = given()
                .spec(reSpec)
                .queryParam("name","Board without Board list")
                .queryParam("defaultLists", false)
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
//        Assertions.assertEquals("Board without Board list", jsonPath.get("name"));
        assertThat(jsonPath.getString("name")).isEqualTo("Board without Board list");

        String boardId = jsonPath.get("id");

        Response responseGet = given()
                .spec(reSpec)
                .when()
                .get(BASE_URL + "/" + BOARDS + "/" + boardId + "/" + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();
        List<String> idLists = jsonGet.getList("id");
//        Assertions.assertEquals(0, idLists.size());
        assertThat(idLists).hasSize(0);

        given()
                .spec(reSpec)
                .when()
                .delete(BASE_URL + "/" + BOARDS + "/" + boardId)
                .then()
                .statusCode(200);

    }

    @Test
    public void createBoardWithDefaultLists(){

        Response response = given()
                .spec(reSpec)
                .queryParam("name","Board with Board list")
                .queryParam("defaultLists", true)
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
//        Assertions.assertEquals("Board with Board list", jsonPath.get("name"));
        assertThat(jsonPath.getString("name")).isEqualTo("Board with Board list");

        String boardId = jsonPath.get("id");

        Response responseGet = given()
                .spec(reSpec)
                .when()
                .get(BASE_URL + "/" + BOARDS + "/" + boardId + "/" + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();


        JsonPath jsonGet = responseGet.jsonPath();
        List<String> nameLists = jsonGet.getList("name");
//        Assertions.assertEquals("Do zrobienia", nameLists.get(0));
//        Assertions.assertEquals("W trakcie", nameLists.get(1));
//        Assertions.assertEquals("Zrobione", nameLists.get(2));

        assertThat(nameLists).hasSize(3).contains("Do zrobienia","W trakcie","Zrobione");

        given()
                .spec(reSpec)
                .when()
                .delete(BASE_URL + "/" + BOARDS + "/" + boardId)
                .then()
                .statusCode(200);

    }


}
