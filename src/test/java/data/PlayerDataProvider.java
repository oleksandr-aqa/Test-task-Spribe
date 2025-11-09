package data;

import base.BaseTest;
import dto.request.PlayerRequest;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.DataProvider;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerDataProvider extends BaseTest {

    private final String login = generateUniqueLogin();
    private final String screenName = generateUniqueScreenName();
    private final String male = "male";
    private final String female = "female";
    private final String validPassword = "Qwerty123";

    // TODO: add message validation once API returns proper error message
    @DataProvider(name = "validPlayerData", parallel = true)
    public Object[][] validPlayerData() {
        return new Object[][]{
                {new PlayerRequest("16", supervisor, male, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), supervisor, female, generateUniqueLogin(), validPassword, user, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), admin, female, generateUniqueLogin(), validPassword, user, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), supervisor, female, generateUniqueLogin(), validPassword, user, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), admin, female, generateUniqueLogin(), validPassword, user, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), admin, female, generateUniqueLogin(), validPassword, user, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest("60", supervisor, female, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_OK}
        };
    }

    // TODO: add message validation once API returns proper error message
    @DataProvider(name = "invalidPlayerData", parallel = true)
    public Object[][] invalidPlayerData() {
        return new Object[][]{
                {new PlayerRequest(generateRandomValidAge(), supervisor, "invalid_gender", generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, generateUniqueLogin(), "short", admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, generateUniqueLogin(), "Long121234512345", admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest("15", supervisor, female, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest("61", supervisor, male, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), admin, female, generateUniqueLogin(), validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_FORBIDDEN},
                {new PlayerRequest(generateRandomValidAge(), admin, female, generateUniqueLogin(), validPassword, supervisor, generateUniqueScreenName()), HttpStatus.SC_FORBIDDEN},
                {new PlayerRequest(generateRandomValidAge(), user, male, generateUniqueLogin(), validPassword + "   ", admin, generateUniqueScreenName()), HttpStatus.SC_FORBIDDEN},
                {new PlayerRequest(generateRandomValidAge(), supervisor, female, generateUniqueLogin(), "ТестПароль", admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR}
        };
    }

    // TODO: add message validation once API returns proper error message
    @DataProvider(name = "nonUniqueLoginAndScreenNameData", parallel = true)
    public Object[][] nonUniqueLoginAndScreenNameData() {
        return new Object[][]{
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, login, validPassword, admin, screenName), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, login, validPassword, user, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), supervisor, female, login, validPassword, admin, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, generateUniqueLogin(), validPassword, admin, screenName), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), supervisor, male, generateUniqueLogin(), validPassword, user, screenName), HttpStatus.SC_CLIENT_ERROR}
        };
    }

    private String generateUniqueLogin() {
        return "login" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateUniqueScreenName() {
        return "uniqueScreenName" + UUID.randomUUID().toString().substring(0, 8);
    }

    private static String generateRandomValidAge() {
        int age = ThreadLocalRandom.current().nextInt(16, 61);
        return String.valueOf(age);
    }
}
