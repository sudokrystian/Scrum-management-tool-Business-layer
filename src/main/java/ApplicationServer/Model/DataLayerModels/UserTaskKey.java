package ApplicationServer.Model.DataLayerModels;

import java.util.Objects;

public class UserTaskKey {
    private int taskId;

    private String username;

    public UserTaskKey() {
    }

    public UserTaskKey(int taskId, String username) {
        this.taskId = taskId;
        this.username = username;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserTaskKey{" +
                "taskId=" + taskId +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTaskKey)) return false;
        UserTaskKey that = (UserTaskKey) o;
        return getTaskId() == that.getTaskId() &&
                Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), getUsername());
    }
}
