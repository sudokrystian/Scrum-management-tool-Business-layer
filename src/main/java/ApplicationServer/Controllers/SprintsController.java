package ApplicationServer.Controllers;

import ApplicationServer.Model.ClientModels.SprintClient;
import ApplicationServer.Model.DataLayerModels.ScrumRole;
import ApplicationServer.Model.DataLayerModels.SprintDataLayer;
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
public class SprintsController extends ControllerConfiguration {

    /**
     * HTTP Method to get the sprints by project ID.
     * It is required to specify project ID otherwise server returns BAD REQUEST 400.
     *
     * Endpoint
     * http://<b>{host}</b>:8081/api/sprint?projectId={id}
     *
     * Example
     * http://localhost:8081/api/sprint?projectId=1
     *
     * Json template{
     *
     *            *   "sprintId":1,
     *            *   "projectId":1,
     *            *   "sprintNumber":1,
     *            *   "dateStarted":"2019-11-21",
     *            *   "dateFinished":"2019-11-21",
     *            *   "productOwnerUsername":"username",
     *            *   "scrumMasterUsername":"username",
     *            *   "status":"completed"
     *
     *          * }
     * @param projectId of the project, which is unique for all the projects
     * @return returns a list of sprints depending on the project ID
     */
    @RequestMapping(value = "/sprint", method = RequestMethod.GET)
    public ResponseEntity<?> getSprints(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "projectId", required = false) Integer projectId,
            @RequestParam(value = "sprintId", required = false) Integer sprintId) {
        String jsonSprints;
        if (projectId != null) {
            jsonSprints = restUtility.getForObject(DataLayerURI + "/api/sprint?projectId=" + projectId, String.class);
        } else if(sprintId != null) {
            jsonSprints = restUtility.getForObject(DataLayerURI + "/api/sprint?id=" + sprintId, String.class);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            try {
                List<SprintDataLayer> sprintsFromDataLayer = jsonMapper.readValue(jsonSprints, new TypeReference<List<SprintDataLayer>>(){});
                //For now this block is useless, but later this is where actual remodelling will be taking place
                //--------------------------------------------------------------------||--------------------------------------------------------------------
                List<SprintClient> sprintsForClients = new ArrayList<>();
                for(SprintDataLayer sprint : sprintsFromDataLayer) {
                    sprintsForClients.add(new SprintClient(sprint.getSprintId(), sprint.getProjectId(), sprint.getSprintNumber(), sprint.getDateStarted(), sprint.getDateStarted(), sprint.getproductOwnerUsername(), sprint.getscrumMasterUsername(), sprint.getStatus()));
                }
                for(SprintClient sprintClient : sprintsForClients) {
                    if(sprintClient.getproductOwnerUsername().equals(username)) {
                        sprintClient.setProductOwner(true);
                    }
                    if(sprintClient.getscrumMasterUsername().equals(username)) {
                        sprintClient.setScrumMaster(true);
                    }
                }
                String jsonForClient = jsonMapper.writeValueAsString(sprintsForClients);
                return new ResponseEntity<>(jsonForClient, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }

    /**
     * HTTP Method to update existing sprint.
     * It is required to send entire object of the sprint.
     *
     * Endpoint
     * http://<b>{host}</b>:8081/api/sprint
     *
     * Example
     * http://localhost:8081/api/sprint
     *
     * Json template{
     *
     *            *   "sprintId":1,
     *            *   "projectId":1,
     *            *   "sprintNumber":1,
     *            *   "dateStarted":"2019-11-21",
     *            *   "dateFinished":"2019-11-21",
     *            *   "productOwnerUsername":"username",
     *            *   "scrumMasterUsername":"username",
     *            *   "status":"completed"
     *
     *          * }
     * @return OK or BAD_REQUEST
     */

    @RequestMapping(value = "/createSprint", method = RequestMethod.POST)
    public ResponseEntity<SprintClient> create(@RequestBody SprintClient sprint) {
        //For now this block is useless, but later this is where actual remodelling will be taking place
        //--------------------------------------------------------------------||--------------------------------------------------------------------
        SprintDataLayer sprintForDataLayer = new SprintDataLayer(sprint.getSprintId(), sprint.getProjectId(), sprint.getSprintNumber(), sprint.getDateStarted(), sprint.getDateStarted(), sprint.getproductOwnerUsername(), sprint.getscrumMasterUsername(), sprint.getStatus());
        HttpEntity<SprintDataLayer> sprintDataLayerHttpEntity = new HttpEntity<>(sprintForDataLayer);
        //--------------------------------------------------------------------||--------------------------------------------------------------------
        try {
            restUtility.postForLocation(DataLayerURI + "/api/sprint", sprintDataLayerHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Sprint couldn't be created");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Assigns a product owner to the sprint.
     *
     * EXAMPLE:
     *  http://{host}:8081/api/productOwner
     *
     * @return <i>HTTP 200 - OK</i> code with sprint object if product owner is assigned. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/productOwner", method = RequestMethod.POST)
    public ResponseEntity<?> assignProductOwner(
            @RequestBody ScrumRole scrumRole
    ){
            //Line below is useless but I had to do it because of bad implementation of dataLayer
            HttpEntity<ScrumRole> scrumRoleHttpEntity = new HttpEntity<>(scrumRole);
        try {
            restUtility.postForLocation(DataLayerURI + "/api/productOwner", scrumRoleHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Product owner couldn't be added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Assigns a scrum master to the sprint.
     *
     * EXAMPLE:
     *  http://{host}:8081/api/scrumMaster
     *
     * @return <i>HTTP 200 - OK</i> code with sprint object if scrum master is assigned. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/scrumMaster", method = RequestMethod.POST)
    public ResponseEntity<?> assignScrumMaster(
            @RequestBody ScrumRole scrumRole
    ){
        HttpEntity<ScrumRole> scrumRoleHttpEntity = new HttpEntity<>(scrumRole);
        try {
            restUtility.postForLocation(DataLayerURI + "/api/scrumMaster", scrumRoleHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Scrum master couldn't be added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
