package ApplicationServer.Model.ClientModels;


import ApplicationServer.Model.DataLayerModels.UserStoryDataLayer;

public class SprintUserStoryClient {
    private int sprintUserStoryId;

    private int userStoryId;

    private String priority;

    private String description;

    private int difficulty;

    private String status;

    private int sprintBacklogId;

    public SprintUserStoryClient() {
    }

    public SprintUserStoryClient(int sprintUserStoryId, int userStoryId, String priority, String description, int difficulty, String status, int sprintBacklogId) {
        this.sprintUserStoryId = sprintUserStoryId;
        this.userStoryId = userStoryId;
        this.priority = priority;
        this.description = description;
        this.difficulty = difficulty;
        this.status = status;
        this.sprintBacklogId = sprintBacklogId;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getSprintUserStoryId() {
        return sprintUserStoryId;
    }

    public void setSprintUserStoryId(int sprintUserStoryId) {
        this.sprintUserStoryId = sprintUserStoryId;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSprintBacklogId() {
        return sprintBacklogId;
    }

    public void setSprintBacklogId(int sprintBacklogId) {
        this.sprintBacklogId = sprintBacklogId;
    }

    @Override
    public String toString() {
        return "SprintUserStoryClient{" +
                "sprintUserStoryId=" + sprintUserStoryId +
                ", userStoryId=" + userStoryId +
                ", priority='" + priority + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", status='" + status + '\'' +
                ", sprintBacklogId=" + sprintBacklogId +
                '}';
    }
}
