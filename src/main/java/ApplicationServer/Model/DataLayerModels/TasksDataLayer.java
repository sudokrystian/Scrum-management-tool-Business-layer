package ApplicationServer.Model.DataLayerModels;

public class TasksDataLayer {
    private int taskId;

    private int sprintUserStoryId;

    private String description;

    private String status;

    public TasksDataLayer() {
    }

    public TasksDataLayer(int sprintUserStoryId, String description, String status) {
        this.sprintUserStoryId = sprintUserStoryId;
        this.description = description;
        this.status = status;
    }

    public TasksDataLayer(int taskId, int sprintUserStoryId, String description, String status) {
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
}
