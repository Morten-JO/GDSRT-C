from gdsrt import Gdsrt
from tradeitem import TradeItem
import time

def main():
    gdsrt = Gdsrt("localhost", 1234)
    #try:
    gdsrt.start_connection()
    list_one = []
    list_two = []
    for i in range(5):
        item = TradeItem(""+str(i),2)
        list_one.append(item)
        list_two.append(item)
    gdsrt.send_trade("traderOne", "traderTwo", list_one, list_two)
    gdsrt.request_web_graph("traderOne", 0, 5)
    
    time.sleep(5)
    
    gdsrt.close_connection()

if __name__ == "__main__":
    main()