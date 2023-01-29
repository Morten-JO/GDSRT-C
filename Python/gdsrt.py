import socket
import threading
import encryptionhelper
import _thread
from tradeitem import TradeItem

class Gdsrt:

    data_retrieved = []

    def __init__(self, host, port, encryption = False, pk_file="", socket_connection=None):
        self.host = host
        self.port = port
        self.encryption = encryption
        self.pk_file = pk_file
        self.socket_connection = socket_connection

    def start_connection(self):
        self.socket_connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket_connection.connect((self.host, self.port))
        self.socket_connection.sendall(b"cock\n")
        _thread.start_new_thread(self.recv_thread, (self.socket_connection,))
        self.socket_connection.sendall(b"what the fuck\n")

    def send_trade_with_string(self, trade: str):
        if self.encryption:
            trade = EncryptionHelper.EncryptWithPk(trade, self.pk_file)
        transfer_msg = "TR "+trade+'\n'
        self.socket_connection.sendall(transfer_msg.encode())

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
            stringed_trade += "traderTwo"+str(item_iterator)+"="+item.itemId+":"+str(item.quantity)
            if len(itemsTwo) > item_iterator:
                stringed_trade += ","
            item_iterator += 1
        stringed_trade += "}"
        self.send_trade_with_string(stringed_trade)
    
    def request_web_graph(self, trader: str, warning_level: int, layers: int):
        msg = trader+" "+str(warning_level)+" "+str(layers)
        self.send_web_graph_request(msg)
    
    def send_web_graph_request(self, msg):
        if self.encryption:
            msg = EncryptionHelper.EncryptWithPk(msg, self.pk_file)
        transfer_msg = "UTG "+msg+'\n'
        self.socket_connection.sendall(transfer_msg.encode())
        
    def recv_thread(self, s):
        while True:
            data = s.recv(1024)
            self.data_retrieved.append(data)
    
    def close_connection(self):
        if self.socket_connection != None:
            self.socket_connection.close()