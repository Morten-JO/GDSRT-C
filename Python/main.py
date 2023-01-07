from gdsrt import Gdsrt
from tradeitem import TradeItem

def main():
    gdsrt = Gdsrt("127.0.0.1", 65432)
    try:
        gdsrt.start_connection()
    except:
        pass
    list_one = []
    list_two = []
    for i in range(5):
        item = TradeItem(""+str(i),2)
        list_one.append(item)
        list_two.append(item)
    gdsrt.send_trade("traderOne", "traderTwo", list_one, list_two)

if __name__ == "__main__":
    main()