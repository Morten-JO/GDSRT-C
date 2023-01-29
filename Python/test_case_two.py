from gdsrt import Gdsrt
from tradeitem import TradeItem
import time

#Test case two
#Have "rwter" trade a bunch of individuals a various amounts of 1 items(represent gold)
#Requesting web-graph on that "rwter" should list all of the trades that was flagged.
#Furthermore, there will be added trades in there that should not be flagged(could be normal trades)
#Goal: the outputted report should contain the trades for the individuals in "suspicious_traders" array

def main():
    #Testing is done on localhost
    gdsrt = Gdsrt("localhost", 1234)
    gdsrt.start_connection()
    
    rwter_name = "rwtname"
    suspicious_traders = ["traderOne","traderTwo","traderThree","traderFour","traderFive","traderSix"]
    non_suspicious_traders = ["traderSeven", "traderEight"]
    coin_trade_id = "coins"
    amount_values = [500000, 2500000, 10000000, 1000000, 200000, 750000]
    
    #Equal trades, should not be flagged
    list_one = []
    list_two = []
    for i in range(5):
        item = TradeItem(""+str(i),2)
        list_one.append(item)
        list_two.append(item)
    
    #Perform suspicious trades
    index = 0
    for sus in suspicious_traders:
        list_temp = []
        list_trading = []
        item = TradeItem(coin_trade_id, amount_values[index])
        index += 1
        list_trading.append(item)
        gdsrt.send_trade(rwter_name, sus, list_trading, list_temp)
    
    #Perform non-suspicious trades
    for non_sus in non_suspicious_traders:
        gdsrt.send_trade(rwter_name, non_sus, list_one, list_two)
    
    #Request graph
    gdsrt.request_web_graph(rwter_name, 7, 5)
    
    time.sleep(5)
    
    gdsrt.close_connection()

if __name__ == "__main__":
    main()