package proxytest;

import client.Client;
import server.Server;

public class proxyTest {
    public static void main(String[] args) {

        // Opening the communication between the Proxy Network and the board.
        client.Client clientToBoard = new Client(5555,1);

        //Lister for connection from AndroidGUI
        server.Server serverToAndroid = new Server(7777,1,clientToBoard);
        clientToBoard.setMsgHandler(serverToAndroid);

        //We now need to simulate the action of clicking on
        //2:StartServer
        //6:Listen for client connections
        clientToBoard.openConnectionToServer("192.168.1.250");
        serverToAndroid.startServer();
        serverToAndroid.listen();

    }
}
