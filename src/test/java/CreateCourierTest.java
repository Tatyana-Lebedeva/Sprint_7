import Courier.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.client.methods.RequestBuilder.delete;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseTest {
    static private String login = RandomStringUtils.randomAlphabetic(8);
    static private String PEN_CREATE = "/api/v1/courier";
    static private String PEN_LOGIN = "/api/v1/courier/login";
    static private String PEN_DELETE = "/api/v1/courier/";
    static private String firstname = "vesna";
    static private String password = "kartoshka";

    @Before

    public void setUp(){
        openUrl(); }

    @Test
    @DisplayName("Создание нового курьера с правильными данными")
    public void createNewCourierAndCheckResponse(){
        Courier courierCreate = new Courier(firstname,password,login);
                given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(PEN_CREATE)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok",equalTo(true));

    }
    @Test
    @DisplayName("Создание нового курьера без обязательного поля firstName")
    public void createCourierWithoutFirstName(){
        Courier courierCreate = new Courier(password,login);
        given()

                .body(courierCreate)
                .when()
                .post(PEN_CREATE)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание двух одиннаковых курьеров")
    public void createCourierCheckResponse(){
        Courier courierCreate = new Courier(firstname,password,login);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(PEN_CREATE)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok",equalTo(true));
       // проверяем то, что нельзя создать двух одинаковых курьеров

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(PEN_CREATE)
                .then().assertThat().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
        public void tearDown (){
        Courier courierDelete = new Courier(firstname,password,login );
        String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierDelete)
                .when()
                .post(PEN_LOGIN)
                .asString();
        JsonPath jsonPath = new JsonPath(response);
        String userId = jsonPath.getString("id");
        delete(PEN_DELETE + userId);
    }


        }
