package com.qa.democart.factory;

import java.io.FileInputStream;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	private static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverFactory.class));
	public WebDriver driver;
	public Properties prop ;
	ProfileManager profileManager;
	public static ThreadLocal<WebDriver> tDriver = new ThreadLocal<WebDriver>();
	
	/*
	 * @param browserName
	 * @return this returns WebDriver reference on the basis of given browser 
	 */
	
	public WebDriver init_driver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		LOGGER.info("browser name is: " + browserName);
		profileManager = new ProfileManager(prop);
		
		if(browserName.contains("chrome")) {
			
			LOGGER.info("setup chrome browser");
			WebDriverManager.chromedriver().setup();
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("chrome");
				
			}else {
				tDriver.set(new ChromeDriver(profileManager.getChromeOptions()));
			}
			
		}else if(browserName.contains("firefox")) {
			
			LOGGER.info("setup FF browser");
			WebDriverManager.firefoxdriver().setup();
			
			if(browserName.contains("chrome")) {
				WebDriverManager.chromedriver().setup();
				if(Boolean.parseBoolean(prop.getProperty("remote"))) {
					init_remoteDriver("firefox");
					
				}else {
					tDriver.set(new FirefoxDriver());
				}
			
		}else {
			LOGGER.info("Browser name not match");
		}		
	 }
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}	
		
	public void init_remoteDriver(String browserName) {
		
		if(browserName.equals("chrome")) {
			
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, profileManager.getChromeOptions());
			
			try {
				tDriver.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else if(browserName.equals("firefox")) {
			
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			try {
				tDriver.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/*
	 * get Driver using ThreadLocal
	 */
	
	public static synchronized WebDriver getDriver(){
		return tDriver.get();
	}
	
	/*
	 * This method will initialize the properties from the config.properties file
	 */

	public Properties init_prop() {
		prop = null;

		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(ip);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			LOGGER.error("File Not found at the given location....");
		}catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	} 
	
	/**
	 * this is method is used to take the screenshot and it will return the path 
	 * of the screenshot 
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
