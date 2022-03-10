package ApplicationServer.Controllers;

import ApplicationServer.Model.ClientModels.TasksClient;
import ApplicationServer.Model.DataLayerModels.SprintUserStoryDataLayer;
import ApplicationServer.Model.DataLayerModels.TasksDataLayer;
import ApplicationServer.Model.DataLayerModels.UserTaskKey;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class TaskController extends ControllerConfiguration {
    @RequestMapping(value = "/userTask", method = RequestMethod.GET)
    public ResponseEntity<?> getUserTasksForUsername(
            @RequestParam(value = "username") String username
    ){
        String jsonTasksForUser = restUtility.getForObject(DataLayerURI + "/api/userTask?username=" + username, String.class);

        try {
            List<TasksDataLayer> tasksForUsername = jsonMapper.readValue(jsonTasksForUser, new TypeReference<List<TasksDataLayer>>() {});
            return ResponseEntity.status(HttpStatus.OK).body(tasksForUsername);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/userTask", method = RequestMethod.POST)
    public ResponseEntity<?> assignTaskToUser(
            @RequestBody UserTaskKey userTaskKey
    ){
        try{
            restUtility.postForLocation(DataLayerURI + "/api/userTask", userTaskKey);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTasks(
            @RequestParam(value = "sprintUserStoryId", required = false) Integer sprintUserStoryId,
            @RequestParam(value = "sprintId", required = false) Integer sprintId
    ){
        String jsonTasks = "";
        if (sprintUserStoryId != null){
            jsonTasks = restUtility.getForObject(DataLayerURI + "/api/task?sprintUserStoryId=" + sprintUserStoryId, String.class);
        } else if (sprintId != null){
            jsonTasks = restUtility.getForObject(DataLayerURI + "/api/sprintTasks?sprintId=" + sprintId, String.class);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<TasksDataLayer> allTasks = jsonMapper.readValue(jsonTasks, new TypeReference<List<TasksDataLayer>>() {});
            return ResponseEntity.status(HttpStatus.OK).body(allTasks);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(
            @RequestBody TasksClient task
    ){
        TasksDataLayer postTask = new TasksDataLayer(task.getSprintUserStoryId(), task.getDescription(), task.getStatus());
        try{
            restUtility.postForLocation(DataLayerURI + "/api/task", postTask);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
