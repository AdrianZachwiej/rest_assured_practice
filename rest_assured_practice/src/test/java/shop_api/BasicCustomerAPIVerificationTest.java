package shop_api;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class BasicCustomerAPIVerificationTest {

    private final String basePath = "http://localhost:3000/customers";

    @Test
    public void shouldReturnCustomersList() {
        Header h1 = new Header("h1", "v1");
        given().header(h1).log().headers().
        when().get(basePath)
                .then().statusCode(200).contentType(ContentType.JSON);
    }

    @Test
    public void shouldReturnInfoForTheCustomerID2() {
        Response resp = given().cookie("cookie", "key1/value,key2/value2").log().cookies().
        when().get(basePath + "/2")
                .then().extract().response();
        System.out.println(resp.getBody().prettyPrint());
        System.out.println(resp.statusCode());
        System.out.println(resp.statusLine());
        System.out.println(resp.getHeaders().asList());
        System.out.println(resp.getCookies());
                //.statusCode(200)
                //.body("person.email", equalTo("john.doe@customDomain.com"));

    }

    @Test
    public void verifyCityForID3() {
        String expectedCity = "Auckland";
        when().get(basePath)
                .then().body("find {it.id == '3'}.address.city", equalTo(expectedCity) );    }
}
