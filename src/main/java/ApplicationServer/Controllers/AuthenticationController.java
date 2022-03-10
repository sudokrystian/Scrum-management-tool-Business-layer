package ApplicationServer.Controllers;

import ApplicationServer.Model.ClientModels.UserRegisterClient;
import ApplicationServer.Model.DataLayerModels.UserDataLayer;
import ApplicationServer.Model.RegisterSocketProtocol.RegisterClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends ControllerConfiguration{

    /**
     * Register user method, processing data in JSON form sent from Client site of the system. If request is correct, sends POST request to the data layer.
     * Remodels UserCient into UserDataLayer.
     * Returns HTTP Response Status with code '400 Bad Request' or '200 OK' and relevant message so client can react accordingly.
     *
     * JSON Template
     * {
     * 	    "username": "username",
     * 	    "password": "password",
     *      "firstName": "firstName",
     *      "lastName": "lastName",
     *      "birthday": "YYYY-MM-DD",
     *      "dateJoined": "YYYY-MM-DD",
     *      "profilePicture": "profilePicture"
     * }
     *
     * @param user UserRegisterClient object parsed from JSON format received from Client
     * @return HTTP Response Status with Relevant message
     */

    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserRegisterClient user) {
        UserDataLayer userForDataLayer = new UserDataLayer(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getBirthday(), user.getDateJoined(), user.getProfilePicture());

        try {
            ResponseEntity<?> result = new RegisterClient().registerUser(userForDataLayer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

//        HttpEntity<UserDataLayer> userDataLayerHttpEntity = new HttpEntity<>(userForDataLayer);
//        try {
//            restUtility.postForLocation(DataLayerURI + "/auth/register", userDataLayerHttpEntity);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (HttpClientErrorException e) {
//            e.printStackTrace();
//            System.out.println("User couldn't be created");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (RestClientException e) {
//            System.out.println("Some internal json issue but user created successfully");
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
    }

    /**
     * Method for login user. It is processing POST request with UserRegisterClient object in format of JSON as an argument.
     * Remodels UserRegisterClient into UserDataLayer.
     * <p>
     *  Examples:
     *  http://<b>{host}</b>:8081/auth/login as a POST request with UserDataLayer object converted to JSON in a body.
     * </p>
     *
     * @param user UserDataLayer object in format of JSON
     * @return <i>HTTP 200 - OK</i> code if credentials are verified. Returns <i>HTTP 400 - BAD_REQUEST</i> if credentials are incorrect.
     */
    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<UserDataLayer> login(@RequestBody UserRegisterClient user) {
        UserDataLayer userForDataLayer = new UserDataLayer(user.getUsername(), user.getPassword(), null, null, null, null, null);
        try {
            restUtility.postForEntity(DataLayerURI + "/auth/login", userForDataLayer, userForDataLayer.getClass());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("No user returned from DataLayer");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
