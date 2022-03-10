package ApplicationServer.Model.ClientModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectClient {

    private int projectId;
    private String name;
    private String status;
    private int numberOfIterations;
    private int lengthOfSprint;
    private List<String> admins;
    @JsonProperty("isAdministrator")
    private boolean isAdministrator;

    public ProjectClient(){

    }

    public ProjectClient(int projectId, String name, String status, int numberOfIterations, int lengthOfSprint, List<String> admins) {
        this.projectId = projectId;
        this.name = name;
        this.status = status;
        this.numberOfIterations = numberOfIterations;
        this.lengthOfSprint = lengthOfSprint;
        this.admins = admins;
        isAdministrator = false;
    }

    public ProjectClient(String name, String status, int numberOfIterations, int lengthOfSprint, List<String> admins) {
        this.name = name;
        this.status = status;
        this.numberOfIterations = numberOfIterations;
        this.lengthOfSprint = lengthOfSprint;
        this.admins = admins;
        isAdministrator = false;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public int getLengthOfSprint() {
        return lengthOfSprint;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator() {
        isAdministrator = true;
    }

    @Override
    public String toString() {
        return "ProjectDataLayer{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", numberOfIterations=" + numberOfIterations +
                ", lengthOfSprint=" + lengthOfSprint +
                '}';
    }
}
