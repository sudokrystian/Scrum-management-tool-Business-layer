package ApplicationServer.Tests;

import ApplicationServer.Controllers.ControllerConfiguration;
import ApplicationServer.Model.DataLayerModels.UserStoryDataLayer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserStoryControllerTest extends ControllerConfiguration {

    private final static String businessTierUrl = "http://" + "localhost" + ":8081/";

    @BeforeEach
    void setUp() {
        //Data tier has to be running
    }

    @Test
    void getUserStory() {
        String jsonUserStory1 = restUtility.getForObject(businessTierUrl + "/api/userStory?projectId=", String.class);
        String jsonUserStory2 = restUtility.getForObject(businessTierUrl + "/api/userStory?userStoryId=", String.class);
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.getForObject(businessTierUrl + "/api/userStory", String.class);
        });
        try {
            List<UserStoryDataLayer> userStoryFromDataLayer1 = jsonMapper.readValue(jsonUserStory1, new TypeReference<List<UserStoryDataLayer>>(){});
            List<UserStoryDataLayer> userStoryFromDataLayer2 = jsonMapper.readValue(jsonUserStory2, new TypeReference<List<UserStoryDataLayer>>(){});
            assertEquals(0, userStoryFromDataLayer1.size());
            assertNotEquals(0, userStoryFromDataLayer2.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void createUserStory() {
    }

    @Test
    void removeUserStory() {
    }
}
