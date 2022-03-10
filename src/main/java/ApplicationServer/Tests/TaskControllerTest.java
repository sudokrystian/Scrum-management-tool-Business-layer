package ApplicationServer.Tests;

import ApplicationServer.Controllers.ControllerConfiguration;
import ApplicationServer.Model.ClientModels.TasksClient;
import ApplicationServer.Model.DataLayerModels.TasksDataLayer;
import ApplicationServer.Model.DataLayerModels.UserTaskKey;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest extends ControllerConfiguration {

    private final static String businessTierUrl = "http://" + "localhost" + ":8081/";

    @BeforeEach
    void setUp() {
        //Data tier has to be running
    }

    @Test
    void getUserTasksForUsername() {
        String jsonUserTask1 = restUtility.getForObject(businessTierUrl + "/api/userTask?username=", String.class);
        String jsonUserTask2 = restUtility.getForObject(businessTierUrl + "/api/userTask?username=admin", String.class);
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.getForObject(businessTierUrl + "/api/userTask", String.class);
        });
        try {
            List<TasksDataLayer> userTasksFromDataLayer1 = jsonMapper.readValue(jsonUserTask1, new TypeReference<List<TasksDataLayer>>(){});
            List<TasksDataLayer> userTasksFromDataLayer2 = jsonMapper.readValue(jsonUserTask2, new TypeReference<List<TasksDataLayer>>(){});
            assertEquals(0, userTasksFromDataLayer1.size());
            assertNotEquals(0, userTasksFromDataLayer2.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void assignTaskToUser() {
        UserTaskKey userTaskKey1 = new UserTaskKey(1,"admin");
        UserTaskKey userTaskKey2 = new UserTaskKey(-1,"ajajajajjaja");
        UserTaskKey userTaskKey3 = new UserTaskKey(0,"admin");
        UserTaskKey userTaskKey4 = new UserTaskKey(-1,null);

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/api/register", userTaskKey1, userTaskKey1.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/api/register", userTaskKey2, userTaskKey2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/api/register", userTaskKey3, userTaskKey3.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result4 = restUtility.postForEntity(businessTierUrl + "/api/register", userTaskKey3, userTaskKey3.getClass());
            assertEquals(result4.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
    }

    @Test
    void getAllTasks() {
        String jsonTasks1 = restUtility.getForObject(businessTierUrl + "api/task?sprintUserStoryId=", String.class);
        String jsonTasks2 = restUtility.getForObject(businessTierUrl + "api/task?sprintUserStoryId=1", String.class);
        String jsonTasks3 = restUtility.getForObject(businessTierUrl + "/api/sprintTasks?sprintId=", String.class);
        String jsonTasks4 = restUtility.getForObject(businessTierUrl + "/api/sprintTasks?sprintId=1", String.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restUtility.getForObject(businessTierUrl + "/api/task", String.class);
        });
        try {
            List<TasksDataLayer> tasksFromDataLayer1 = jsonMapper.readValue(jsonTasks1, new TypeReference<List<TasksDataLayer>>(){});
            List<TasksDataLayer> tasksFromDataLayer2 = jsonMapper.readValue(jsonTasks2, new TypeReference<List<TasksDataLayer>>(){});
            List<TasksDataLayer> tasksFromDataLayer3 = jsonMapper.readValue(jsonTasks3, new TypeReference<List<TasksDataLayer>>(){});
            List<TasksDataLayer> tasksFromDataLayer4 = jsonMapper.readValue(jsonTasks4, new TypeReference<List<TasksDataLayer>>(){});

            assertEquals(0, tasksFromDataLayer1.size());
            assertNotEquals(0, tasksFromDataLayer2.size());
            assertEquals(0, tasksFromDataLayer3.size());
            assertNotEquals(0, tasksFromDataLayer4.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTask() {
        //Models from client
        TasksClient TasksClient1 = new TasksClient(1,1,"lalalla","ONGOING");
        TasksClient TasksClient2 = new TasksClient(1,-1, "lalala", "ONGOING");
        TasksClient TasksClient3 = new TasksClient(0,1, "lalala", "ONGOING");
        TasksClient TasksClient4 = new TasksClient(-1,0, "lalala", "ONGOING");
        TasksClient TasksClient5 = new TasksClient(-1,1,null, "COMPLETED");
        TasksClient TasksClient6 = new TasksClient(-1,1,"lalala", null);
        TasksClient TasksClient7 = new TasksClient(-1,1,"lalala", "Random");

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient1, TasksClient1.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient2, TasksClient2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient3, TasksClient3.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result4 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient4, TasksClient4.getClass());
            assertEquals(result4.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result5 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient5, TasksClient5.getClass());
            assertEquals(result5.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result6 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient6, TasksClient6.getClass());
            assertEquals(result6.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result7 = restUtility.postForEntity(businessTierUrl + "/api/register", TasksClient7, TasksClient7.getClass());
            assertEquals(result7.getStatusCode(), HttpStatus.BAD_REQUEST);
        });
    }
}
