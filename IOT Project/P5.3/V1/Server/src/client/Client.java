package client;

import java.io.*;
import java.net.*;

import clientmessagehandler.ClientMessageHandler;

public class Client implements Runnable {

    InputStream input;
    OutputStream output;
    Socket clientSocket = null;
    ClientMessageHandler msgHandler;
    byte clientCommand = ' ';
    int portNumber = 5555, backlog = 1;
    String theCommand = "";
    boolean threadStop;
    
    public Client(int portNumber, int backlog) {
        this.portNumber = portNumber;
        this.backlog = backlog;
    }

    public void openConnectionToServer(String serverIP) {
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
        } finally {
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

    public boolean isConnected(){
        return clientSocket!=null;
    }
    
    public String getClientSocket(){
        return  clientSocket.getLocalSocketAddress().toString();
    }

    public void getMessageFromServer() {
        if(this.isConnected()){
        Thread messageHandlingThread = new Thread(this);  
        messageHandlingThread.start();
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
    
    public void sendMessageToClient(int msg) {
        String msgToSend = Integer.toString(msg);
        this.sendMessageToServer(msgToSend);
    }

    public void setMsgHandler(server.Server theServer){
        msgHandler = theServer.getMyClientCommandHandler();
    }

    public void setPort(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPort() {
        return this.portNumber;
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
//////////
//      \\Since you're here mafrod hena takhod ay message gaya mn el board
// KEY  \\lel proxy server and based on that reply you print it on the android gui
//      \\technically this's the right way to do it however mesh da ely by7sal
//////////
            } catch (IOException e) {
                String exceptionMessage = "Cannot read from socket; stopping thread and disconnecting client."+ "error message is: ";
                //msgHandler.handleServerMessage(exceptionMessage,e);
                disconnectfromServer();
                threadStop = true;
            }
        }       
    }
}