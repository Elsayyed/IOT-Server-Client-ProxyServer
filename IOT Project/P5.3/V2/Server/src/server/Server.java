package server;

import proxymessagehandler.ProxyMessageHandler;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elsayyed
 */
public class Server implements Runnable {

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    ProxyMessageHandler myClientCommandHandler;
    int portNumber = 5555, backlog = 500;
    boolean doListen = false;

    public Server(int portNumber, int backlog,client.Client clientToBoard) {
        this.portNumber = portNumber;
        this.backlog = backlog;
        this.myClientCommandHandler = new ProxyMessageHandler(this,clientToBoard);
    }

    public synchronized void setDoListen(boolean doListen) {
        this.doListen = doListen;
    }
    public ProxyMessageHandler getMyClientCommandHandler() {
        return myClientCommandHandler;
    }

    public void startServer() {
        if (serverSocket != null) {
            stopServer();
        } else {
            try {
                serverSocket = new ServerSocket(portNumber, backlog);
            } catch (IOException e) {
                System.exit(0);
            } finally {
            }
        }

    }

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.exit(0);
            } finally {
            }
        }
    }

    public void listen() {
        try {
            setDoListen(true);
            serverSocket.setSoTimeout(500);
            Thread myListenerThread = new Thread(this);
            myListenerThread.start();
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (doListen == true) {
                try {
                    clientSocket = serverSocket.accept();
                    clientconnection.ClientConnection myCC = new clientconnection.ClientConnection(clientSocket, myClientCommandHandler, this);
                    Thread myCCthread = new Thread(myCC);
                    myCCthread.start();
                } catch (IOException e) {
                    //check doListen.
                } finally {
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }
}
