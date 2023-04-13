import ConstantUrl.Url;
import Order.Order;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class StepOrder {
    protected final String POST_CREATE_ORDER = "/api/v1/orders";
    protected final String GET_ORDER = "/api/v1/orders";
    protected final String PUT_ORDER_CANCEL = "/api/v1/orders/cancel";

    @Step("Create new order")

    public ValidatableResponse createOrder(Order order) {
                return given().log().all()
                        .contentType(ContentType.JSON)
                        .baseUri(Url.urlBase)
                        .body(order)
                        .when()
                        .post(POST_CREATE_ORDER)
                        .then();
    }
    @Step("Get order list")

    public ValidatableResponse getOrderList() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Url.urlBase)
                .when().log().all()
                .get(GET_ORDER)
                .then();
    }

    @Step("Delete existing order by track")

        public ValidatableResponse cancelOrder(int track) {
            return given().log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(Url.urlBase)
                    .body(track)
                    .when()
                    .put(PUT_ORDER_CANCEL)
                    .then();
        }




}
