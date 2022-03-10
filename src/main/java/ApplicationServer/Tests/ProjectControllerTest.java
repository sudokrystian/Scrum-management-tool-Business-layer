package ApplicationServer.Tests;

import ApplicationServer.Controllers.ControllerConfiguration;
import ApplicationServer.Model.ClientModels.ProjectClient;
import ApplicationServer.Model.ClientModels.UserForDisplay;
import ApplicationServer.Model.DataLayerModels.ProjectDataLayer;
import ApplicationServer.Model.DataLayerModels.UserDataLayer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectControllerTest extends ControllerConfiguration {

    @BeforeEach
    void setUp() {
        //Data tier has to be running
    }

    @Test
    void getProject() {
        String businessTierUrl = "http://" + "localhost" + ":8081/";
        String jsonProjects1 = restUtility.getForObject(businessTierUrl + "/api/project?username=", String.class);
        String jsonProjects2 = restUtility.getForObject(businessTierUrl + "/api/project?username=admin", String.class);
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.getForObject(businessTierUrl + "/api/project", String.class);
        });
        try {
            List<ProjectDataLayer> projectsFromDataLayer1 = jsonMapper.readValue(jsonProjects1, new TypeReference<List<ProjectDataLayer>>(){});
            List<ProjectDataLayer> projectsFromDataLayer2 = jsonMapper.readValue(jsonProjects2, new TypeReference<List<ProjectDataLayer>>(){});
            assertEquals(0, projectsFromDataLayer1.size());
            assertNotEquals(0, projectsFromDataLayer2.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void create() {
        String businessTierUrl = "http://" + "localhost" + ":8081/";
        List <String> admins = new ArrayList<>();
        admins.add("admin");
        admins.add("user");
        List<String> emptyAdmins = new ArrayList<>();
        List<String> nonExistantUsersAsAdmins = new ArrayList<>();
        nonExistantUsersAsAdmins.add("your mom");
        //Models from client
        ProjectClient projectClient1 = new ProjectClient(-1, "NameOfTheProject1", "ONGOING", 3, 3, admins);
        ProjectClient projectClient2 = new ProjectClient(1, "NameOfTheProject2", "ONGOING", 3, 3, admins);
        ProjectClient projectClient3 = new ProjectClient(0, "NameOfTheProject3", "ONGOING", 3, 3, admins);
        ProjectClient projectClient4 = new ProjectClient(-1, null, "ONGOING", 3, 3, admins);
        ProjectClient projectClient5 = new ProjectClient(-1, "NameOfTheProject4", null, 3, 3, admins);
        ProjectClient projectClient6 = new ProjectClient(-1, "NameOfTheProject5", "COMPLETED", 3, 3, admins);
        ProjectClient projectClient7 = new ProjectClient(-1, "NameOfTheProject6", "ONGOING", 0, 3, admins);
        ProjectClient projectClient8 = new ProjectClient(-1, "NameOfTheProject7", "ONGOING", 3, 0, admins);
        ProjectClient projectClient9 = new ProjectClient(-1, "NameOfTheProject8", "ONGOING", 0, 0, admins);
        ProjectClient projectClient10 = new ProjectClient(-1, "NameOfTheProject9", "random String", 0, 0, admins);
        ProjectClient projectClient11 = new ProjectClient(-1, "NameOfTheProject10", "ONGOING", 0, 0, emptyAdmins);
        ProjectClient projectClient12 = new ProjectClient(-1, "NameOfTheProject11", "ONGOING", 0, 0, nonExistantUsersAsAdmins);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient1);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient2);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient3);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient4);
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient5);
        });

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient6);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient7);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient8);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient9);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient10);
        });

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient11);

        restUtility.postForLocation(businessTierUrl + "/api/createProject?username=admin", projectClient12);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.postForLocation(businessTierUrl + "/api/createProject", projectClient1);
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.postForLocation(businessTierUrl + "/api/createProject?username=", projectClient1);
        });


    }

    @Test
    void getUsersInProjects() {
        String businessTierUrl = "http://" + "localhost" + ":8081/";
        String jsonUsers1 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?projectId=9", String.class);
        String jsonUsers2 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?projectId=1325235325", String.class);
        String jsonUsers3 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?projectId=-1", String.class);
        String jsonUsers4 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?projectId=0", String.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            String jsonUsers5 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?projectId", String.class);
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            String jsonUsers6 = restUtility.getForObject(businessTierUrl + "/api/usersInProject", String.class);
        });

        String jsonUsers7 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?sprintId=1", String.class);
        String jsonUsers8 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?sprintId=1325235325", String.class);
        String jsonUsers9 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?sprintId=-1", String.class);
        String jsonUsers10 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?sprintId=0", String.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            String jsonUsers11 = restUtility.getForObject(businessTierUrl + "/api/usersInProject?sprintId", String.class);
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            String jsonUsers12 = restUtility.getForObject(businessTierUrl + "/api/usersInProject/sprintId", String.class);
        });
        try {
            List<UserForDisplay> userForDisplay1 = jsonMapper.readValue(jsonUsers1, new TypeReference<List<UserForDisplay>>(){});
            assertNotEquals(0, userForDisplay1.size());
            List<UserForDisplay> userForDisplay2 = jsonMapper.readValue(jsonUsers2, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay2);
            List<UserForDisplay> userForDisplay3 = jsonMapper.readValue(jsonUsers3, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay3);
            List<UserForDisplay> userForDisplay4 = jsonMapper.readValue(jsonUsers4, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay4);
            List<UserForDisplay> userForDisplay5 = jsonMapper.readValue(jsonUsers7, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay5);
            List<UserForDisplay> userForDisplay6 = jsonMapper.readValue(jsonUsers8, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay6);
            List<UserForDisplay> userForDisplay7 = jsonMapper.readValue(jsonUsers9, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay7);
            List<UserForDisplay> userForDisplay8 = jsonMapper.readValue(jsonUsers10, new TypeReference<List<UserForDisplay>>(){});
            assertEquals(0, userForDisplay8);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    void getUsersOutsideProjects() {
    }

    @Test
    void addUserToProject() {
    }

    @Test
    void removeUserFromProject() {
    }

    @Test
    void assignAdmin() {
    }

    @Test
    void removeAdmin() {
    }
}
