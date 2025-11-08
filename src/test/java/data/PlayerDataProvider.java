package data;

import dto.request.PlayerRequest;
import org.testng.annotations.DataProvider;

public class PlayerDataProvider {

    @DataProvider(name = "validPlayerData", parallel = true)
    public Object[][] validPlayerData() {
        return new Object[][]{
                {new PlayerRequest("16", "supervisor", "male", "uniqueLogin1", "Qwerty123", "admin", "uniqueScreenName1"), 200}, //можно поменять на HttpStatus.SC_OK
                {new PlayerRequest("59", "supervisor", "female", "uniqueLogin2", "Abcdef123", "user", "uniqueScreenName2"), 200},
                {new PlayerRequest("30", "admin", "female", "uniqueLogin3", "Pas1234", "user", "uniqueScreenName3"), 200},
                {new PlayerRequest("46", "supervisor", "male", "uniqueLogin4", "Qwerty1234", "admin", "uniqueScreenName4"), 200},
                {new PlayerRequest("60", "supervisor", "female", "uniqueLogin5", "Qwerty456", "user", "uniqueScreenName5"), 200},
                {new PlayerRequest("17", "admin", "female", "uniqueLogin6", "Abcdef789", "user", "uniqueScreenName6"), 200},
                {new PlayerRequest("18", "supervisor", "male", "uniqueLogin7", "1Password234", "admin", "uniqueScreenName7"), 200},
                {new PlayerRequest("49", "admin", "female", "uniqueLogin8", "Qwerty987", "user", "uniqueScreenName8"), 200},
                {new PlayerRequest("25", "supervisor", "female", "uniqueLogin9", "Password1234567", "admin", "uniqueScreenName9"), 200}
        };
    }

    @DataProvider(name = "invalidPlayerData", parallel = true)
    public Object[][] invalidPlayerData() {
        return new Object[][]{
                {new PlayerRequest("17", "supervisor", "invalid_gender", "uniqueLogin10", "Pass123", "admin", "uniqueScreenName10"), 400},
                {new PlayerRequest("17", "supervisor", "male", "uniqueLogin11", "short", "admin", "uniqueScreenName11"), 400},
                {new PlayerRequest("17", "supervisor", "male", "uniqueLogin12", "Qwerty1234512345", "admin", "uniqueScreenName12"), 400},
                {new PlayerRequest("15", "supervisor", "female", "uniqueLogin13", "Qwerty123", "admin", "uniqueScreenName13"), 400},
                {new PlayerRequest("61", "supervisor", "male", "uniqueLogin14", "Qwerty123", "admin", "uniqueScreenName14"), 400},
                {new PlayerRequest("25", "admin", "female", "uniqueLogin15", "Qwerty123", "admin", "uniqueScreenName15"), 400},
                {new PlayerRequest("25", "admin", "female", "uniqueLogin16", "Qwerty123", "supervisor", "uniqueScreenName16"), 400},
                {new PlayerRequest("25", "user", "male", "uniqueLogin17", "Qwerty123   ", "admin", "uniqueScreenName17"), 400},
                {new PlayerRequest("17", "supervisor", "female", "uniqueLogin18", "ТестПароль", "admin", "uniqueScreenName18"), 400}
        };
    }

    @DataProvider(name = "nonUniqueLoginAndScreenNameData", parallel = true)
    public Object[][] nonUniqueLoginAndScreenNameData() {
        return new Object[][]{
                {new PlayerRequest("16", "supervisor", "male", "uniqueLogin19", "Qwerty123", "admin", "uniqueScreenName19"), 200},
                {new PlayerRequest("17", "supervisor", "male", "uniqueLogin19", "Password123", "user", "uniqueScreenName19"), 400},
                {new PlayerRequest("25", "supervisor", "female", "uniqueLogin19", "Password123", "admin", "uniqueScreenName19"), 400},
                {new PlayerRequest("25", "supervisor", "male", "uniqueLogin0", "Password123", "admin", "uniqueScreenName0"), 400},
                {new PlayerRequest("25", "supervisor", "male", "uniqueLogin0", "Password123", "user", "uniqueScreenName0"), 400}
        };
    }
}
