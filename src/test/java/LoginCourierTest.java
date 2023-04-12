import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.client.methods.RequestBuilder.delete;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    static private String login = RandomStringUtils.randomAlphabetic(10);
    static private String password = "11112222";
    static private String firstName = "Vasili";
    static private String endPointCreate = "/api/v1/courier";
    static private String endPointLogin = "/api/v1/courier/login";
    static private String endPointDelete = "/api/v1/courier/";

    @Before

    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        Courier courierCreate = new Courier(firstName,password,login);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(endPointCreate)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok",equalTo(true));
    }

     @Test
     @DisplayName("Курьер может авторизоваться")
    public void loginCourierAndCheckResponse() {
        Courier loginCourier = new Courier(password,login);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(endPointLogin)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("id",notNullValue());
                   }

    @Test
    @DisplayName("Для авторизации нужно передать все поля. Нет пароля.")
    public void loginCourierWithoutPassword(){
        Courier courierCreate = new Courier("",login);
        given()

                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));


    }
    @Test
    @DisplayName("Ошибка, если неправильный пароль")

    public void loginCourierWithWrongPassword() {
        Courier courierCreate = new Courier(password+"2151515", login);
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Ошибка, если неправильный логин")

    public void loginCourierWithWrongLogin() {
        Courier courierCreate = new Courier(password, login + "cdhbch");
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка, если неправильный логин и пароль")

    public void loginCourierWithWrongLoginAndPassord() {
        Courier courierCreate = new Courier(password + "nnnnnbh", login + "cdhbch");
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

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
