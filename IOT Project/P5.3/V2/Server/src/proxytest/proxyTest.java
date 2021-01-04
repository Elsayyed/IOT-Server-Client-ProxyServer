package proxytest;

import client.Client;
import server.Server;
/**
 *
 * @author Elsayyed
 */
public class proxyTest {
    public static void main(String[] args) {

        // Opening the communication between the Proxy Network and the board.
        client.Client clientToBoard = new Client(5555,1,"192.168.1.250");

        //Lister for connection from AndroidGUI
        server.Server serverToAndroid = new Server(7777,1,clientToBoard);
        clientToBoard.setMsgHandler(serverToAndroid);

        //Open Connection to the Server
        clientToBoard.openConnectionToServer();
        //2: Start Proxy server
        serverToAndroid.startServer();
        //6: Listening for Android Connection
        serverToAndroid.listen();

    }
}
