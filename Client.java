package bing;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.StringTokenizer;



public class Client extends Frame implements ActionListener, KeyListener {


    public Socket clientSocket;

    public DataInputStream inputStream;

    public DataOutputStream outputStream;


    public String chessClientName = null;

    public String host = null;

    public int port = 1234;

    public boolean isOnChat = false;

  public boolean isOnChess = false;

  public boolean isGameConnected = false;

  public boolean isCreator = false;

  public boolean isParticipant = false;

    public UserList userListPad = new UserList();
    /**
     * 用户聊天区
     */
    protected UserChat userChatPad = new UserChat();

    public UserController userControlPad = new UserController();

    protected UserInput userInputPad = new UserInput();

    ChessBoard chessBoard = new ChessBoard();

    private Panel southPanel = new Panel();
    private Panel centerPanel = new Panel();
    private Panel eastPanel = new Panel();


    public ClientChess() {
     super("tic");
        setLayout(new BorderLayout());
        host = userControlPad.ipInputted.getText();

        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(userListPad, BorderLayout.NORTH);
        eastPanel.add(userChatPad, BorderLayout.CENTER);
        eastPanel.setBackground(new Color(238, 154, 73));

        userInputPad.contentInputted.addKeyListener(this);

        chessBoard.host =  (userControlPad.ipInputted.getText());
        centerPanel.add(chessBoard, BorderLayout.CENTER);
        centerPanel.add(userInputPad, BorderLayout.SOUTH);
        centerPanel.setBackground(new Color(238, 154, 73));
        userControlPad.connectButton.addActionListener(this);
        userControlPad.createButton.addActionListener(this);
        userControlPad.joinButton.addActionListener(this);
        userControlPad.cancelButton.addActionListener(this);
        userControlPad.exitButton.addActionListener(this);
        userControlPad.createButton.setEnabled(false);
        userControlPad.joinButton.setEnabled(false);
        userControlPad.cancelButton.setEnabled(false);

        southPanel.add(userControlPad, BorderLayout.CENTER);
        southPanel.setBackground(PINK);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                if (isOnChat) {

                    try {
                        clientSocket.close();
                    }
                    catch (Exception ed){}
                }

                if (isOnChess || isGameConnected) {

                    try {

                        chessBoard.chessSocket.close();
                    }
                    catch (Exception ee){}
                }
                System.exit(0);
            }
        });

        add(eastPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        pack();
        setSize(1000, 700);
        setVisible(true);
        setResizable(false);
        this.validate();
    }


    public boolean connectToServer(String serverIP, int serverPort) throws Exception {
        try {

            clientSocket = new Socket(serverIP, serverPort);

            inputStream = new DataInputStream(clientSocket.getInputStream());

            outputStream = new DataOutputStream(clientSocket.getOutputStream());

            ClientThread clientThread = new ClientThread(this);

            clientThread.start();
            isOnChat = true;
            return true;
        } catch (IOException ex) {
            userChatPad.chatTextArea.setText("Sorry!!!\n");
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == userControlPad.connectButton) {
            // 取得主机地址
            host = chessBoard.host = userControlPad.ipInputted.getText();
            try {

        if (connectToServer(host, port)) {
                    userChatPad.chatTextArea.setText("");
                    userControlPad.connectButton.setEnabled(false);
                    userControlPad.createButton.setEnabled(true);
                    userControlPad.joinButton.setEnabled(true);
                    chessBoard.statusText.setText("wait!!!");
                }
            } catch (Exception ei) {
                userChatPad.chatTextArea.setText("Sorry!!!\n");
            }
        }


        if (e.getSource() == userControlPad.exitButton) {

            if (isOnChat) {
                try {

                    clientSocket.close();
                }
                catch (Exception ed){}
            }


            if (isOnChess || isGameConnected) {
                try {

                    chessBoard.chessSocket.close();
                }
                catch (Exception ee){}
            }
            System.exit(0);
        }


        if (e.getSource() == userControlPad.joinButton) {

            String selectedUser = userListPad.userList.getSelectedItem();

            if (selectedUser == null || selectedUser.startsWith("[inchess]") ||
                    selectedUser.equals(chessClientName)) {
                chessBoard.statusText.setText("1!");
            } else {

                try {

                    if (!isGameConnected) {

                        if (chessBoard.connectServer(chessBoard.host, chessBoard.port)) {
                            isGameConnected = true;
                            isOnChess = true;
                            isParticipant = true;
                            userControlPad.createButton.setEnabled(false);
                            userControlPad.joinButton.setEnabled(false);
                            userControlPad.cancelButton.setEnabled(true);
                            chessBoard.chessThread.sendMessage("/joingame "
                                    + userListPad.userList.getSelectedItem() + " "
                                    + chessClientName);
                        }
                    } else {

                        isOnChess = true;
                        isParticipant = true;
                        userControlPad.createButton.setEnabled(false);
                        userControlPad.joinButton.setEnabled(false);
                        userControlPad.cancelButton.setEnabled(true);
                        chessBoard.chessThread.sendMessage("/joingame "
                                + userListPad.userList.getSelectedItem() + " "
                                + chessClientName);
                    }
                } catch (Exception ee) {
                    isGameConnected = false;
                    isOnChess = false;
                    isParticipant = false;
                    userControlPad.createButton.setEnabled(true);
                    userControlPad.joinButton.setEnabled(true);
                    userControlPad.cancelButton.setEnabled(false);
                    userChatPad.chatTextArea.setText("can: \n" + ee);
                }
            }
        }


        if (e.getSource() == userControlPad.createButton) {
            try {

                if (!isGameConnected) {
                    if (chessBoard.connectServer(chessBoard.host, chessBoard.port)) {

                        isGameConnected = true;
                        isOnChess = true;
                        isCreator = true;
                        userControlPad.createButton.setEnabled(false);
                        userControlPad.joinButton.setEnabled(false);
                        userControlPad.cancelButton.setEnabled(true);
                        chessBoard.chessThread.sendMessage("/creatgame " + "[inchess]" + chessClientName);
                    }
                } else {

                    isOnChess = true;
                    isCreator = true;
                    userControlPad.createButton.setEnabled(false);
                    userControlPad.joinButton.setEnabled(false);
                    userControlPad.cancelButton.setEnabled(true);
                    chessBoard.chessThread.sendMessage("/creatgame "
                            + "[inchess]" + chessClientName);
                }
            } catch (Exception ec) {
                isGameConnected = false;
                isOnChess = false;
                isCreator = false;
                userControlPad.createButton.setEnabled(true);
                userControlPad.joinButton.setEnabled(true);
                userControlPad.cancelButton.setEnabled(false);
                ec.printStackTrace();
                userChatPad.chatTextArea.setText("Sorry: \n" + ec);
            }
        }


        if (e.getSource() == userControlPad.cancelButton) {
            // 游戏中
            if (isOnChess) {
                chessBoard.chessThread.sendMessage("/giveup " + chessClientName);
                chessBoard.setVicStatus(-1 * chessBoard.chessColor);
                userControlPad.createButton.setEnabled(true);
                userControlPad.joinButton.setEnabled(true);
                userControlPad.cancelButton.setEnabled(false);
                chessBoard.statusText.setText("!!!");
            } if (!isOnChess) {
                // 非游戏中
                userControlPad.createButton.setEnabled(true);
                userControlPad.joinButton.setEnabled(true);
                userControlPad.cancelButton.setEnabled(false);
                chessBoard.statusText.setText("!!!");
            }
            isParticipant = isCreator = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        TextField inputwords = (TextField) e.getSource();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (userInputPad.userChoice.getSelectedItem().equals("asd")) {
                try {

                    outputStream.writeUTF(inputwords.getText());
                    inputwords.setText("");
                } catch (Exception ea) {
                    userChatPad.chatTextArea.setText("Sorry!\n");
                    userListPad.userList.removeAll();
                    userInputPad.userChoice.removeAll();
                    inputwords.setText("");
                    userControlPad.connectButton.setEnabled(true);
                }
            } else {
                // 给指定人发信息
                try {
                    outputStream.writeUTF("/" + userInputPad.userChoice.getSelectedItem()
                            + " " + inputwords.getText());
                    inputwords.setText("");
                } catch (Exception ea) {
                    userChatPad.chatTextArea.setText("Sorry!\n");
                    userListPad.userList.removeAll();
                    userInputPad.userChoice.removeAll();
                    inputwords.setText("");
                    userControlPad.connectButton.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new ClientChess();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

