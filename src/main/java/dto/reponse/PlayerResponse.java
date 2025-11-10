package dto.reponse;

public class PlayerResponse extends BaseResponse {

    private String id;
    private String login;
    private String password;
    private String screenName;
    private String gender;
    private String age;
    private String role;

    public PlayerResponse() {
    }

    public PlayerResponse(String id, String login, String password, String screenName,
                          String gender, String age, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.screenName = screenName;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }
}
