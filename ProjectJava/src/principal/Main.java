package principal;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			InterfaceHandler interfaceHandler = new InterfaceHandler();		
			ServerHandler.initializeServer(interfaceHandler);
		});	
	}
}
