from gdsrt_morten_jo.gdsrt import Gdsrt
from gdsrt_morten_jo.gdsrt import TradeItem
import time

def main():
    #Connect
    gdsrt = Gdsrt("localhost", 1234)
    #Start connection
    gdsrt.start_connection()
    
    #Create data
    list_one = []
    list_two = []
    for i in range(5):
        item = TradeItem(""+str(i),2)
        list_one.append(item)
        list_two.append(item)
    #Send trade
    gdsrt.send_trade("traderOne", "traderTwo", list_one, list_two)
    #Request report
    gdsrt.request_web_graph("traderOne", 0, 5)
    
    time.sleep(5)
    
    #Close
    gdsrt.close_connection()

if __name__ == "__main__":
    main()