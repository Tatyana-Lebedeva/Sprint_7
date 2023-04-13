import Courier.Courier;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.apache.http.client.methods.RequestBuilder.delete;

public class LoginCourierTest extends BaseTest {
    StepLogin objStepCreate = new StepLogin();
    Faker faker = new Faker();
    private String login = faker.internet().emailAddress();
    private String password = faker.internet().password();
    private String firstName = faker.name().firstName();
    static private String endPointCreate = "/api/v1/courier";
    static private String endPointLogin = "/api/v1/courier/login";
    static private String endPointDelete = "/api/v1/courier/";

    @Before

    public void setUp(){
        openUrl();
        objStepCreate.setUpAndCreate(firstName,password,login);
    }


     @Test
     @DisplayName("Курьер может авторизоваться")
     public void loginCourierAndCheckResponse() {
        objStepCreate.loginCurier(password, login);
                   }

    @Test
    @DisplayName("Для авторизации нужно передать все поля. Нет пароля.")
    public void loginCourierWithoutPassword() {
    objStepCreate.withoutPassword("",login);
    }
    @Test
    @DisplayName("Ошибка, если неправильный пароль")
    public void loginCourierWithWrongPassword() {
        objStepCreate.withWrongPassword(password, login);
            }
    @Test
    @DisplayName("Ошибка, если неправильный логин")

    public void loginCourierWithWrongLogin() {
        objStepCreate.withWrongLogin(password,login);
    }

    @Test
    @DisplayName("Ошибка, если неправильный логин и пароль")

    public void loginCourierWithWrongLoginAndPassord() {
        objStepCreate.withWrongLoginAndPassord(password,login);
    }
        @After
    public void tearDown() {
        Courier courierDelete  = new Courier(password,login);
// Удаляем курьера после тестов:
        String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierDelete)
                .when()
                .post(endPointLogin)
                .asString();

        JsonPath jsonPath = new JsonPath(response);
        String userId = jsonPath.getString("id");
        delete(endPointDelete + userId);
    }
}
