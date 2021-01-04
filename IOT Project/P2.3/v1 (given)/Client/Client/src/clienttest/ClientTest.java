package clienttest;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientTest {

    public static void main(String[] args) {

        standardiouserinterface.StandardIO myUI = new standardiouserinterface.StandardIO();
        client.Client myClient= new client.Client(5555, 1, myUI);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myClient);
        myUI.setCommandHandler(myUserCommandHandler);
        Thread myUIthread = new Thread(myUI);
        myUIthread.start();
        myUI.update("1:\tQuit\n2:\tConnect to server\n3:\tDisconnect from server\n4:\tGet Time\n");
    }
}
