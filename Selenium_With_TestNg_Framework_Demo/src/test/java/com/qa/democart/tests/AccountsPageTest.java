package com.qa.democart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.democart.base.BaseTest;
import com.qa.democart.utils.Constants;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accountsPageSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(priority = 1)
	public void accountPageTitleTest() {
		String title = accountsPage.getAccountPageTitle();
		System.out.println("acc page title is : "+ title);
		Assert.assertEquals(title, Constants.ACCOUNT_PAGE_TITLE);
	}
	
	@Test(priority = 2)
	public void verifyAccPageHeaderTest() {
		String accHeader = accountsPage.getHeaderValue();
		System.out.println("acc page title is : "+ accHeader);
		Assert.assertEquals(accHeader, Constants.ACCOUNT_PAGE_HEADER);
	}
	
	@Test(priority = 3)
	public void verifyAccSectionsCountTest() {
		Assert.assertTrue(accountsPage.getAccountSectionsCount() == Constants.ACCOUNT_SECTIONS_COUNT);
	}
	
	@Test(priority = 4)
	public void verifyAccountSectionsListTest() {
	 List<String> accSecList = accountsPage.getAccountSectionsList();
	 Assert.assertEquals(accSecList, Constants.getExpectedAccountsSectionsList());
	}
	
	@DataProvider
	public Object[][] productTestData(){
		return new Object[][] {
			{"iMac"},{"Macbook"},{"iPhone"}
							};
	}
	
	
	@Test(priority = 5, dataProvider = "productTestData")
	public void searchTest(String productName	) {
		Assert.assertTrue(accountsPage.doSearch(productName));
	}
	
}
