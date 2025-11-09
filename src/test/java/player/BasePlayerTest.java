package player;

import api.PlayerControllerRequest;
import base.BaseTest;
import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import utils.JsonReader;

public abstract class BasePlayerTest extends BaseTest {

    protected PlayerControllerRequest playerControllerRequest;
    protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
    protected ThreadLocal<String> playerId = ThreadLocal.withInitial(() -> null);
    protected final ThreadLocal<String> randomAge = ThreadLocal.withInitial(() -> String.valueOf(
            java.util.concurrent.ThreadLocalRandom.current().nextInt(16, 61)));
    protected ThreadLocal<PlayerResponse> supervisorData = ThreadLocal.withInitial(() -> JsonReader.getPlayerResponse(supervisor));
    protected ThreadLocal<PlayerRequest> adminData = ThreadLocal.withInitial(() -> JsonReader.getPlayerRequest(admin));
    protected ThreadLocal<PlayerRequest> userData = ThreadLocal.withInitial(() -> JsonReader.getPlayerRequest(user));

    @BeforeMethod
    public void setUp() {
        playerControllerRequest = new PlayerControllerRequest();
        softAssert.set(new SoftAssert());
        /*if you want to verify thread count uncomment this line and search in logs
        'TestNG-test-PlayerRequestControllerTest-n', where n - thread count*/
        /*System.out.println("BeforeMethod Thread: " + Thread.currentThread().getName());*/
    }
}