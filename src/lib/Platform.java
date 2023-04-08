package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String APPIUM_ANDROID_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String APPIUM_IOS_URL = "http://127.0.0.1:4723/";

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance() {
        if (instance == null) {
            instance =  new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception {
        if(this.isAndroid()) {
            return new AndroidDriver(new URL(APPIUM_ANDROID_URL), this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(new URL(APPIUM_IOS_URL), this.getIOSDesiredCapabilities());
        }
        throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
    }


    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/halynab/courses/JavaAppiumAutomation/apks/org.wikipedia.apk");

        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 14");
        capabilities.setCapability("platformVersion", "16.4");
        capabilities.setCapability("app", "/Users/halynab/courses/JavaAppiumAutomation/apks/Wikipedia693.app");
        capabilities.setCapability("automationName", "XCUITest");

        return capabilities;
    }

    private boolean isPlatform(String myPlatform) {
        String platform = this.getPlatformVar();
        return myPlatform.equals(platform);
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}
