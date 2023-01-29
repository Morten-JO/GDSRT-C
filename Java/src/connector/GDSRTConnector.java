package connector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import dto.TradeItem;
import util.EncryptionHelper;

public class GDSRTConnector {
	
	private final boolean sendWithEncryption;
	private PublicKey serversPublicKey;
	private String ip;
	private int port;
	
	private GDSRTConnection gdsrtConnection;

	public static final int INCOMING_SERVER_PORT = 1239;
	
	
	public GDSRTConnector(String ip, int port) throws IOException, ClassNotFoundException {
		this.ip = ip;
		this.port = port;
		this.sendWithEncryption = false;
	}
	
	public GDSRTConnector(String ip, int port, File publicKeyFile) throws IOException, ClassNotFoundException {
		this.ip = ip;
		this.port = port;
		this.sendWithEncryption = true;
		try {
			this.serversPublicKey = EncryptionHelper.getKeyFileToPublicKey(publicKeyFile);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Failed to load the public key file, are you sure you specified the right location?");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public boolean run() throws UnknownHostException, IOException{
		Socket gdscrtSocket = new Socket(ip, port);
		BufferedReader reader = new BufferedReader(new InputStreamReader(gdscrtSocket.getInputStream()));
		PrintWriter writer = new PrintWriter(gdscrtSocket.getOutputStream(), true);	
		gdsrtConnection = new GDSRTConnection(gdscrtSocket, reader, writer);
		return true;
	}

	public void addToDebug(String string) {

	}
	
	public boolean addTrade(String traderOne, String traderTwo, List<TradeItem> traderOneItems, List<TradeItem> traderTwoItems) {
		HashMap<String, String> map = new HashMap<>();
		map.put("traderOne", traderOne);
		map.put("traderTwo", traderTwo);
		int itemIterator = 1;
		for(TradeItem item : traderOneItems) {
			map.put("traderOne"+itemIterator, item.getItemId()+":"+item.getQuantity());
			itemIterator += 1;
		}
		itemIterator = 1;
		for(TradeItem item : traderTwoItems) {
			map.put("traderTwo"+itemIterator, item.getItemId()+":"+item.getQuantity());
			itemIterator += 1;
		}
		return addTrade(map);
	}
	
	public void requestWebGraph(String traderName, int warning, int layers) {
		String msg = traderName+" "+warning+" "+layers;
		addMessageForTransportation(msg, "UTG");
	}
	
	public boolean addTrade(Map<String, String> map) {
		String mapAsString = map.keySet().stream()
			      .map(key -> key + "=" + map.get(key))
			      .collect(Collectors.joining(",", "{", "}"));
	    return addMessageForTransportation(mapAsString.toString(), "TR");
	}
	
	public boolean addTrade(String string) {
		return addMessageForTransportation(string, "TR");
	}
	
	private boolean addMessageForTransportation(String msg, String prefix) {
		if(sendWithEncryption) {
			try {
				String encryptedMsg = EncryptionHelper.encryptMsgWithPublicKey(msg, serversPublicKey);
				gdsrtConnection.addMessageToSend(prefix+" "+encryptedMsg);
				
				return true;
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
					| NoSuchPaddingException e) {
				e.printStackTrace();
				return false;
			}
			
		} else {
			return gdsrtConnection.addMessageToSend(prefix+" "+msg);
		}
	}
	
	public GDSRTConnection getConnection() {
		return gdsrtConnection;
	}
}
