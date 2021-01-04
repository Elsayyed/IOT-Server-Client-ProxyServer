package client;

import java.io.*;
import java.net.*;

import proxymessagehandler.ProxyMessageHandler;

public class Client implements Runnable {

    InputStream input;
    OutputStream output;
    Socket clientSocket = null;
    ProxyMessageHandler msgHandler;
    int portNumber = 5555, backlog = 1;
    boolean threadStop;
    String serverIP = "";

    public Client(int portNumber, int backlog, String serverIP) {
        this.portNumber = portNumber;
        this.backlog = backlog;
        this.serverIP = serverIP;
    }

    public void openConnectionToServer() {
        try {
             clientSocket = new Socket(InetAddress.getByName(serverIP), portNumber);
             input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
            Thread messageHandlingThread = new Thread(this);
            threadStop = false;
            messageHandlingThread.start();
        } catch (IOException e) {
            System.err.println(e.toString());
            System.err.println("Cannot connect to server. Exiting program.");
            System.exit(0);
        }
    }

    public void disconnectfromServer() {
        try {
            threadStop = true;
            clientSocket.close();
            input = null;
            output = null;
            clientSocket = null;
        } catch (IOException e) {
            System.err.println("cannot close stream/client socket; exiting.");
            System.exit(0);
        } finally {
        }
    }


    public void sendMessageToServer(byte msg) {
        try {
            output.write(msg);
            output.flush();
        } catch (IOException e) {
            System.err.println("cannot send to socket; exiting program.");
            System.exit(0);
        } finally {
        }
    }
    
    public void sendMessageToServer(String msg) {
        for (int i = 0; i < msg.length(); i++) {
            byte theByteMessage = (byte) msg.charAt(i);
            this.sendMessageToServer(theByteMessage);
        }
    }


    public void sendMessageToServer(int msg) { //check lateeeeeeeeeeeeeeeeeeeeeerrrrrrrrrr
        String msgToSend = Integer.toString(msg);
        this.sendMessageToServer(msgToSend);
    }

    public void setMsgHandler(server.Server serverToBoard){
        msgHandler = serverToBoard.getMyClientCommandHandler();
    }
    
@Override
public void run() {
        byte msg;
        String theString="";
        while (!threadStop) {
            try {
                msg = (byte) input.read();
                theString = Character.toString((char) msg);
                msgHandler.handleClientMessage(msgHandler.getMyAndroidConnection(), theString);

            } catch (IOException e) {
                disconnectfromServer();
                threadStop = true;
            }
        }       
    }
}