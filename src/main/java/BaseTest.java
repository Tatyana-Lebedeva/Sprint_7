import ConstantUrl.Url;
import io.restassured.RestAssured;

public class BaseTest {
    public void openUrl(){
        RestAssured.baseURI = Url.urlBase ;
    }

}
