package com.qa.democart.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	
	public static final int ACCOUNT_SECTIONS_COUNT = 4;
	public static final int FORGOTTEN_PWD_LINK_COUNT = 2;
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String ACCOUNT_PAGE_TITLE = "My Account";
	public static final String ACCOUNT_PAGE_HEADER = "Your Store";
	
	public static List<String> accSecList;
	
	public static List<String> getExpectedAccountsSectionsList() {
		accSecList = new ArrayList<String>();
		
		accSecList.add("My Account");
		accSecList.add("My Orders");
		accSecList.add("My Affiliate Account");
		accSecList.add("Newsletter");
		
		return accSecList;
	}

}
