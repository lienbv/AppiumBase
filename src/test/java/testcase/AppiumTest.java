package testcase;

import base.AppiumServerManager;
import base.ConfigVar;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.URL;
import java.util.Objects;

public class AppiumTest {

    private AndroidDriver<MobileElement> driver;

    @BeforeClass
    public void setUp(){
        AppiumServerManager.killProcessOnPort(ConfigVar.PORT);
        if (!AppiumServerManager.isServerRunning(ConfigVar.PORT)) {
            AppiumServerManager.startServer();
            AppiumServerManager.waitForServerToStart(ConfigVar.PORT);
        }
    }

    @Test
    public void test() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigVar.PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigVar.AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigVar.DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigVar.PLATFORM_VERSION);
        driver = new AndroidDriver<>(new URL(Objects.requireNonNull(AppiumServerManager.getServerUrl())), capabilities);

    }

    @AfterClass
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            AppiumServerManager.stopServer();
        }
    }
}

