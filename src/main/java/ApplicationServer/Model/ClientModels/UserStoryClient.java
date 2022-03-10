package ApplicationServer.Model.ClientModels;

public class UserStoryClient {
    private int userStoryId;
    private int projectId;
    private String priority;
    private String description;
    private int difficulty;
    private String status;

    public UserStoryClient() {
    }

    public UserStoryClient(int projectId, String priority, String description, int difficulty, String status) {
        this.projectId = projectId;
        this.priority = priority;
        this.description = description;
        this.difficulty = difficulty;
        this.status = status;
    }

    public UserStoryClient(int userStoryId, int projectId, String priority, String description, int difficulty, String status) {
        this.userStoryId = userStoryId;
        this.projectId = projectId;
        this.priority = priority;
        this.description = description;
        this.difficulty = difficulty;
        this.status = status;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UserStoryClient{" +
                "userStoryId=" + userStoryId +
                ", projectBacklogId=" + projectId +
                ", priority='" + priority + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", status='" + status + '\'' +
                '}';
    }
}
