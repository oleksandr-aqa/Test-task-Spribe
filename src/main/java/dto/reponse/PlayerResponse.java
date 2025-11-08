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

    public static PlayerResponse getStaticSupervisorData() {
        return new PlayerResponse(
                "1",
                "supervisor",
                "testSupervisor",
                "testSupervisor",
                "male",
                "28",
                "supervisor"
        );
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getScreenName() {
        return screenName;
    }
}
