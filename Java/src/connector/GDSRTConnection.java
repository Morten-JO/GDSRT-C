package connector;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import connection.Connection;

public class GDSRTConnection extends Connection{
	
	public GDSRTConnection(Socket socket, BufferedReader reader, PrintWriter writer) {
		super(socket, reader, writer);
	}
	

	@Override
	protected void handleInput(String input) {
		if (input.startsWith("TR ")) {
			String[] values = input.split(" ");
			if(values.length == 2) {
				String msg = values[1];
			}
		}
	}
}
