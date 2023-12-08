package principal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
	
	static private ServerSocket serverSocket;
	
    static public void initializeServer(InterfaceHandler ui) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(54321);
                startConnection(ui);  
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    static public void startConnection(InterfaceHandler ui) throws IOException
    {
    	Socket socket = serverSocket.accept();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ui.setWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        ui.alterOnConnected();

        while (true) {
            try {
                String message = reader.readLine();
                if (message != null) {
                    ui.appendToTextArea("Outro: " + message);
                }
            } catch (IOException e) {
            	ui.alterOnDisconnected();
                e.printStackTrace();
                startConnection(ui);
                break;
            }
        }
    	
    }
}
