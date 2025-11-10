package player;

import api.PlayerControllerRequest;
import base.BaseTest;
import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;
import helpers.JsonReader;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public abstract class BasePlayerTest extends BaseTest {

    protected PlayerControllerRequest playerControllerRequest;
    protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
    protected ThreadLocal<String> playerId = ThreadLocal.withInitial(() -> null);
    protected final ThreadLocal<String> randomAge = ThreadLocal.withInitial(() -> String.valueOf(
            java.util.concurrent.ThreadLocalRandom.current().nextInt(16, 61)));
    protected ThreadLocal<PlayerResponse> supervisorData = ThreadLocal.withInitial(() -> JsonReader.getPlayer(SUPERVISOR, PlayerResponse.class));
    protected ThreadLocal<PlayerRequest> adminData = ThreadLocal.withInitial(() -> JsonReader.getPlayer(ADMIN, PlayerRequest.class));
    protected ThreadLocal<PlayerRequest> userData = ThreadLocal.withInitial(() -> JsonReader.getPlayer(USER, PlayerRequest.class));


    @BeforeMethod
    public void setUp() {
        playerControllerRequest = new PlayerControllerRequest();
        softAssert.set(new SoftAssert());
        /*if you want to verify thread count uncomment this line and search in logs
        'TestNG-test-PlayerRequestControllerTest-n', where n - thread count*/
        /*System.out.println("BeforeMethod Thread: " + Thread.currentThread().getName());*/
    }

    public PlayerResponse createValidPlayerWithAdminRole() {
        return playerControllerRequest.createPlayer(adminData.get(), HttpStatus.SC_OK);
    }

    public PlayerResponse createValidPlayerWithUserRole() {
        return playerControllerRequest.createPlayer(userData.get(), HttpStatus.SC_OK);
    }
}
