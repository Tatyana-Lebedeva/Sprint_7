import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class ListOrderTest {
    private StepOrder step= new StepOrder();
    private OrderAsserts check= new OrderAsserts();
 @Test
 public void checkBody(){
     ValidatableResponse response = step.getOrderList();
     check.checkBodyList(response);
 }




}
