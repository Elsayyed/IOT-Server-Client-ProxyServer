package clienttest;

import GUI.Gui;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientTest {

    public static void main(String[] args) {

        final GUI.Gui myUI = new GUI.Gui();
        client.Client myClient = new client.Client(5555, 1, myUI);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myClient);
        myUI.setCommandHandler(myUserCommandHandler);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }
}
    
//    public static void main(String[] args) {
//
//        standardiouserinterface.StandardIO myUI = new standardiouserinterface.StandardIO();
//        client.Client myClient= new client.Client(5555, 1, myUI);
//        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myClient);
//        myUI.setCommandHandler(myUserCommandHandler);
//        Thread myUIthread = new Thread(myUI);
//        myUIthread.start();
//        myUI.update("1:\tQuit\n2:\tConnect to server\n3:\tDisconnect from server\n4:\tGet Time\n");
//    }
//}