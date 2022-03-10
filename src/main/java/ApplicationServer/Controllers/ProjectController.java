package ApplicationServer.Controllers;

import ApplicationServer.Model.ClientModels.ProjectClient;
import ApplicationServer.Model.ClientModels.UserForDisplay;
import ApplicationServer.Model.ClientModels.UserProjectClient;
import ApplicationServer.Model.DataLayerModels.ProjectDataLayer;
import ApplicationServer.Model.DataLayerModels.SprintDataLayer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ProjectController extends ControllerConfiguration{

    /**
     * HTTP Method to get the projects by two nullable parameters.
     * It is not required to specify them and also not possible to specify both of them.
     * Endpoint
     *  http://<b>{host}</b>:8081/api/project
     *
     * Example of request with nullable parameters
     * http://<b>{host}</b>:8081/api/project?username=YourUsername               To find all the existing projects
     *
     * Json template{
     *          *
     *          *  "name":"name",
     *          *  "status":"status",
     *          *  "numberOfIterations":"numberOfIterations",
     *          *  "lengthOfSprint":"length"
     *          * }
     * @param username username of the user who is asking for the project
     * @return returns a list of projects depending on the request that we made
     */
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectClient>> getProject(
            @RequestParam(value = "username", required = true) String username) {
        String jsonProjects;
        if (username != null){
            jsonProjects = restUtility.getForObject(DataLayerURI + "/api/project?username=" + username, String.class);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<ProjectDataLayer> projectsFromDataLayer = jsonMapper.readValue(jsonProjects, new TypeReference<List<ProjectDataLayer>>(){});
            //For now this block is useless, but later this is where actual remodelling will be taking place
            //--------------------------------------------------------------------||--------------------------------------------------------------------
            List<ProjectClient> projectsForClients = new ArrayList<>();
            for(ProjectDataLayer project : projectsFromDataLayer) {
                projectsForClients.add(new ProjectClient(project.getProjectId(), project.getName(), project.getStatus(), project.getNumberOfIterations(), project.getLengthOfSprint(), project.getAdmins()));
            }
            for(ProjectClient projectClient : projectsForClients) {
                for(String admin : projectClient.getAdmins()) {
                    if(admin.equals(username)) {
                        projectClient.setAdministrator();
                    }
                }
            }
            //--------------------------------------------------------------------||--------------------------------------------------------------------
            return new ResponseEntity<>(projectsForClients, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for creating a new project. Processing data in JSON form sent from client site of the system.
     * Remodels the project and sends it to Data Layer.
     * If the project was created successfully it returns  a HTTP Response Status with code '200 OK'
     * If the project failed to be created  it returns  a HTTP Response Status with code '400 Bad Request'
     * Endpoint
     *      * http://<b>{host}</b>:8081/api/createProject

     * @param project ProjectDataLayer object in format of Json received from client
     * @return <i>HTTP 200 - OK</i> code if project created. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    public ResponseEntity<ProjectClient> create(
            @RequestBody ProjectClient project,
            @RequestParam(value = "username") String username) {
        //For now this block is useless, but later this is where actual remodelling will be taking place
        //--------------------------------------------------------------------||--------------------------------------------------------------------
        ProjectDataLayer projectForDataLayer = new ProjectDataLayer(project.getName(), project.getStatus(), project.getNumberOfIterations(), project.getLengthOfSprint(), project.getAdmins());
        HttpEntity<ProjectDataLayer> projectDataLayerHttpEntity = new HttpEntity<>(projectForDataLayer);
        //--------------------------------------------------------------------||--------------------------------------------------------------------
        try {
            restUtility.postForLocation(DataLayerURI + "/api/project?username=" + username, projectDataLayerHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Project couldn't be created");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * HTTP Method to get all users or specific ones using username as a parameter.
     *
     * Endpoint
     *  http://<b>{host}</b>:8081/api/usersInProjects?projectId={id}
     *
     * Example of request
     * http://localhost:8081/api/usersInProject?projectId={id}
     * http://localhost:8081/api/usersInProject?sprintId={id}
     *
     * @param projectId specifying project ID
     * @return returns a list of all users or specific user if parameter is used
     */
    @RequestMapping(value = "/usersInProject", method = RequestMethod.GET)
    public ResponseEntity<List<UserForDisplay>> getUsersInProjects(
            @RequestParam(value = "projectId", required = false) Integer projectId,
            @RequestParam(value = "sprintId", required = false) Integer sprintId
    ){
        if(projectId != null) {
            return new ResponseEntity<>(getAllUsersForProject(projectId), HttpStatus.OK);
        } else if (sprintId != null){
            return new ResponseEntity<>(getAllUsersWithRoles(sprintId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private List<UserForDisplay> getAllUsersForProject(int projectId){
        String jsonUsersInProjects = restUtility.getForObject(DataLayerURI + "/api/usersInProjects?projectId=" + projectId, String.class);

        try {
            return jsonMapper.readValue(jsonUsersInProjects, new TypeReference<List<UserForDisplay>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<UserForDisplay> getAllUsersWithRoles(int sprintId){
        String jsonUsersInProjects = restUtility.getForObject(DataLayerURI + "/api/usersInProjects?sprintId=" + sprintId, String.class);
        String jsonSprint = restUtility.getForObject(DataLayerURI + "/api/sprint?id=" + sprintId, String.class);
        try {
            List<UserForDisplay> usersInProjectsFromDataLayer = jsonMapper.readValue(jsonUsersInProjects, new TypeReference<List<UserForDisplay>>(){});
            List<SprintDataLayer> sprintDataLayerList = jsonMapper.readValue(jsonSprint, new TypeReference<List<SprintDataLayer>>(){});
            SprintDataLayer sprintDataLayer = sprintDataLayerList.get(0);

            String scrumMasterUsername = sprintDataLayer.getscrumMasterUsername();
            String productOwnerUsername = sprintDataLayer.getproductOwnerUsername();

            for (UserForDisplay users : usersInProjectsFromDataLayer){
                if (users.getUsername().equals(scrumMasterUsername))
                    users.setScrumMaster(true);
                if (users.getUsername().equals(productOwnerUsername))
                    users.setProductOwner(true);
                System.out.println("User " + users.getUsername() + ", is product owner: " + users.isProductOwner() + ", is Scrum master: " + users.isScrumMaster());
            }

            return usersInProjectsFromDataLayer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * HTTP Method to get all users outside of project.
     *
     * Endpoint
     *  http://<b>{host}</b>:8081/api/usersOutsideProject?projectId={id}
     *
     * Example of request
     * http://localhost:8081/api/usersOutsideProject?projectId={id}
     *
     * @param projectId specifying project ID
     * @return returns a list of all users outside of project
     */
    @RequestMapping(value = "/usersOutsideProject", method = RequestMethod.GET)
    public ResponseEntity<List<UserForDisplay>> getUsersOutsideProjects(
            @RequestParam(value = "projectId") Integer projectId
    ){
        String jsonUsersInProjects = restUtility.getForObject(DataLayerURI + "/api/usersInProjects?projectId=" + projectId, String.class);
        String jsonUsers = restUtility.getForObject(DataLayerURI + "/api/users", String.class);

        try {
            List<UserForDisplay> allUsersInProject = jsonMapper.readValue(jsonUsersInProjects, new TypeReference<List<UserForDisplay>>(){});
            List<UserForDisplay> allUsers = jsonMapper.readValue(jsonUsers, new TypeReference<List<UserForDisplay>>(){});

            for (int i = 0; i < allUsersInProject.size(); i ++){
                for (int a = 0; a < allUsers.size(); a++){
                    if(allUsersInProject.get(i).getUsername().equals(allUsers.get(a).getUsername())){
                        allUsers.remove(a);
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(allUsers);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Add new entry to UsersInProjects table.
     * EXAMPLE:
     *  http://{host}:8081/api/addUser
     *
     * @return <i>HTTP 201 - CREATED</i> code with saved object in body if user is added to the project. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity<UserProjectClient> addUserToProject(
            @RequestBody UserProjectClient userProjectClient
    ) {
        System.out.println(userProjectClient.getUsername() + " , " + userProjectClient.getProjectId());
        HttpEntity<UserProjectClient> projectDataLayerHttpEntity = new HttpEntity<>(userProjectClient);
        //--------------------------------------------------------------------||--------------------------------------------------------------------
        try {
            restUtility.postForLocation(DataLayerURI + "/api/addUser", projectDataLayerHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove user from the project.
     * EXAMPLE:
     *  http://{host}:8081/api/removeUser
     *
     * @return <i>HTTP 201 - CREATED</i> code with saved object in body if user is added to the project. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    public ResponseEntity<UserProjectClient> removeUserFromProject(
            @RequestBody UserProjectClient userProjectClient
    ){
        HttpEntity<UserProjectClient> projectDataLayerHttpEntity = new HttpEntity<>(userProjectClient);
        //--------------------------------------------------------------------||--------------------------------------------------------------------
        try {
            restUtility.postForLocation(DataLayerURI + "/api/removeUser", projectDataLayerHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Post method assigning administrator privileges to the project.
     * EXAMPLE
     * http://{host}:8081/api/assignAdmin
     *
     * @return <i>HTTP 201 - CREATED</i> code if administrator is added. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/assignAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> assignAdmin(
            @RequestBody UserProjectClient userProjectClient
    ){
        HttpEntity<UserProjectClient> projectDataLayerHttpEntity = new HttpEntity<>(userProjectClient);
        try {
            restUtility.postForLocation(DataLayerURI + "/api/assignAdmin", projectDataLayerHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Admin couldn't be assigned");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Removes admin privileges from user. Deletes entry in database.
     *
     * EXAMPLE:
     *  http://{host}:8081/api/removeAdmin
     *
     * @return <i>HTTP 200 - OK</i> code if administrator is removed. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/removeAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> removeAdmin(
            @RequestBody UserProjectClient userProjectClient
    ){
        HttpEntity<UserProjectClient> projectDataLayerHttpEntity = new HttpEntity<>(userProjectClient);
        try {
            restUtility.postForLocation(DataLayerURI + "/api/removeAdmin", projectDataLayerHttpEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            System.out.println("Admin couldn't be deleted");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
