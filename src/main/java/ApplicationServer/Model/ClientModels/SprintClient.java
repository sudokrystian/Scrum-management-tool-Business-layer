package ApplicationServer.Model.ClientModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SprintClient {

    private int projectId;
    private int sprintId;
    private int sprintNumber;
    private String dateStarted;
    private String dateFinished;
    private String productOwnerUsername;
    private String scrumMasterUsername;
    private String status;
    @JsonProperty("isScrumMaster")
    private boolean isScrumMaster;
    @JsonProperty("isProductOwner")
    private boolean isProductOwner;

    public SprintClient() {}

    public SprintClient(int sprintId, int projectId, int sprintNumber, String dateStarted, String dateFinished, String productOwnerUsername, String scrumMasterUsername, String status) {
        this.sprintId = sprintId;
        this.projectId = projectId;
        this.sprintNumber = sprintNumber;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.productOwnerUsername = productOwnerUsername;
        this.scrumMasterUsername = scrumMasterUsername;
        this.status = status;
        this.isScrumMaster = false;
        this.isProductOwner = false;
    }

    public SprintClient(int sprintNumber, String dateStarted, String dateFinished, String productOwnerUsername, String scrumMasterUsername, String status) {
        this.sprintNumber = sprintNumber;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.productOwnerUsername = productOwnerUsername;
        this.scrumMasterUsername = scrumMasterUsername;
        this.status = status;
        this.isScrumMaster = false;
        this.isProductOwner = false;
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

    public boolean isScrumMaster() {
        return isScrumMaster;
    }

    public boolean isProductOwner() {
        return isProductOwner;
    }

    public void setScrumMaster(boolean scrumMaster) {
        isScrumMaster = scrumMaster;
    }

    public void setProductOwner(boolean productOwner) {
        isProductOwner = productOwner;
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
