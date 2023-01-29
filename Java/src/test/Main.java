package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connector.GDSRTConnector;
import dto.TradeItem;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		//Create the object
		GDSRTConnector connector = new GDSRTConnector("localhost", 1234);
		//Create the different data objects needed
		connector.construct();
		//Start connection to the service
		connector.startConnection();
		
		//Make data, aka list of trade items for each person
		List<TradeItem> firstItems = new ArrayList<>();
		firstItems.add(new TradeItem("1", 1));
		firstItems.add(new TradeItem("2", 2));
		List<TradeItem> secondItems = new ArrayList<>();
		secondItems.add(new TradeItem("3", 1));
		secondItems.add(new TradeItem("4", 4));
		
		//Send a trade to the service, (userOne, userTwo, list)
		connector.addTrade("traderOne", "traderTwo", firstItems, secondItems);
		
		//Afterwards, a request to make a report on a specific user can be made
		//connector.requestWebGraph("traderOne", 6, 10);
	}
}
