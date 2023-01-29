package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connector.GDSRTConnector;
import dto.TradeItem;

public class TestCaseOne {

	//Test case 1: 
	//1. Train items with alot of entries(Done through price flooding)
	//2. Create a "chain" of suspicious trades muling(1 player trades uneven to x, then that player trades onwwards)
	//Goal: Accounts muled will be viewed through chain when exporting graph of users.
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		GDSRTConnector connector = new GDSRTConnector("localhost", 1234);
		connector.construct();
		connector.startConnection();
		
		
		
		Map<String, Integer> itemsAndPreferredPrice = new HashMap<>();
		itemsAndPreferredPrice.put("itemOne", 4200);
		itemsAndPreferredPrice.put("itemTwo", 200);
		itemsAndPreferredPrice.put("itemThree", 2500);
		itemsAndPreferredPrice.put("itemFour", 3850);
		itemsAndPreferredPrice.put("itemFive", 50);
		itemsAndPreferredPrice.put("itemSix", 550);
		itemsAndPreferredPrice.put("itemSeven", 250);
		itemsAndPreferredPrice.put("itemEight", 780);
		itemsAndPreferredPrice.put("itemNine", 920);
		itemsAndPreferredPrice.put("itemTen", 1240);
		itemsAndPreferredPrice.put("itemEleven", 1750);
		itemsAndPreferredPrice.put("itemTwelve", 2120);
		itemsAndPreferredPrice.put("itemThirteen", 3100);
		itemsAndPreferredPrice.put("itemFourteen", 3750);
		itemsAndPreferredPrice.put("itemFifteen", 2750);
		
		
		String[] usersToMuleThrough = new String[] {"userOne", "userTwo", "userThree", "userFour", "userFive", "userSix", "userSeven"};
		
		List<TradeItem> firstItems = new ArrayList<>();
		firstItems.add(new TradeItem("itemOne", 3));
		firstItems.add(new TradeItem("itemSix", 7));
		firstItems.add(new TradeItem("itemEleven", 5));
		List<TradeItem> secondItems = new ArrayList<>();
		
		for(int i = 0; i < usersToMuleThrough.length - 1; i++) {
			connector.addTrade(usersToMuleThrough[i], usersToMuleThrough[i+1], firstItems, secondItems);
		}
		
		connector.requestWebGraph("userOne", 6, 10);
		
	}
	
	
	
}
