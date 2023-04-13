import Courier.Courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class StepLogin {
    static private String endPointCreate = "/api/v1/courier";
    static private String endPointLogin = "/api/v1/courier/login";
    static private String endPointDelete = "/api/v1/courier/";

    @Step("login curier")

    public ValidatableResponse loginCurier(String password, String login) {
        Courier loginCourier = new Courier(password, login);
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(endPointLogin)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }

    @Step
    public ValidatableResponse setUpAndCreate(String firstName, String password, String login) {
        Courier courierCreate = new Courier(firstName, password, login);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(endPointCreate)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Step

    public ValidatableResponse withoutPassword(String password, String login) {
        Courier courierCreate = new Courier("", login);
        return given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step
    public ValidatableResponse withWrongPassword(String password, String login) {
        Courier courierCreate = new Courier(password + "2151515", login);
        return given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step
    public ValidatableResponse withWrongLogin(String password, String login) {
        Courier courierCreate = new Courier(password, login + "cdhbch");
        return given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @Step
    public ValidatableResponse withWrongLoginAndPassord(String password, String login){
        Courier courierCreate = new Courier(password + "nnnnnbh", login + "cdhbch");
        return given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}