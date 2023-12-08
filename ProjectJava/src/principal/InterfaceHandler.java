	package principal;
	
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.io.BufferedWriter;
	import java.io.IOException;
	import java.util.Objects;
	
	public class InterfaceHandler extends JFrame {
		
		private JTextArea textArea;
		public JTextArea getTextArea()
		{
			return textArea;
		}
		
	    private JTextField textField;    
	    public JTextField getTextField()
	    {
	    	return textField;
	    }
	    
	    private JDialog connectionDialog;
	    public JDialog getConnectionDialog()
	    {
	    	return connectionDialog;
	    }
	    
	    private JLabel connectionStatus;
	    public JLabel getConnectionStatus()
	    {
	    	return connectionStatus;
	    }
	    
	    private BufferedWriter writer;
	    public void setWriter(BufferedWriter value)
	    {
	    	writer = value;
	    }
	
	    private JMenuItem itemConnection;
	    public JMenuItem getItemConnection()
	    {
	    	return itemConnection;
	    }
	    
	    private JPanel statusBar;
	    public JPanel getStatusBar()
	    {
	    	return statusBar;
	    }
	    
	    public InterfaceHandler() {
	    	initializeUI();
	    }
	
	    private void initializeUI() {
	    	setTitle("Online Chat");
	        setSize(800, 500);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	        textArea = new JTextArea();
	        textArea.setLineWrap(true);
	        textArea.setWrapStyleWord(true);
	        textArea.setEditable(false);
	        textArea.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));
	        JScrollPane scrollPane = new JScrollPane(textArea);
	        
	        JPanel panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	        panel.add(scrollPane);
	        
	        JPanel inputPanel = new JPanel(new FlowLayout());
	        textField = new JTextField();
	        textField.setEditable(false);
	        JButton buttonSend = new JButton("Enviar");
	        buttonSend.addActionListener(e -> sendMessage());
	        textField.addKeyListener((KeyListener) new KeyListener(){
	            @Override
	            public void keyPressed(KeyEvent e){
	                if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                sendMessage();
	                }
	            }

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
	        });
	        
	        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
	        textField.setPreferredSize(new Dimension(500, 30));
	        inputPanel.add(textField);
	        inputPanel.add(buttonSend);
	        panel.add(inputPanel);
	
	        statusBar = new JPanel(new FlowLayout());
	        connectionStatus = new JLabel();
	        connectionStatus.setText("Status de Conexão: Desconectado");
	        statusBar.setBackground(new Color (254, 54, 60));
	        statusBar.add(connectionStatus);
	        statusBar.setBorder(BorderFactory.createEtchedBorder());
	        statusBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
	        panel.add(statusBar);
	        
	        add(panel);
     
	        //Menu
	        JMenuBar menuBar = new JMenuBar();
	        menuBar.setBorder(BorderFactory.createEtchedBorder());
	        menuBar.setBackground(new Color (173, 216, 230));
	        
	        JMenu menuArchive = new JMenu("Arquivo");
	        itemConnection = new JMenuItem("Conectar");
	        JMenuItem itemExit = new JMenuItem("Sair");
	
	        menuArchive.add(itemConnection);
	        menuArchive.setBorder(BorderFactory.createEtchedBorder());
	        
	        menuArchive.addSeparator();
	        menuArchive.add(itemExit);
	        
	        itemConnection.addActionListener(e -> showConnectionDialog());
	        itemExit.addActionListener(e -> {
	            System.exit(0);
	        });
	
	        JMenu menuHelp = new JMenu("Ajuda");
	        JMenuItem itemHelp = new JMenuItem("Ajuda");
	        JMenuItem itemAbout = new JMenuItem("Sobre");
	
	        menuHelp.add(itemHelp);
	        menuHelp.addSeparator();
	        menuHelp.add(itemAbout);
	 
	        itemHelp.addActionListener(e -> showHelpDialog());
	        itemAbout.addActionListener(e -> showAboutDialog());
	        
	        menuBar.add(menuArchive);
	        menuBar.add(menuHelp);
	        setJMenuBar(menuBar);
	        
	        this.setVisible(true);
	    }
	    
	    private void showConnectionDialog() {
	        connectionDialog = new JDialog(this, "Conectar", true);
	        connectionDialog.setLayout(new BorderLayout());
	
	        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
	
	        JTextField ipAddressField = new JTextField();
	        JTextField portField = new JTextField();
	        portField.setText("54321");
	        
	        inputPanel.add(new JLabel("Endereço de IP:"));
	        inputPanel.add(ipAddressField);
	        inputPanel.add(new JLabel("Porta:"));
	        inputPanel.add(portField);
	
	        JButton connectButton = new JButton("Conectar");
	        connectButton.addActionListener(e -> 
	        {
	        	if(ipAddressField.getText().equals("") || portField.getText().equals(""))
	        		JOptionPane.showMessageDialog(this, "Preencha todos os dados para conectar.", "Erro", JOptionPane.ERROR_MESSAGE);
	        	else
	        		ClientHandler.connect(this, ipAddressField.getText(), portField.getText());
	        });
	
	        connectionDialog.add(inputPanel, BorderLayout.CENTER);
	        connectionDialog.add(connectButton, BorderLayout.SOUTH);
	
	        connectionDialog.setSize(300, 150);
	        connectionDialog.setResizable(false);
	        connectionDialog.setLocationRelativeTo(this);
	        connectionDialog.setVisible(true);
	    }
	    
	    private synchronized void showHelpDialog() {
	        JDialog helpDialog = new JDialog(this, "Ajuda", true);
	        helpDialog.setLayout(new BorderLayout());
	
	        JTextArea helpText = new JTextArea();
	        helpText.setEditable(false);
	        helpText.setLineWrap(true);
	        helpText.setWrapStyleWord(true);
	        helpText.setText(
	            "Bem-vindo ao Chat em Java\n\n" +
	            "Este programa de chat em Java oferece uma plataforma interativa para a comunicação por texto entre dois usuários. Desenvolvido utilizando a biblioteca Swing, o aplicativo proporciona uma experiência de conversação simples e eficaz.\n\n" +
	            "Instruções de Uso:\n\n" +
	            "Conectar:\n" +
	            "1. Selecione a opção \"Conectar\" no menu \"Arquivo\".\n" +
	            "2. Insira o endereço IP e a porta do destinatário.\n" +
	            "3. Clique em \"Conectar\" para iniciar a conversação.\n\n" +
	            "Enviar Mensagens:\n" +
	            "1. Digite sua mensagem na caixa de texto na parte inferior da janela.\n" +
	            "2. Pressione \"Enviar\" ou a tecla \"Enter\" para enviar a mensagem.\n\n" +
	            "Sair:\n" +
	            "Para encerrar o programa, selecione a opção \"Sair\" no menu \"Arquivo\"."
	        );
	
	        JScrollPane scrollPane = new JScrollPane(helpText);
	        helpDialog.add(scrollPane, BorderLayout.CENTER);
	
	        JButton closeButton = new JButton("Fechar");
	        closeButton.addActionListener(e -> helpDialog.dispose());
	
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(closeButton);
	        
	        helpDialog.add(buttonPanel, BorderLayout.SOUTH);
	        helpDialog.setSize(400, 300);
	        helpDialog.setLocationRelativeTo(this);
	        helpDialog.setResizable(false);
	        helpDialog.setVisible(true);
	    }
	
	    private synchronized void showAboutDialog() {
	        JDialog aboutDialog = new JDialog(this, "Sobre", true);
	        aboutDialog.setLayout(new BoxLayout(aboutDialog.getContentPane(), BoxLayout.Y_AXIS));
	
	        JPanel introductionAbout = new JPanel();
	        introductionAbout.add(new JLabel("Versão: Versão 1.0"));
	        aboutDialog.add(introductionAbout);
	        
	        JPanel introductionGroup = new JPanel();
	        introductionGroup.add(new JLabel("Desenvolvido por:"));
	        aboutDialog.add(introductionGroup);
	        
	        JPanel students = new JPanel(new GridLayout(0, 2));
	        students.add(new JLabel("Mitsuo Luan De Andrade Miyazato - 260852"));
	        JLabel mitsuo = imageLabel("principal/mitsuo.jpg");
	        students.add(mitsuo);
	        
	        JScrollPane scrollPane = new JScrollPane(students);
	        aboutDialog.add(scrollPane);
	
	        aboutDialog.setSize(500, 400);
	        aboutDialog.setLocationRelativeTo(this);
	        aboutDialog.setResizable(false);
	        aboutDialog.setVisible(true);
	    }
	    
	    private JLabel imageLabel(String Path)
	    {
	    	ImageIcon studentPath = new ImageIcon(getClass().getClassLoader().getResource(Path));
	    	ImageIcon studentImg = resizeImageIcon(studentPath);
	    	JLabel studentLabel = new JLabel(studentImg);
	    	return studentLabel;
	    }
	    
	    private ImageIcon resizeImageIcon(ImageIcon icon) {
	        Image img = icon.getImage();
	        Image resizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	        return new ImageIcon(resizedImg);
	    }

	    private void sendMessage() {
	        String message = textField.getText();
	        
	        if (!message.isEmpty() && writer != null) {
	            appendToTextArea("Você: " + message);
	            try {
	                writer.write(message + "\n");
	                writer.flush();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            textField.setText("");
	        }
	    }
	    
	    public void appendToTextArea(String message) {
	        textArea.append(message + "\n");
	        textArea.setCaretPosition(Objects.requireNonNull(textArea.getDocument()).getLength());
	    }
	
	    public void alterOnConnected()
	    {
	    	getConnectionStatus().setText("Status de Conexão: Conectado");
            getTextArea().setText("");
            getTextField().setEditable(true);
            getItemConnection().setEnabled(false);
            getStatusBar().setBackground(new Color (0, 255, 0));
	    }
    
	    public void alterOnDisconnected()
	    {
	    	getConnectionStatus().setText("Status de Conexão: Desconectado");
            getTextField().setEditable(false);
            getItemConnection().setEnabled(true);
            getStatusBar().setBackground(new Color (254, 54, 60));
	    }	    
	}
