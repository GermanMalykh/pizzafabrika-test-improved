package drivers;

import com.codeborne.selenide.WebDriverProvider;

import common_configs.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;

public class MobileDriver implements WebDriverProvider {

    public static URL getAppiumServerUrl() {
        try {
            return new URI(Config.getProperty("mobile.appium.url")).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + Config.getProperty("mobile.appium.url"), e);
        }
    }

    public String getAppPath() {
        String appPath = "src/test/resources/example.apk";
        File app = new File(appPath);
        return app.getAbsolutePath();
    }

    @NotNull
    @Override
    public WebDriver createDriver(@NotNull Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);

        options.setAutomationName(ANDROID_UIAUTOMATOR2)
                .setPlatformName(ANDROID)
                .setDeviceName(Config.getProperty("mobile.device.name"))
                .setPlatformVersion(Config.getProperty("mobile.os.version"))
                .setApp(getAppPath())
                .setAppPackage(Config.getProperty("mobile.app.package"))
                .setAppActivity(Config.getProperty("mobile.app.activity"))
                .setNoSign(true);
        return new AndroidDriver(getAppiumServerUrl(), options);
    }

}
