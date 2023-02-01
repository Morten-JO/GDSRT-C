# Guide
This is a guide that explains the use of the Python client implementation of GDSRT.

## Installing
There is only currently built a python package inside the repository, so the installation has to be done locally with the following: 
```
py -m pip install .\dist\gdsrt_morten_jo-1.0.0-py3-none-any.whl
```
Where dist is the following folder: [dist][DistLoc]  
This will cause python to install so it can be run


## Making the connection
**Examples of the process can be found in:** [main.py][MainPython] **and** [TestCaseTwo][TestCasePython]  

### Import
```python
from gdsrt_morten_jo.gdsrt import Gdsrt
from gdsrt_morten_jo.gdsrt import TradeItem
```

### Constructing the connector
The object can be created in the following way:  
```python
gdsrt = Gdsrt(host, port)
```
Alternatively, the service can also be created by:  
```python
gdsrt = Gdsrt(host, port, encryptionEnabled, publicKeyFilePath)
```
**WARNING: This is yet implemented as of writing, so encryption will do nothing, and will cause issues for the service**  

To start the connection to the service, the following must be run:  
```python
gdsrt.start_connection()
```
**.start_connection()** is used to create the required structures for running, including the connection itself, and starting the reading/writing to the service. 


## Sending data

After this, trades can be sent to the service, and the recommended way is to pass the two tradersNames, and two different lists of the items, as follows:  
```python
playerOneName = ""
playerTwoName = ""

//Create list & data
playerOneName = ""
playerTwoName = ""

//Create list & data
player_one_items = []
first_item_quantity = 1
first_item = TradeItem("itemName", first_item_quantity)
player_one_items.append(item)

player_two_items = []
second_item_quantity = 1
second_item = TradeItem("itemNameTwo", second_item_quantity)
player_two_items.append(item)

//Send trade
gdsrt.send_trade(playerOneName,playerTwoName, player_one_items, player_two_items)
```

Althought it is possible to use other methods for adding a trade, suchas a string directly, this way is **HIGHLY** recommended, as the other options requires you to already have the trade formatted, which will be done automatically in this way.  

## Requesting report

You can request the service to provide you a report of a specific user. Ideally you'd probably want to implement this in a different service, such as a admin tool, but the service will support multi-connections, so it should be fine.  
You can request a report by using the following:  
```python
String playerName = "";
int warningLevelFilter = 6;
int layers = 5;
gdsrt.request_web_graph(playerName, warningLevelFilter, layers)
```

This will generate a report to the service, which can then be retrieved from there.  
**warningLevelFilter** is used to filter what level of warninngs 0-10 to account for when making the report. All trades under that level will be omitted.  
**layers** is used to define how deep the recursion should look. So for example layers = 3, should cause the service to scan:  
User's(1) trades -> User's(2) trades that traded with (1) -> User's(3) trades that traded with (2).  

<!-- Identifiers, in alphabetical order -->

[MainPython]: https://github.com/Morten-JO/GDSRT-C/blob/main/Python/main.py
[TestCasePython]: https://github.com/Morten-JO/GDSRT-C/blob/main/Python/test_case_two.py
[DistLoc]: https://github.com/Morten-JO/GDSRT-C/tree/main/Python/gdsrt/dist
