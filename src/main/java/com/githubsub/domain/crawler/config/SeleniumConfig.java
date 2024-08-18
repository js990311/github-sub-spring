package com.githubsub.domain.crawler.config;

import com.githubsub.domain.crawler.service.crawling.driver.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
public class SeleniumConfig {
    @Value("${selenium.url}")
    private String gridUrl;

    @Bean
    public WebDriverFactory webDriverFactory(){
        return new WebDriverFactory(gridUrl);
    }
}
