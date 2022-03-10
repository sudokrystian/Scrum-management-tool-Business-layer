package ApplicationServer.Model.DataLayerModels;

public class UsersInProjectDataLayer {
    private int projectId;
    private String username;

    public UsersInProjectDataLayer() {
    }

    public UsersInProjectDataLayer(int projectId, String username) {
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
