# Guide
This is a guide that explains the use of the Java client implementation of GDSRT.

## Installing
The stable .jar for use can be found in this repository under [/Java_jar/GDSRT_Java_Client_v1.jar][https://github.com/Morten-JO/GDSRT-C/blob/main/Java_jar/GDSRT_Java_Client_v1.jar].

Import this .jar file as a library into the project you wish to use this in.


## Making the connection
**Examples of the process can be found in:** [Main.java][MainJava] **and** [TestCaseOne][TestCaseJava]  

### Constructing the connector
The object can be created in the following way:  
```
GDSRTConnector connector = new GDSRTConnector(host, port);
```
{: .language-java}
To start the connection to the service, the following must be run:  
```
connector.construct();
connector.startConnection();
```
{: .language-java}
**.construct()** is used to create the required structures for running, including the connection itself, and **.startConnection()** starts the reading/writing to the service.  
**.construct()** can throw either **UnknownHostException** or **IOException**, in which case, it means there is a problem connecting to the service.  

## Sending data

After this, trades can be sent to the service, and the recommended way is to pass the two tradersNames, and two different lists of the items, as follows:  
```
String playerOneName = "";
String playerTwoName = "";

//Create list & data
List<TradeItem> playerOneItems = new ArrayList<>();
int firstItemQuantity = 1;
TradeItem firstItem = new TradeItem("itemName", firstItemQuantity);
playerOneItems.add(firstItem);

List<TradeItem> playerTwoItems = new ArrayList<>();
int secondItemQuantity = 2;
TradeItem secondItem = new TradeItem("itemNameTwo", secondItemQuantity);
playerTwoItems.add(firstItem);

//Send trade
connector.addTrade(playerOneName, playerTwoName, playerOneItems, playerTwoItems);
```
{: .language-java}

Althought it is possible to use other methods for adding a trade, suchas a HashMap or a string directly, this way is **HIGHLY** recommended, as the other options requires you to already have the trade formatted, which will be done automatically in this way.  

## Requesting report

You can request the service to provide you a report of a specific user. Ideally you'd probably want to implement this in a different service, such as a admin tool, but the service will support multi-connections, so it should be fine.  
You can request a report by using the following:  
```
String playerName = "";
int warningLevelFilter = 6;
int layers = 5;
connector.requestWebGraph(playerName, warningLevelFilter, layers);
```
{: .language-java}

This will generate a report to the service, which can then be retrieved from there.  
**warningLevelFilter** is used to filter what level of warninngs 0-10 to account for when making the report. All trades under that level will be omitted.  
**layers** is used to define how deep the recursion should look. So for example layers = 3, should cause the service to scan:  
User's(1) trades -> User's(2) trades that traded with (1) -> User's(3) trades that traded with (2).  

<!-- Identifiers, in alphabetical order -->

[MainJava]: https://github.com/Morten-JO/GDSRT-C/blob/main/Java/src/test/Main.java
[TestCaseJava]: https://github.com/Morten-JO/GDSRT-C/blob/main/Java/src/test/TestCaseOne.java
