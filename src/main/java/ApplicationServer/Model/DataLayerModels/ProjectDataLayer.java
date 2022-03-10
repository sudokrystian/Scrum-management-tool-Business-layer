package ApplicationServer.Model.DataLayerModels;

import java.util.List;

public class ProjectDataLayer {
    private int projectId;
    private String name;
    private String status;
    private int numberOfIterations;
    private int lengthOfSprint;
    private List<String> admins;

    public ProjectDataLayer(){

    }

    public ProjectDataLayer(int projectId, String name, String status, int numberOfIterations, int lengthOfSprint, List<String> admins) {
        this.projectId = projectId;
        this.name = name;
        this.status = status;
        this.numberOfIterations = numberOfIterations;
        this.lengthOfSprint = lengthOfSprint;
        this.admins = admins;
    }

    public ProjectDataLayer(String name, String status, int numberOfIterations, int lengthOfSprint, List<String> admins) {
        this.name = name;
        this.status = status;
        this.numberOfIterations = numberOfIterations;
        this.lengthOfSprint = lengthOfSprint;
        this.admins = admins;
    }

    public int getLengthOfSprint() {
        return lengthOfSprint;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
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

    public List<String> getAdmins() {
        return admins;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", numberOfIterations=" + numberOfIterations +
                ", lengthOfSprint=" + lengthOfSprint +
                '}';
    }
}
