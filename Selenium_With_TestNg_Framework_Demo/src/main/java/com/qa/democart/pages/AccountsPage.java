package com.qa.democart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil ;
	
	//Locators
	private By header = By.cssSelector("div#logo a");
	private By accountSelectionHeaders = By.cssSelector("div#content h2");
	private By searchText = By.cssSelector("div#search input");
	private By searchButton = By.cssSelector("div#search button");
	private By searchItemsResult = By.cssSelector("div.product-layout .product-thumb");

	//Constructor
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	//Action methods
	public String getAccountPageTitle() {
		return elementUtil.waitForTitleToBe(Constants.ACCOUNT_PAGE_TITLE,5);
	}
	
	public String getHeaderValue() {
		if(elementUtil.doIsDisplayed(header)) {
			return elementUtil.doGetText(header);
		}
		return null;
	}
	
	public int getAccountSectionsCount() {
		return elementUtil.getElements(accountSelectionHeaders).size();
	}
	
	public List<String> getAccountSectionsList() {
		List<String> accountList = new ArrayList<String>();
		List<WebElement> accSectionList = elementUtil.getElements(accountSelectionHeaders);
		for(WebElement e : accSectionList) {
			String secText = e.getText();
			accountList.add(secText);
		}
		return accountList;
	}
	
   public boolean doSearch(String productName) {
	   elementUtil.doSendKeys(searchText, productName);
	   elementUtil.doClick(searchButton);
	   if(elementUtil.getElements(searchItemsResult).size() > 0) {
		   elementUtil.doClear(searchText);
		   return true; 
	   }
	   return false;
   }
	
}
