package pl.qaaacademy.restassured.shop_api.apis;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pl.qaaacademy.restassured.shop_api.environment.Environment;
import pl.qaaacademy.restassured.shop_api.models.Customer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CustomerAPI {

    public static final String SEPARATOR = "/";
    private final String HOST;
    private final Environment env;
    private RequestSpecification reqSpec;

    private final String CUSTOMERS = "/customers";
    private final String CHANGE_EMAIL_ENDPOINT = "/email";

    private CustomerAPI(Environment env) {
        this.env = env;
        HOST = env.getHost();
    }



    public static CustomerAPI get(Environment env) {
        return new CustomerAPI(env);
    }



    public List<Customer> getAllCustomers() {
        String query = HOST + CUSTOMERS;
        return when().get(query)
                .then().log().body().
                extract().body().jsonPath().getList("", Customer.class);
    }

    private void requestSetUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder() ;
            builder.setAccept(ContentType.JSON);
        builder.addCookie("cookie1", "key1=value1");
        builder.addHeader("usefulHeader", "veryUseful");

    }

    public Customer updateEmailAddressForCustomer(String customerId, String newEmail) {
        String address = HOST + CUSTOMERS + SEPARATOR + customerId + CHANGE_EMAIL_ENDPOINT;
        Customer alex = given().spec(reqSpec)
                .queryParam("email", newEmail)
                .when().patch(address).then().extract().body().as(Customer.class);

        return alex;
    }

    public void addItemToCartForCustomer(String customerId, String itemId, String quantity) {
        String endPointForAddingItem = HOST + CUSTOMERS + SEPARATOR + customerId + SEPARATOR + "cart";
        given().queryParam("quantity", quantity) .and().queryParam("productId", itemId).put(endPointForAddingItem).then().log().body();
    }

    public Customer getCustomerById(String customerId) {
        return when().get(HOST + SEPARATOR + CUSTOMERS + SEPARATOR + customerId)
                .then().extract().body().as(Customer.class);
    }
}
