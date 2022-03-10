package ApplicationServer.Controllers;

import ApplicationServer.Model.ClientModels.UserStoryClient;
import ApplicationServer.Model.DataLayerModels.SprintUserStoryDataLayer;
import ApplicationServer.Model.DataLayerModels.UserStoryDataLayer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserStoryController extends ControllerConfiguration {
    /**
     * HTTP Method to get the user story by backlog ID.
     *
     * Endpoint
     * http://<b>{host}</b>:8081/api/userStory?backlogId={id}
     *
     * Example
     * http://localhost:8081/api/userStory?backlogId=1
     *
     * @param projectId of the project, which is unique for all the projects
     * @param userStoryId Id of User Story for getting all info
     * @return returns a list of user stories depending on the backlog ID
     */
    @RequestMapping(value = "/userStory", method = RequestMethod.GET)
    public ResponseEntity<?> getUserStory(
            @RequestParam(value = "projectId", required = false) Integer projectId,
            @RequestParam(value = "userStoryId", required = false) Integer userStoryId,
            @RequestParam(value = "sprintId", required = false) Integer sprintId
    ){
        String jsonUserStories = "";
        if (projectId != null) {
            jsonUserStories = restUtility.getForObject(DataLayerURI + "/api/userStory?projectId=" + projectId, String.class);
        } else if(userStoryId != null) {
            jsonUserStories = restUtility.getForObject(DataLayerURI + "/api/userStory?userStoryId=" + userStoryId, String.class);
        } else if (sprintId != null){
            List<UserStoryDataLayer> userStoriesNotInSprint = getUserStoriesNotInSprint(sprintId);
            return new ResponseEntity<>(userStoriesNotInSprint, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<UserStoryDataLayer> userStoryFromDataLayer = jsonMapper.readValue(jsonUserStories, new TypeReference<List<UserStoryDataLayer>>(){});
            //For now this block is useless, but later this is where actual remodelling will be taking place
            //--------------------------------------------------------------------||--------------------------------------------------------------------
            List<UserStoryClient> userStoryForClient = new ArrayList<>();
            for(UserStoryDataLayer userStory : userStoryFromDataLayer) {
                userStoryForClient.add(new UserStoryClient(userStory.getUserStoryId(), userStory.getProductBacklogId(), userStory.getPriority(), userStory.getDescription(), userStory.getDifficulty(), userStory.getStatus()));
            }
            String jsonForClient = jsonMapper.writeValueAsString(userStoryForClient);
            return new ResponseEntity<>(jsonForClient, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private List<UserStoryDataLayer> getUserStoriesNotInSprint(int sprintId) {
        String jsonAllUserStories = restUtility.getForObject(DataLayerURI + "/api/userStory?sprintId=" + sprintId, String.class);
        String jsonSpringUserStories = restUtility.getForObject(DataLayerURI + "/api/sprintUserStory?sprintId=" + sprintId, String.class);

        try {
            List<UserStoryDataLayer> allUserStoryFromDataLayer = jsonMapper.readValue(jsonAllUserStories, new TypeReference<List<UserStoryDataLayer>>() {
            });
            List<SprintUserStoryDataLayer> allSprintUserStories = jsonMapper.readValue(jsonSpringUserStories, new TypeReference<List<SprintUserStoryDataLayer>>() {
            });

            for (int i = 0; i < allSprintUserStories.size(); i++) {
                for (int a = 0; a < allUserStoryFromDataLayer.size(); a++) {
                    if (allSprintUserStories.get(i).getUserStoryId() == allUserStoryFromDataLayer.get(a).getUserStoryId()) {
                        allUserStoryFromDataLayer.remove(a);
                    }
                }
            }

            return allUserStoryFromDataLayer;
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @RequestMapping(value = "/userStory", method = RequestMethod.POST)
    public ResponseEntity<?> createUserStory(
            @RequestBody UserStoryClient userStoryClient
    ){
        System.out.println(userStoryClient.getDescription());
        UserStoryDataLayer userStoryDataLayer = new UserStoryDataLayer(userStoryClient.getUserStoryId(), userStoryClient.getProjectId(), userStoryClient.getPriority(), userStoryClient.getDescription(), userStoryClient.getDifficulty(), userStoryClient.getStatus());
        HttpEntity<UserStoryDataLayer> userStoryClientHttpEntity = new HttpEntity<>(userStoryDataLayer);
        try {
            restUtility.postForLocation(DataLayerURI + "/api/userStory", userStoryClientHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/userStory", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUserStory(@RequestParam(value = "userStoryId") Integer userStoryId){
        try{
            restUtility.delete(DataLayerURI + "/api/userStory?userStoryId=" + userStoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
