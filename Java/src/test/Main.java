package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connector.GDSRTConnector;
import dto.TradeItem;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		GDSRTConnector connector = new GDSRTConnector("localhost", 1234);
		connector.run();
		connector.getConnection().startConnection();
		List<TradeItem> firstItems = new ArrayList<>();
		firstItems.add(new TradeItem("1", 1));
		firstItems.add(new TradeItem("2", 2));
		List<TradeItem> secondItems = new ArrayList<>();
		secondItems.add(new TradeItem("3", 1));
		secondItems.add(new TradeItem("4", 4));
		connector.addTrade("traderOne", "traderTwo", firstItems, secondItems);
	}
}
