package shop_api.api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.qaaacademy.restassured.shop_api.apis.CustomerAPI;
import pl.qaaacademy.restassured.shop_api.environment.Environment;
import pl.qaaacademy.restassured.shop_api.environment.EnvironmentManager;
import pl.qaaacademy.restassured.shop_api.models.Customer;

import java.util.List;

public class BasicCustomerVerificationsWithAPIs {


    private CustomerAPI customerAPI;

    @BeforeClass
    public void setUp() {
        String env = System.getProperty("env");
        Environment currentEnv = EnvironmentManager.getEnvironment(env);
        customerAPI = CustomerAPI.get(currentEnv);

    }

    @Test
    public void shouldGetListOfExistingCustomers() {
        List<Customer> customers = customerAPI.getAllCustomers();
        Assert.assertTrue(customers.size() > 0,
                "Customer list size is 0. Check logs for additional details");
    }

    @Test
    public void shouldUpdateCustomerEmailWithNewOne() {
        String customerId = "3";
        String newEmail = "alex.kowalsky@changed.yes";
        Customer alex = customerAPI.updateEmailAddressForCustomer(customerId, newEmail);
        Assert.assertEquals(newEmail, alex.getPerson().getEmail(),"Alex's email not updated. Check logs");

    }

    @Test
    public void checkIfItemWasAddedToCart() {
        String itemId = "3";
        String quantity = "3";
        String customerId = "4";
        customerAPI.addItemToCartForCustomer(customerId, itemId, quantity);
        Customer dima = customerAPI.getCustomerById(customerId);
        Assert.assertTrue(dima.getShoppingCart().getItems().length > 0);

    }
}
