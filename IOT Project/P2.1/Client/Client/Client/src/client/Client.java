package client;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client {

    InputStream input;
    OutputStream output;
    Socket clientSocket = null;
    byte msg = ' ', clientCommand = ' ';
    int portNumber = 5555, backlog = 1;

    public Client(int portNumber, int backlog) {
        this.portNumber = portNumber;
        this.backlog = backlog;
    }

    public void openConnectionToServer(String serverIP) {
        try {
             clientSocket = new Socket(serverIP, portNumber);
             input = clientSocket.getInputStream();
             output = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e.toString());
            System.err.println("Cannot connect to server. Exiting program.");
            System.exit(0);
        } finally {
        }
    }

    public void disconnectfromServer() {
        try {
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

    public byte getMessageFromServer() {
        try {
            msg = (byte) input.read();
        } catch (IOException e) {
            System.err.println("cannot read from socket; exiting program.");
            System.exit(0);
        } finally {
            return msg;
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

    public void setPort(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPort() {
        return this.portNumber;
    }
}