package ApplicationServer.Model.ClientModels;

public class UserProjectClient {

    private int projectId;
    private String username;

    public UserProjectClient() {
    }

    public UserProjectClient(int projectId, String username) {
        this.projectId = projectId;
        this.username = username;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getUsername() {
        return username;
    }
}
