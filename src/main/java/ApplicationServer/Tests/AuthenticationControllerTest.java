package ApplicationServer.Tests;

import ApplicationServer.Controllers.ControllerConfiguration;
import ApplicationServer.Model.ClientModels.UserRegisterClient;
import ApplicationServer.Model.DataLayerModels.UserDataLayer;
import ApplicationServer.Model.RegisterSocketProtocol.RegisterClient;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest extends ControllerConfiguration {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Start Business Tier
        //Start the Data Tier
    }

    @org.junit.jupiter.api.Test
    void register() {
        String businessTierUrl = "http://" + "localhost" + ":8081/";
        //Models from client
        UserRegisterClient userRegisterClient1 = new UserRegisterClient("userTestJunit", "password", "TestName", "TestLastName", "2019-12-16", "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient2 = new UserRegisterClient(null, "password", "TestName", "TestLastName", "birthday", "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient3 = new UserRegisterClient("userTestJunit2", null, "TestName", "TestLastName", "birthday", "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient4 = new UserRegisterClient("userTestJunit4", "password", null, "TestLastName", "birthday", "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient5 = new UserRegisterClient("userTestJunit5", "password", "TestName", null, "birthday", "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient6 = new UserRegisterClient("userTestJunit6", "password", "TestName", "TestLastName", null, "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient7 = new UserRegisterClient("userTestJunit7", "password", "TestName", "TestLastName", "birthday", null, "profilePicture");
        UserRegisterClient userRegisterClient8 = new UserRegisterClient("userTestJunit8", "password", "TestName", "TestLastName", "birthday", "2019-12-16", null);
        UserRegisterClient userRegisterClient9 = new UserRegisterClient(null, null, "TestName", "TestLastName", "2019-12-16", "dateJoined", "profilePicture");


        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient1, userRegisterClient1.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient1, userRegisterClient1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient2, userRegisterClient2.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result4 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient3, userRegisterClient3.getClass());
            assertEquals(result4.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result5 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient4, userRegisterClient4.getClass());
            assertEquals(result5.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result6 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient5, userRegisterClient5.getClass());
            assertEquals(result6.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result7 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient6, userRegisterClient6.getClass());
            assertEquals(result7.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result8 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient7, userRegisterClient7.getClass());
            assertEquals(result8.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result9 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient8, userRegisterClient8.getClass());
            assertEquals(result9.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result10 = restUtility.postForEntity(businessTierUrl + "/api/register", userRegisterClient9, userRegisterClient9.getClass());
            assertEquals(result10.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
    }

    @org.junit.jupiter.api.Test
    void login() {
        String businessTierUrl = "http://" + "localhost" + ":8081/";
        //Models from client
        UserRegisterClient userRegisterClient1 = new UserRegisterClient(null, "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "TestName", "TestLastName", "birthday", "dateJoined", "profilePicture");
        UserRegisterClient userRegisterClient2 = new UserRegisterClient("user", null, "TestName", "TestLastName", "birthday", "2019-12-16", "profilePicture");
        UserRegisterClient userRegisterClient3 = new UserRegisterClient(null, null, "TestName", "TestLastName", "2019-12-16", "dateJoined", "profilePicture");
        UserRegisterClient userRegisterClient4 = new UserRegisterClient("user", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "TestName", "TestLastName", "2019-12-16", "2019-12-16", "profilePicture");

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<?> test1 = restUtility.postForEntity(businessTierUrl + "/auth/login", userRegisterClient1, userRegisterClient1.getClass());
            assertEquals(test1.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<?> test2 = restUtility.postForEntity(businessTierUrl + "/auth/login", userRegisterClient2, userRegisterClient2.getClass());
            assertEquals(test2.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<?> test3 = restUtility.postForEntity(businessTierUrl + "/auth/login", userRegisterClient3, userRegisterClient3.getClass());
            assertEquals(test3.getStatusCode(), HttpStatus.BAD_REQUEST);
        });

        //This doesn't work because I didn't register the user so actually it is expected
        ResponseEntity<?> test4 = restUtility.postForEntity(DataLayerURI + "/auth/login", userRegisterClient4, userRegisterClient4.getClass());
        assertEquals(test4.getStatusCode(), HttpStatus.OK);


    }
}