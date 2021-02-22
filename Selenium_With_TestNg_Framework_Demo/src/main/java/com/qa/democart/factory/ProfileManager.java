package com.qa.democart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;

public class ProfileManager {

		private Properties prop ;
		private ChromeOptions co;
		
		public ProfileManager(Properties prop) {
			this.prop = prop;
		}
		
		public ChromeOptions getChromeOptions() {
			co = new ChromeOptions();
			
			if(prop.getProperty("headless").equals("true")) {co.addArguments("--headless");}
			if(prop.getProperty("incognito").equals("true")) {co.addArguments("--incognito");}
			return co;
		}
}
