package ApplicationServer.Model.DataLayerModels;



public class SprintUserStoryDataLayer {
    private int sprintUserStoryId;

    private int userStoryId;

    private int sprintBacklogId;

    public SprintUserStoryDataLayer() {
    }

    public SprintUserStoryDataLayer(int userStoryId, int sprintBacklogId) {
        this.userStoryId = userStoryId;
        this.sprintBacklogId = sprintBacklogId;
    }

    public int getSprintUserStoryId() {
        return sprintUserStoryId;
    }

    public void setSprintUserStoryId(int sprintUserStoryId) {
        this.sprintUserStoryId = sprintUserStoryId;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getSprintBacklogId() {
        return sprintBacklogId;
    }

    public void setSprintBacklogId(int sprintBacklogId) {
        this.sprintBacklogId = sprintBacklogId;
    }
}
