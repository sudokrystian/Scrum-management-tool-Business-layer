package ApplicationServer.Model.DataLayerModels;

public class SprintDataLayer {

    private int sprintId;
    private int projectId;
    private int sprintNumber;
    private String dateStarted;
    private String dateFinished;
    private String productOwnerUsername;
    private String scrumMasterUsername;
    private String status;

    public SprintDataLayer() {}

    public SprintDataLayer(int sprintId, int projectId, int sprintNumber, String dateStarted, String dateFinished, String productOwnerUsername, String scrumMasterUsername, String status) {
        this.projectId = projectId;
        this.sprintId = sprintId;
        this.sprintNumber = sprintNumber;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.productOwnerUsername = productOwnerUsername;
        this.scrumMasterUsername = scrumMasterUsername;
        this.status = status;
    }

    public SprintDataLayer(int sprintNumber, String dateStarted, String dateFinished, String productOwnerUsername, String scrumMasterUsername, String status) {
        this.sprintNumber = sprintNumber;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.productOwnerUsername = productOwnerUsername;
        this.scrumMasterUsername = scrumMasterUsername;
        this.status = status;
    }

    public int getSprintId() {
        return sprintId;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public String getDateFinished() {
        return dateFinished;
    }

    public String getproductOwnerUsername() {
        return productOwnerUsername;
    }

    public String getscrumMasterUsername() {
        return scrumMasterUsername;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "Sprint{" +
                        "sprintId=" + sprintId +
                        ", projectId=" + projectId +
                        ", sprintNumber=" + sprintNumber +
                        ", dateStarted='" + dateStarted + '\'' +
                        ", dateFinished='" + dateFinished + '\'' +
                        ", productOwnerUsername=" + productOwnerUsername +
                        ", scrumMasterUsername=" + scrumMasterUsername +
                        ", status='" + status + '\'' +
                        '}';
    }
}
