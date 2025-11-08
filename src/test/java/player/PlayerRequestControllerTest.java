package player;

import data.PlayerDataProvider;
import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static dto.reponse.PlayerResponse.getStaticSupervisorData;
import static dto.request.PlayerRequest.*;

@Feature("Player controller methods")
public class PlayerRequestControllerTest extends BaseTest {

    private final String user = "user";
    private final String admin = "admin";
    private final String supervisor = "supervisor";
    private final String supervisorId = "1";

    @Test(description = "Create player with valid data [POSITIVE]", dataProvider = "validPlayerData", dataProviderClass = PlayerDataProvider.class)
    public void createPlayerValidDataTest(PlayerRequest playerRequest, int statusCode) {
        PlayerResponse playerResponse = playerControllerRequest.createPlayer(playerRequest, statusCode);
        softAssert.assertNotNull(playerResponse.getId(), "ID should not be null");
        assertFieldPlayerRequestResponse(playerRequest, playerResponse);
        playerId.set(playerResponse.getId());
        deletePlayer(playerId.get());
        softAssert.assertAll();
    }

    @Test(description = "Create player with invalid data [NEGATIVE]", dataProvider = "invalidPlayerData", dataProviderClass = PlayerDataProvider.class)
    public void createPlayerInvalidDataTest(PlayerRequest playerRequest, int statusCode) {
        PlayerResponse playerResponse = playerControllerRequest.createPlayer(playerRequest, statusCode);
        Assert.assertEquals(playerResponse.getContentLength(), "0"); //assert that returned response is empty, player not created
    }

    @Test(description = "Create player with non unique fields [NEGATIVE]", dataProvider = "nonUniqueLoginAndScreenNameData", dataProviderClass = PlayerDataProvider.class)
    public void createPlayerUniqueFieldTest(PlayerRequest playerRequest, int statusCode) {
        PlayerResponse playerResponse = playerControllerRequest.createPlayer(playerRequest, statusCode);
        Assert.assertEquals(playerResponse.getContentLength(), "0");
    }

    @Test(description = "Delete player 'admin' role by supervisor [POSITIVE]")
    public void deletePlayerAdminBySupervisor() {
        playerId.set(playerControllerRequest.createValidPlayerWithAdminRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), supervisor, 204);
    }

    @Test(description = "Delete player 'user' role by supervisor [POSITIVE]")
    public void deletePlayerUserBySupervisor() {
        playerId.set(playerControllerRequest.createValidPlayerWithUserRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), supervisor, 204);
    }

    @Test(description = "Delete player 'user' role by admin [POSITIVE]")
    public void deletePlayerUserByAdmin() {
        playerId.set(playerControllerRequest.createValidPlayerWithUserRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), admin, 204);
    }

    @Test(description = "Delete player 'supervisor' role by admin [NEGATIVE]")
    public void deletePlayerSupervisorByAdmin() {
        playerControllerRequest.deletePlayer(supervisorId, admin, 403);
    }

    @Test(description = "Delete player 'admin' role by user [NEGATIVE]")
    public void deletePlayerAdminByUser() {
        playerId.set(playerControllerRequest.createValidPlayerWithAdminRole().getId());
        playerControllerRequest.deletePlayer(playerId.get(), user, 403);
    }

    @Test(description = "Delete player 'supervisor' role by user [NEGATIVE]")
    public void deletePlayerSupervisorByUser() {
        playerControllerRequest.deletePlayer(supervisorId, user, 403);
    }

    @Test(description = "Get data of supervisor role player [POSITIVE]")
    public void getSupervisorData() {
        PlayerResponse playerResponse = playerControllerRequest.getPlayerDataById(supervisorId, 200);
        assertFieldPlayerResponseResponse(getStaticSupervisorData(), playerResponse);
        softAssert.assertAll();
    }

    @Test(description = "Get data of admin role player [POSITIVE]")
    public void getAdminData() { //will fail due to bug (some response fields from create method are null)
        PlayerResponse playerResponse = playerControllerRequest.createValidPlayerWithAdminRole();
        PlayerResponse playerResponse2 = playerControllerRequest.getPlayerDataById(playerResponse.getId(), 200);
        assertFieldPlayerResponseResponse(playerResponse2, playerResponse);
        playerId.set(playerResponse.getId());
        deletePlayer(playerId.get());
        softAssert.assertAll();
    }

    @Test(description = "Get data of user role player [POSITIVE]")
    public void getUserData() { //will fail due to bug (some response fields from create method are null)
        PlayerResponse playerResponse = playerControllerRequest.createValidPlayerWithUserRole();
        PlayerResponse playerResponse2 = playerControllerRequest.getPlayerDataById(playerResponse.getId(), 200);
        assertFieldPlayerResponseResponse(playerResponse2, playerResponse);
        playerId.set(playerResponse.getId());
        deletePlayer(playerId.get());
        softAssert.assertAll();
    }

    @Test(description = "Get data of unexisted player [NEGATIVE]")
    public void getUnexistedPlayerData() {
        PlayerResponse playerResponse = playerControllerRequest.getPlayerDataById("987654321", 200);
        Assert.assertEquals(playerResponse.getContentLength(), "0");
    }

    @Test(description = "Get data of all players [POSITIVE]")
    public void getDataAllPlayers() { //will fail due to bug (some response fields from create method are null)
        PlayerResponse adminPlayerResponse = playerControllerRequest.createValidPlayerWithAdminRole();
        PlayerResponse userPlayerResponse = playerControllerRequest.createValidPlayerWithUserRole();
        List<PlayerResponse> listPlayersResponse = playerControllerRequest.getAllPlayers(200);

        PlayerResponse foundSupervisor = listPlayersResponse.stream()
                .filter(p -> p.getId().equals(supervisorId))
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

        assertFieldGetAllPlayers(getStaticSupervisorData(), foundSupervisor);
        assertFieldGetAllPlayers(adminPlayerResponse, foundAdmin);
        assertFieldGetAllPlayers(userPlayerResponse, foundUser);

        deletePlayer(adminPlayerResponse.getId());
        deletePlayer(userPlayerResponse.getId());

        softAssert.assertAll();
    }

    @Test(description = "Edit player 'admin' role by supervisor [POSITIVE]")
    public void editPlayerAdminBySupervisor() {
        playerId.set(playerControllerRequest.createValidPlayerWithAdminRole().getId());
        PlayerRequest changedPlayerRequest = getDefaultPlayerAdminRole().setAge(randomAge.get());
        PlayerResponse playerResponse = playerControllerRequest.updatePlayer(changedPlayerRequest, supervisor, playerId.get(), 200);
        deletePlayer(playerId.get());
        Assert.assertEquals(playerResponse.getAge(), randomAge.get());
    }

    @Test(description = "Edit player 'user' role by supervisor [POSITIVE]")
    public void editPlayerUserBySupervisor() {
        playerId.set(playerControllerRequest.createValidPlayerWithUserRole().getId());
        PlayerRequest changedPlayerRequest = getDefaultPlayerUserRole().setAge(randomAge.get());
        PlayerResponse playerResponse = playerControllerRequest.updatePlayer(changedPlayerRequest, supervisor, playerId.get(), 200);
        deletePlayer(playerId.get());
        Assert.assertEquals(playerResponse.getAge(), randomAge.get());
    }

    @Test(description = "Edit player 'user' role by admin [POSITIVE]")
    public void editPlayerUserByAdmin() {
        playerId.set(playerControllerRequest.createValidPlayerWithUserRole().getId());
        PlayerRequest changedPlayerRequest = getDefaultPlayerUserRole().setAge(randomAge.get());
        PlayerResponse playerResponse = playerControllerRequest.updatePlayer(changedPlayerRequest, admin, playerId.get(), 200);
        deletePlayer(playerId.get());
        Assert.assertEquals(playerResponse.getAge(), randomAge.get());
    }

    @Test(description = "Edit player 'admin' role by user [NEGATIVE]")
    public void editPlayerAdminByUser() {
        playerId.set(playerControllerRequest.createValidPlayerWithAdminRole().getId());
        PlayerRequest changedPlayerRequest = getDefaultPlayerAdminRole().setAge(randomAge.get());
        playerControllerRequest.updatePlayer(changedPlayerRequest, admin, playerId.get(), 403);
        deletePlayer(playerId.get());
    }

    @Test(description = "Edit player 'supervisor' role by admin [NEGATIVE]")
    public void EditPlayerSupervisorByAdmin() {
        PlayerRequest changedPlayerRequest = getDefaultPlayerAdminRole().setAge(randomAge.get());
        playerControllerRequest.updatePlayer(changedPlayerRequest, admin, supervisorId, 403);
    }

    @Test(description = "Edit player 'supervisor' role by user [NEGATIVE]")
    public void EditPlayerSupervisorByUser() {
        PlayerRequest changedPlayerRequest = getDefaultPlayerAdminRole().setAge(randomAge.get());
        playerControllerRequest.updatePlayer(changedPlayerRequest, user, supervisorId, 403);
    }

    private void assertFieldPlayerRequestResponse(PlayerRequest playerRequest, PlayerResponse playerResponse) {
        softAssert.assertEquals(playerResponse.getAge(), playerRequest.getAge(), "Age does not match");
        softAssert.assertEquals(playerResponse.getGender(), playerRequest.getGender(), "Gender does not match");
        softAssert.assertEquals(playerResponse.getLogin(), playerRequest.getLogin(), "Login does not match");
        softAssert.assertEquals(playerResponse.getPassword(), playerRequest.getPassword(), "Password does not match");
        softAssert.assertEquals(playerResponse.getRole(), playerRequest.getRole(), "Role does not match");
        softAssert.assertEquals(playerResponse.getScreenName(), playerRequest.getScreenName(), "Screen Name does not match");
    }

    private void assertFieldGetAllPlayers(PlayerResponse responseActual, PlayerResponse responseExpected) {
        softAssert.assertEquals(responseActual.getId(), responseExpected.getId(), "Player ID does not match");
        softAssert.assertEquals(responseActual.getScreenName(), responseExpected.getScreenName(), "Screen name does not match");
        softAssert.assertEquals(responseActual.getGender(), responseExpected.getGender(), "Gender does not match");
        softAssert.assertEquals(responseActual.getAge(), responseExpected.getAge(), "Age does not match");
    }


    private void assertFieldPlayerResponseResponse(PlayerResponse responseActual, PlayerResponse responseExpected) {
        softAssert.assertEquals(responseActual.getId(), responseExpected.getId(), "PlayerId does not match");
        assertFieldPlayerRequestResponse(fromResponse(responseActual), responseExpected);
    }

    private void deletePlayer(String playerId) {
        playerControllerRequest.deletePlayer(playerId, "supervisor", 204);
    }

    //if you want to verify thread count uncomment this method and search in logs 'TestNG-test-PlayerRequestControllerTest-n', where thread count
     @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod Thread: " + Thread.currentThread().getName());
    }
}
