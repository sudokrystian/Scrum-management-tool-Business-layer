package ApplicationServer.Controllers;

import ApplicationServer.Model.ClientModels.SprintUserStoryClient;
import ApplicationServer.Model.ClientModels.UserStoryClient;
import ApplicationServer.Model.DataLayerModels.AssignUserStory;
import ApplicationServer.Model.DataLayerModels.SprintDataLayer;
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
@RequestMapping(value = "/api")
public class SprintUserStoryController extends ControllerConfiguration {
    /**
     * Sends JSON format of AssignUserStory class to data tier to register it in database
     *
     * EXAMPLE:
     *  http://{host}:8081/api/sprintUserStory
     *
     *  Body:
     *  {
     *      "userStoryId": 5,
     *      "sprintId": 15
     *  }
     *
     * @param assignUserStory AssignUserStory class in JSON format passed as a body
     * @return <i>HTTP 201 - CREATED</i> code with created Sprint User Story. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/sprintUserStory", method = RequestMethod.POST)
    public ResponseEntity<?> assignUserStory(
            @RequestBody AssignUserStory assignUserStory
    ){
        HttpEntity<AssignUserStory> assignUserStoryHttpEntity = new HttpEntity<>(assignUserStory);
        try {
            restUtility.postForLocation(DataLayerURI + "/api/sprintUserStory", assignUserStoryHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Scrum master couldn't be added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sprintUserStory", method = RequestMethod.GET)
    public ResponseEntity<?> getSprintUserStory(
            @RequestParam(value = "sprintId") Integer sprintId
    ){
        String jsonSpringUserStories = restUtility.getForObject(DataLayerURI + "/api/sprintUserStory?sprintId=" + sprintId, String.class);

        try {
            List<SprintUserStoryDataLayer> sprintUserStories = jsonMapper.readValue(jsonSpringUserStories, new TypeReference<List<SprintUserStoryDataLayer>>(){});

            List<SprintUserStoryClient> clientSprintUserStories = new ArrayList<>();

            for(SprintUserStoryDataLayer USDataLayer : sprintUserStories){
                String jsonUserStoryDataLayer = restUtility.getForObject(DataLayerURI + "/api/userStory?userStoryId=" + USDataLayer.getUserStoryId(), String.class);

                List<UserStoryDataLayer> userStoryFromDataLayerList = jsonMapper.readValue(jsonUserStoryDataLayer, new TypeReference<List<UserStoryDataLayer>>(){});
                UserStoryDataLayer userStoryDataLayer = userStoryFromDataLayerList.get(0);

                SprintUserStoryClient sprintUserStoryClient = new SprintUserStoryClient(USDataLayer.getSprintUserStoryId(), userStoryDataLayer.getUserStoryId(), userStoryDataLayer.getPriority(), userStoryDataLayer.getDescription(), userStoryDataLayer.getDifficulty(), userStoryDataLayer.getStatus(), USDataLayer.getSprintBacklogId());
                clientSprintUserStories.add(sprintUserStoryClient);
            }

            return ResponseEntity.status(HttpStatus.OK).body(clientSprintUserStories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
