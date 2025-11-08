package player;

import api.PlayerControllerRequest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

public abstract class BaseTest {

    protected PlayerControllerRequest playerControllerRequest;
    protected SoftAssert softAssert = new SoftAssert();
    protected static ThreadLocal<String> playerId = ThreadLocal.withInitial(() -> null);
    protected static final ThreadLocal<String> randomAge = ThreadLocal.withInitial(() -> String.valueOf(
            java.util.concurrent.ThreadLocalRandom.current().nextInt(16, 61)));


    @BeforeTest
    protected void setUp() {
        playerControllerRequest = new PlayerControllerRequest();
        softAssert = new SoftAssert();
    }
}
