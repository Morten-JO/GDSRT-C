package dto;

import java.util.ArrayList;
import java.util.List;

public class Trade {

	private String traderOne;
	private String traderTwo;
	private List<TradeItem> itemsOne; //Maybe it should be Object Item(ItemName/ItemId and quantity)
	private List<TradeItem> itemsTwo;

	public Trade() {
		
	}
	public Trade(Trade toCopy) {
		this.traderOne = toCopy.traderOne;
		this.traderTwo = toCopy.traderTwo;
		this.itemsOne = toCopy.itemsOne;
		this.itemsTwo = toCopy.itemsTwo;
	}

	public String getTraderOne() {
		return traderOne;
	}

	public void setTraderOne(String traderOne) {
		this.traderOne = traderOne;
	}

	public String getTraderTwo() {
		return traderTwo;
	}

	public void setTraderTwo(String traderTwo) {
		this.traderTwo = traderTwo;
	}

	public List<TradeItem> getItemsOne() {
		return itemsOne;
	}

	public void setItemsOne(List<TradeItem> itemsOne) {
		this.itemsOne = itemsOne;
	}

	public List<TradeItem> getItemsTwo() {
		return itemsTwo;
	}

	public void setItemsTwo(List<TradeItem> itemsTwo) {
		this.itemsTwo = itemsTwo;
	}
	
	public List<TradeItem> getBothTradeItems(){
		List<TradeItem> list = new ArrayList<>();
		list.addAll(itemsOne);
		list.addAll(itemsTwo);
		return list;
	}
	
	
	
	
}
