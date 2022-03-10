package ApplicationServer.Model.ClientModels;

public class UserForDisplay {

    private String username;
    private String firstName;
    private String lastName;
    private boolean scrumMaster;
    private boolean productOwner;

    public UserForDisplay() {
    }

    public UserForDisplay(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserForDisplay(String username, String firstName, String lastName, boolean scrumMaster, boolean productOwner) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.scrumMaster = scrumMaster;
        this.productOwner = productOwner;
    }

    public UserForDisplay(String username) {
        this.username = username;
        this.firstName = "empty name";
        this.lastName = "empty surname";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(boolean scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public boolean isProductOwner() {
        return productOwner;
    }

    public void setProductOwner(boolean productOwner) {
        this.productOwner = productOwner;
    }
}
