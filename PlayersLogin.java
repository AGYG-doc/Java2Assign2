package bing;

public class PlayersLogin extends Frame implements ActionListener {
  JButton clearMsgButton = new JButton("清空");
  JButton serverStatusButton = new JButton("状态");
    JButton closeServerButton = new JButton("关闭");
    Panel buttonPanel = new Panel();
    ServerMsgPanel serverMsgPanel = new ServerMsgPanel();
    ServerSocket serverSocket;
    int clientAccessNumber = 1;


    Hashtable clientDataHash = new Hashtable(50);

    Hashtable clientNameHash = new Hashtable(50);

    Hashtable chessPeerHash = new Hashtable(50);

    public PlayersLogin() {
        super("123");
        setBackground(Color.PINK);
        buttonPanel.setLayout(new FlowLayout());
        clearMsgButton.setSize(50, 30);
        buttonPanel.add(clearMsgButton);
        clearMsgButton.addActionListener(this);
        serverStatusButton.setSize(50, 30);
        buttonPanel.add(serverStatusButton);
        serverStatusButton.addActionListener(this);
        closeServerButton.setSize(50, 30);
        buttonPanel.add(closeServerButton);
        closeServerButton.addActionListener(this);
        add(serverMsgPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        pack();
        setVisible(true);
        setSize(600, 440);
        setResizable(false);
        validate();

        try {
            createServer(1234, serverMsgPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createServer(int port, ServerMsgPanel serverMsgPanel) throws IOException {

        Socket clientSocket;

        this.serverMsgPanel = serverMsgPanel;
        try {
            serverSocket = new ServerSocket(port);
            serverMsgPanel.msgTextArea.setText("服务器启动:"
                    + InetAddress.getLocalHost() + ":"
                    + serverSocket.getLocalPort() + "\n");
            while (true) {

                clientSocket = serverSocket.accept();
                serverMsgPanel.msgTextArea.append("已连接用户:" +
                        "34" + clientAccessNumber +"\n" + clientSocket + "\n");

                DataOutputStream outputData = new DataOutputStream(clientSocket.getOutputStream());

                clientDataHash.put(clientSocket, outputData);

                clientNameHash.put(clientSocket, ("123" + clientAccessNumber++));

                ServerThread thread = new ServerThread(clientSocket,
                        clientDataHash, clientNameHash, chessPeerHash, serverMsgPanel);
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clearMsgButton) {
            serverMsgPanel.msgTextArea.setText("");
        }


        if (e.getSource() == serverStatusButton) {
            try {
                serverMsgPanel.msgTextArea.append("用户信息：" + "123"
                        + (clientAccessNumber - 1) + "\n服务器信息:"
                        + InetAddress.getLocalHost() + ":"
                        + serverSocket.getLocalPort() + "\n");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PlayersLogin();
    }

}



