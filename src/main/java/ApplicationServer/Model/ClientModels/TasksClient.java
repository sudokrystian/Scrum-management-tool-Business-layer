package ApplicationServer.Model.ClientModels;

public class TasksClient {
    private int taskId;

    private int sprintUserStoryId;

    private String description;

    private String status;

    public TasksClient() {
    }

    public TasksClient(int sprintUserStoryId, String description, String status) {
        this.sprintUserStoryId = sprintUserStoryId;
        this.description = description;
        this.status = status;
    }

    public TasksClient(int taskId, int sprintUserStoryId, String description, String status) {
        this.taskId = taskId;
        this.sprintUserStoryId = sprintUserStoryId;
        this.description = description;
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSprintUserStoryId() {
        return sprintUserStoryId;
    }

    public void setSprintUserStoryId(int sprintUserStoryId) {
        this.sprintUserStoryId = sprintUserStoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TasksClient{" +
                "taskId=" + taskId +
                ", sprintUserStoryId=" + sprintUserStoryId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
