package principal;

import java.io.*;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientHandler {
	
    static public void connect(InterfaceHandler ui, String ipAddress, String port) {
    	try {    
    		Socket socket = new Socket(ipAddress, Integer.parseInt(port));
            
            BufferedReader reader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ui.setWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            ui.alterOnConnected();
            ui.getConnectionDialog().dispose();
            
            new Thread(() -> {
                while (true) {
                    try {
                        String message = reader.readLine();
                        if (message != null) {
                            ui.appendToTextArea("Outro: " + message);
                        }
                    } catch (IOException ex) {
                    	ui.alterOnDisconnected();
                    	disconnect(socket);
                        ex.printStackTrace();
                        break;
                    }
                }
            }).start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ui.getConnectionDialog(), "Erro ao conectar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    static public void disconnect(Socket socket)
    {
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
