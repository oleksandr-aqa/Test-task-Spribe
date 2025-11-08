package dto.request;

import dto.reponse.PlayerResponse;

public class PlayerRequest {
    private String age;
    private String editor;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

    public PlayerRequest(String age, String editor, String gender, String login, String password,
                         String role, String screenName) {
        this.age = age;
        this.editor = editor;
        this.gender = gender;
        this.login = login;
        this.password = password;
        this.role = role;
        this.screenName = screenName;
    }

    public PlayerRequest(String age, String gender, String login, String password,
                         String role, String screenName) {
        this.age = age;
        this.gender = gender;
        this.login = login;
        this.password = password;
        this.role = role;
        this.screenName = screenName;
    }

    public static PlayerRequest fromResponse(PlayerResponse playerResponse) {
        return new PlayerRequest(
                playerResponse.getAge(),
                playerResponse.getGender(),
                playerResponse.getLogin(),
                playerResponse.getPassword(),
                playerResponse.getRole(),
                playerResponse.getScreenName()
        );
    }


    public static PlayerRequest getDefaultPlayerAdminRole() {
        return new PlayerRequest(
                "35",
                "supervisor",
                "male",
                "uniqueLogin24",
                "Qwerty123",
                "admin",
                "uniqueScreenName24"
        );
    }

    public static PlayerRequest getDefaultPlayerUserRole() {
        return new PlayerRequest(
                "35",
                "supervisor",
                "male",
                "uniqueLogin25",
                "Qwerty123",
                "user",
                "uniqueScreenName25"
        );
    }

    public String getAge() {
        return age;
    }

    public PlayerRequest setAge(String age) {
        this.age = age;
        return this;
    }

    public String getEditor() {
        return editor;
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
