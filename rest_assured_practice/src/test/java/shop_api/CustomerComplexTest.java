package shop_api;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.qaaacademy.restassured.shop_api.models.Customer;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CustomerComplexTest {
    private final String basePath = "http://localhost:3000/customers";
    private final String SEPARATOR = "/";
    private String addItemToCartPath = "http://localhost:3000/customers/{customerId}/cart" +
            "?quantity={quantity}&productId={productId}";


    @Test

    public void customerShoppingCartItemsListShouldHaveLength() {
        String customerId = "3";
        emptyCartForCustomer(customerId);
        putProductToCartForCustomerPathParams(customerId,4,5);
        Customer customer = when().get(basePath + SEPARATOR + customerId).as(Customer.class);
        Assert.assertEquals(customer.getShoppingCart().getItems().length,1);
    }

    private void emptyCartForCustomer(String customerId) {
        when().delete(basePath + SEPARATOR + customerId + SEPARATOR + "cart/empty")
                .then().statusCode(200);
    }

    private void putProductToCartForCustomer(String customerId, int productId, int productQuantity) {
        String query = basePath + SEPARATOR + customerId + SEPARATOR + "cart";
        given().queryParam("quantity",productQuantity)
                .queryParam("productId",productId)
                .when().put(query).then().log().all();
    }

    private void putProductToCartForCustomerPathParams(String customerId, int productId, int productQuantity) {
given().pathParam("customerId",customerId)
        .pathParam("quantity",productQuantity)
        .pathParam("productId",productId)
        .when().put(addItemToCartPath).then().log().all();
    }
}
