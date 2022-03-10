package ApplicationServer.Model.ClientModels;


public class UserRegisterClient {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthday;
    private String dateJoined;
    private String profilePicture;

    public UserRegisterClient() {

    }

    public UserRegisterClient(String username, String password, String firstName, String lastName, String birthday, String dateJoined, String profilePicture) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.dateJoined = dateJoined;
        this.profilePicture = profilePicture;
    }

    public UserRegisterClient(String username, String password) {
        this.username = username;
        this.password = password;
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.birthday = "birthday";
        this.dateJoined = "dateJoined";
        this.profilePicture = "profilePicture";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
