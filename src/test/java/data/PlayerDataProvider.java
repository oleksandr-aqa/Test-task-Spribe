package data;

import base.BaseTest;
import dto.request.PlayerRequest;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.DataProvider;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerDataProvider extends BaseTest {

    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private static final String VALID_PASSWORD = "Qwerty123";

    private final String NON_UNIQUE_LOGIN = generateUniqueLogin();
    private final String NON_UNIQUE_SCREENNAME = generateUniqueScreenName();

    // TODO: add message validation once API returns proper error message
    @DataProvider(name = "validPlayerData", parallel = true)
    public Object[][] validPlayerData() {
        return new Object[][]{
                {new PlayerRequest("16",SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, FEMALE, generateUniqueLogin(), VALID_PASSWORD, USER, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), ADMIN, FEMALE, generateUniqueLogin(), VALID_PASSWORD, USER, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, USER, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), ADMIN, FEMALE, generateUniqueLogin(), VALID_PASSWORD, USER, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), ADMIN, MALE, generateUniqueLogin(), VALID_PASSWORD, USER, generateUniqueScreenName()), HttpStatus.SC_OK},
                {new PlayerRequest("60", SUPERVISOR, FEMALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_OK}
        };
    }

    // TODO: add message validation once API returns proper error message
    @DataProvider(name = "invalidPlayerData", parallel = true)
    public Object[][] invalidPlayerData() {
        return new Object[][]{
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, "invalid_gender", generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), "short", ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), "Long121234512345", ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest("15", SUPERVISOR, FEMALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest("61", SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), ADMIN, FEMALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_FORBIDDEN},
                {new PlayerRequest(generateRandomValidAge(), ADMIN, FEMALE, generateUniqueLogin(), VALID_PASSWORD, SUPERVISOR, generateUniqueScreenName()), HttpStatus.SC_FORBIDDEN},
                {new PlayerRequest(generateRandomValidAge(), USER, MALE, generateUniqueLogin(), VALID_PASSWORD + "   ", ADMIN, generateUniqueScreenName()), HttpStatus.SC_FORBIDDEN},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, FEMALE, generateUniqueLogin(), "ТестПароль", ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR}
        };
    }

    // TODO: add message validation once API returns proper error message
    @DataProvider(name = "nonUniqueLoginAndScreenNameData")
    public Object[][] nonUniqueLoginAndScreenNameData() {
        return new Object[][]{
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, NON_UNIQUE_LOGIN, VALID_PASSWORD, ADMIN, NON_UNIQUE_SCREENNAME ), HttpStatus.SC_OK},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, NON_UNIQUE_LOGIN, VALID_PASSWORD, USER, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, FEMALE, NON_UNIQUE_LOGIN, VALID_PASSWORD, ADMIN, generateUniqueScreenName()), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, ADMIN, NON_UNIQUE_SCREENNAME ), HttpStatus.SC_CLIENT_ERROR},
                {new PlayerRequest(generateRandomValidAge(), SUPERVISOR, MALE, generateUniqueLogin(), VALID_PASSWORD, USER, NON_UNIQUE_SCREENNAME ), HttpStatus.SC_CLIENT_ERROR}
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
