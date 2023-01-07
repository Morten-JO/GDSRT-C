import socket
import threading
import encryptionhelper
from tradeitem import TradeItem



class Gdsrt:

    #HOST = "127.0.0.1"
    #PORT = 65432
    socket_connection = None

    def __init__(self, host, port, encryption = False, pk_file=""):
        self.host = host
        self.port = port
        self.encryption = encryption
        self.pk_file = pk_file


    def start_connection(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.host, self.port))
            socket_connection = s
            start_new_thread(recv_thread, (s,))

    def send_trade_with_string(self, trade: str):
        print("sending: "+trade)
        if self.encryption:
            trade = EncryptionHelper.EncryptWithPk(trade, self.pk_file)
        self.socket_connection.sendall(b"TR "+trade)

    def send_trade(self, traderOne: str, traderTwo: str, itemsOne: list, itemsTwo: list) -> str:
        stringed_trade = "{"
        stringed_trade += "traderOne="+traderOne+","
        stringed_trade += "traderTwo="+traderTwo+","
        item_iterator = 1
        for item in itemsOne:
            stringed_trade += "traderOne"+str(item_iterator)+"="+item.itemId+":"+str(item.quantity)+","
            item_iterator += 1
        item_iterator = 1
        for item in itemsTwo:
            stringed_trade += "traderOne"+str(item_iterator)+"="+item.itemId+":"+str(item.quantity)
            if len(itemsTwo) > item_iterator:
                stringed_trade += ","
            item_iterator += 1
        stringed_trade += "}"
        self.send_trade_with_string(stringed_trade)

    def recv_thread(self, s):
        while True:
            data = s.recv(1024)
            print("Received data: "+data)
    