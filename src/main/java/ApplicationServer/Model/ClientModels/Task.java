package ApplicationServer.Model.ClientModels;

public class Task {

    private long taskId;
    private long sprintUserStoryId;
    private String description;
    private String status;

    public Task() {
    }

    public Task(long taskId, long sprintUserStoryId, String description, String status) {
        this.taskId = taskId;
        this.sprintUserStoryId = sprintUserStoryId;
        this.description = description;
        this.status = status;
    }

    public long getTaskId() {
        return taskId;
    }

    public long getSprintUserStoryId() {
        return sprintUserStoryId;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
