package shop_api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.qaaacademy.restassured.shop_api.models.Product;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class BasicProductAPIVerificationTest {
    private final String BASE_PATH = "http://localhost:3000/products";

    @Test
    public void shouldAddNewProduct() {
        given().body("{\n" +
                        "    \"description\": \"Blueberry\",\n" +
                        "    \"manufacturer\": 1,\n" +
                        "    \"price\": 21.7\n" +
                        "}")
                .when().post(BASE_PATH)
                .then().log().all().statusLine(containsString("OK"));
    }
    @Test
    public void shouldChangePriceOfProductBanana() {

    Double newPrice = 9.3;
        HashMap<String, Object> productData = new HashMap<>();
        productData.put("description", "Banana");
        productData.put("id", "2");
        productData.put("manufacturer", "1");
        productData.put("price", newPrice);
        given().contentType(ContentType.JSON)
                .body(productData)
                .when().put(BASE_PATH + "/1")
                .then().log().all();
    }

    @Test
    public void shouldContainProductPeach() {
        when().get(BASE_PATH + "/14")
                .then()
                .body("description", equalTo("Peach"));
    }

    @Test
    public void shouldContainProductStrawberry() {
        when().get(BASE_PATH + "/5")
                .then()
                .body("description", equalTo("Strawberry"));
    }

    @Test
    public void productListShouldContain() {
        given()
                .when().get(BASE_PATH)
                .then().assertThat().body("description", hasItems("Strawberry", "Peach"));
    }

    @Test
    public void shouldReturnProductAsHashMap() {
        String productId = "6";
        var result = given()
                .when().get(BASE_PATH + "/" + productId)
                .then().extract().body().jsonPath().getMap("", String.class, String.class);
        System.out.println(result.get("description"));
    }

    @Test
    public void verifyStrawberryPrice() {
        float strawberryPrice = 18.3f;
        when().get(BASE_PATH + "/5")
                .then()
                .body("price", equalTo(strawberryPrice));
    }
    @Test
    public void addNewProductCoffee() {
        given().body("{\n" +
                        "    \"description\": \"Roasted Coffee\",\n" +
                        "    \"manufacturer\": 1,\n" +
                        "    \"price\": 25.5\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when().post(BASE_PATH)
                .then().log().all().statusLine(containsString("OK"));

    }

    @Test
    public void extractedProductShouldHaveExpectedDescription() {
        String productId = "3";
        String expectedDescription = "Grapes";
        Product grapes = given().
                when().get(BASE_PATH + "/" + productId)
                .then().extract().body().as(Product.class);
        Assert.assertEquals(expectedDescription, grapes.getDescription());
    }

        @Test
        public void verifyProductID7() {
            String productId = "7";
            String expectedDescription = "Orange";
            float expectedPrice = 10.5f;
            Product orange = given().
                    when().get(BASE_PATH + "/" + productId)
                    .then().extract().body().as(Product.class);
            Assert.assertEquals(expectedDescription, orange.getDescription());
            Assert.assertEquals(expectedPrice, orange.getPrice());
    }
}
