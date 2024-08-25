package com.githubsub.domain.crawler.service.crawling.driver.factory;

import com.githubsub.domain.crawler.service.crawling.driver.factory.exception.CannotCreateWebDriverException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

@Slf4j
public class WebDriverFactory {
    private final String gridUrl;
    private ThreadLocal<WebDriver> threadLocal= ThreadLocal.withInitial(() -> null);


    public WebDriverFactory(String gridUrl) {
        this.gridUrl = gridUrl;
    }

    public WebDriver getWebDriver() {
        WebDriver webDriver = threadLocal.get();
        if(webDriver == null){
            webDriver = _createDriver();
            threadLocal.set(webDriver);
        }
        return webDriver;
    }

    private WebDriver _createDriver(){
        try {
            URL url = new URI(gridUrl).toURL();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.setCapability("browserName", "chrome");
            WebDriver webDriver;
            webDriver = new RemoteWebDriver(url,chromeOptions);
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            return webDriver;
        }catch (Exception e){
            log.warn(e.getMessage());
            throw new CannotCreateWebDriverException();
        }
    }

    public boolean createDriver(){
        WebDriver webDriver = threadLocal.get();
        if(webDriver == null){
            webDriver = _createDriver();
            threadLocal.set(webDriver);
            return true;
        }else {
            return false;
        }
    }

    public void closeDriver() {
        WebDriver webDriver = threadLocal.get();
        if (webDriver != null) {
            threadLocal.remove();
            webDriver.quit();
        }
    }
}
