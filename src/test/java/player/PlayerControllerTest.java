package player;

import data.PlayerDataProvider;
import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static dto.request.PlayerRequest.fromResponse;

@Feature("Player controller methods")
public class PlayerControllerTest extends BasePlayerTest {

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Create player with valid data [POSITIVE]", dataProvider = "validPlayerData", dataProviderClass = PlayerDataProvider.class)
    public void createPlayerValidDataTest(PlayerRequest playerRequest, int statusCode) {
        PlayerResponse playerResponse = playerControllerRequest.createPlayer(playerRequest, statusCode);
        softAssert.get().assertNotNull(playerResponse.getId(), "ID should not be null");
        assertFieldPlayerRequestResponse(playerRequest, playerResponse);
        playerId.set(playerResponse.getId());
        deletePlayer(playerId.get());
        softAssert.get().assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Create player with invalid data [NEGATIVE]", dataProvider = "invalidPlayerData", dataProviderClass = PlayerDataProvider.class)
    public void createPlayerInvalidDataTest(PlayerRequest playerRequest, int statusCode) {
        PlayerResponse playerResponse = playerControllerRequest.createPlayer(playerRequest, statusCode);
        Assert.assertEquals(playerResponse.getContentLength(), "0"); //assert that returned response is empty, player not created
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Create player with non unique fields [NEGATIVE]", dataProvider = "nonUniqueLoginAndScreenNameData", dataProviderClass = PlayerDataProvider.class)
    public void createPlayerUniqueFieldTest(PlayerRequest playerRequest, int statusCode) {
        PlayerResponse playerResponse = playerControllerRequest.createPlayer(playerRequest, statusCode);
        Assert.assertEquals(playerResponse.getContentLength(), "0");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete player 'admin' role by supervisor [POSITIVE]")
    public void deletePlayerAdminBySupervisor() {
        playerId.set(createValidPlayerWithAdminRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), SUPERVISOR, HttpStatus.SC_NO_CONTENT);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete player 'user' role by supervisor [POSITIVE]")
    public void deletePlayerUserBySupervisor() {
        playerId.set(createValidPlayerWithUserRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), SUPERVISOR, HttpStatus.SC_NO_CONTENT);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete player 'user' role by admin [POSITIVE]")
    public void deletePlayerUserByAdmin() {
        playerId.set(createValidPlayerWithUserRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), ADMIN, HttpStatus.SC_NO_CONTENT);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete player 'supervisor' role by admin [NEGATIVE]")
    public void deletePlayerSupervisorByAdmin() {
        playerControllerRequest.deletePlayer(SUPERVISOR_ID, ADMIN, HttpStatus.SC_FORBIDDEN);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete player 'admin' role by user [NEGATIVE]")
    public void deletePlayerAdminByUser() {
        playerId.set(createValidPlayerWithAdminRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), USER, HttpStatus.SC_FORBIDDEN);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete player 'supervisor' role by user [NEGATIVE]")
    public void deletePlayerSupervisorByUser() {
        playerControllerRequest.deletePlayer(SUPERVISOR_ID, USER, HttpStatus.SC_FORBIDDEN);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Get data of supervisor role player [POSITIVE]")
    public void getSupervisorData() {
        PlayerResponse playerResponse = playerControllerRequest.getPlayerDataById(SUPERVISOR_ID, HttpStatus.SC_OK);
        assertFieldPlayerResponseResponse(supervisorData.get(), playerResponse);
        softAssert.get().assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Get data of admin role player [POSITIVE]")
    public void getAdminData() { //will fail due to bug (some response fields from create method are null)
        PlayerResponse playerResponse = createValidPlayerWithAdminRole();
        PlayerResponse playerResponse2 = playerControllerRequest.getPlayerDataById(playerResponse.getId(), HttpStatus.SC_OK);
        assertFieldPlayerResponseResponse(playerResponse2, playerResponse);
        playerId.set(playerResponse.getId());
        deletePlayer(playerId.get());
        softAssert.get().assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Get data of user role player [POSITIVE]")
    public void getUserData() { //will fail due to bug (some response fields from create method are null)
        PlayerResponse playerResponse = createValidPlayerWithUserRole();
        PlayerResponse playerResponse2 = playerControllerRequest.getPlayerDataById(playerResponse.getId(), HttpStatus.SC_OK);
        assertFieldPlayerResponseResponse(playerResponse2, playerResponse);
        playerId.set(playerResponse.getId());
        deletePlayer(playerId.get());
        softAssert.get().assertAll();
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Get data of unexisted player [NEGATIVE]")
    public void getUnexistedPlayerData() {
        PlayerResponse playerResponse = playerControllerRequest.getPlayerDataById("987654321", HttpStatus.SC_OK);
        Assert.assertEquals(playerResponse.getContentLength(), "0");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Get data of all players [POSITIVE]")
    public void getDataAllPlayers() { //will fail due to bug (some response fields from create method are null)
        PlayerResponse adminPlayerResponse = createValidPlayerWithAdminRole();
        PlayerResponse userPlayerResponse = createValidPlayerWithUserRole();
        List<PlayerResponse> listPlayersResponse = playerControllerRequest.getAllPlayers(HttpStatus.SC_OK);

        PlayerResponse foundSupervisor = listPlayersResponse.stream()
                .filter(p -> p.getId().equals(SUPERVISOR_ID))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User player not found in list"));
        PlayerResponse foundAdmin = listPlayersResponse.stream()
                .filter(p -> p.getId().equals(adminPlayerResponse.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Admin player not found in list"));
        PlayerResponse foundUser = listPlayersResponse.stream()
                .filter(p -> p.getId().equals(userPlayerResponse.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User player not found in list"));

        assertFieldGetAllPlayers(supervisorData.get(), foundSupervisor);
        assertFieldGetAllPlayers(adminPlayerResponse, foundAdmin);
        assertFieldGetAllPlayers(userPlayerResponse, foundUser);

        deletePlayer(adminPlayerResponse.getId());
        deletePlayer(userPlayerResponse.getId());

        softAssert.get().assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Edit player 'admin' role by supervisor [POSITIVE]")
    public void editPlayerAdminBySupervisor() {
        playerId.set(createValidPlayerWithAdminRole().getId());
        PlayerRequest changedPlayerRequest = adminData.get().setAge(randomAge.get());
        PlayerResponse playerResponse = playerControllerRequest.updatePlayer(changedPlayerRequest, SUPERVISOR, playerId.get(), HttpStatus.SC_OK);
        deletePlayer(playerId.get());
        Assert.assertEquals(playerResponse.getAge(), randomAge.get());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Edit player 'user' role by supervisor [POSITIVE]")
    public void editPlayerUserBySupervisor() {
        playerId.set(createValidPlayerWithUserRole().getId());
        PlayerRequest changedPlayerRequest = userData.get().setAge(randomAge.get());
        PlayerResponse playerResponse = playerControllerRequest.updatePlayer(changedPlayerRequest, SUPERVISOR, playerId.get(), HttpStatus.SC_OK);
        deletePlayer(playerId.get());
        Assert.assertEquals(playerResponse.getAge(), randomAge.get());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Edit player 'user' role by admin [POSITIVE]")
    public void editPlayerUserByAdmin() {
        playerId.set(createValidPlayerWithUserRole().getId());
        PlayerRequest changedPlayerRequest = userData.get().setAge(randomAge.get());
        PlayerResponse playerResponse = playerControllerRequest.updatePlayer(changedPlayerRequest, ADMIN, playerId.get(), HttpStatus.SC_OK);
        deletePlayer(playerId.get());
        Assert.assertEquals(playerResponse.getAge(), randomAge.get());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Edit player 'admin' role by user [NEGATIVE]")
    public void editPlayerAdminByUser() {
        playerId.set(createValidPlayerWithAdminRole().getId());
        PlayerRequest changedPlayerRequest = adminData.get().setAge(randomAge.get());
        playerControllerRequest.updatePlayer(changedPlayerRequest, ADMIN, playerId.get(), HttpStatus.SC_FORBIDDEN);
        deletePlayer(playerId.get());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Edit player 'supervisor' role by admin [NEGATIVE]")
    public void editPlayerSupervisorByAdmin() {
        PlayerRequest changedPlayerRequest = adminData.get().setAge(randomAge.get());
        playerControllerRequest.updatePlayer(changedPlayerRequest, ADMIN, SUPERVISOR_ID, HttpStatus.SC_FORBIDDEN);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Edit player 'supervisor' role by user [NEGATIVE]")
    public void editPlayerSupervisorByUser() {
        PlayerRequest changedPlayerRequest = adminData.get().setAge(randomAge.get());
        playerControllerRequest.updatePlayer(changedPlayerRequest, USER, SUPERVISOR_ID, HttpStatus.SC_FORBIDDEN);
    }

    private void assertFieldPlayerRequestResponse(PlayerRequest playerRequest, PlayerResponse playerResponse) {
        softAssert.get().assertEquals(playerResponse.getAge(), playerRequest.getAge(), "Age does not match");
        softAssert.get().assertEquals(playerResponse.getGender(), playerRequest.getGender(), "Gender does not match");
        softAssert.get().assertEquals(playerResponse.getLogin(), playerRequest.getLogin(), "Login does not match");
        softAssert.get().assertEquals(playerResponse.getPassword(), playerRequest.getPassword(), "Password does not match");
        softAssert.get().assertEquals(playerResponse.getRole(), playerRequest.getRole(), "Role does not match");
        softAssert.get().assertEquals(playerResponse.getScreenName(), playerRequest.getScreenName(), "Screen Name does not match");
    }

    private void assertFieldGetAllPlayers(PlayerResponse responseActual, PlayerResponse responseExpected) {
        softAssert.get().assertEquals(responseActual.getId(), responseExpected.getId(), "Player ID does not match");
        softAssert.get().assertEquals(responseActual.getScreenName(), responseExpected.getScreenName(), "Screen name does not match");
        softAssert.get().assertEquals(responseActual.getGender(), responseExpected.getGender(), "Gender does not match");
        softAssert.get().assertEquals(responseActual.getAge(), responseExpected.getAge(), "Age does not match");
        softAssert.get().assertEquals(responseActual.getRole(), responseExpected.getRole(), "Role does not match");
    }


    private void assertFieldPlayerResponseResponse(PlayerResponse responseActual, PlayerResponse responseExpected) {
        softAssert.get().assertEquals(responseActual.getId(), responseExpected.getId(), "PlayerId does not match");
        assertFieldPlayerRequestResponse(fromResponse(responseActual), responseExpected);
    }

    private void deletePlayer(String playerId) {
        playerControllerRequest.deletePlayer(playerId, "supervisor", HttpStatus.SC_NO_CONTENT);
    }

}
