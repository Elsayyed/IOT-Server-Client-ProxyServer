package clienttest;

import GUI.Gui;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientTest {

    public static void main(String[] args) {

        final GUI.Gui myUI = new GUI.Gui();
        client.Client myClient = new client.Client(7777, 1, myUI);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myClient);
        myUI.setCommandHandler(myUserCommandHandler);

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                myUI.setVisible(true);
            }
        });
    }
}
  
//    public static void main(String[] args) {
//
//        standardiouserinterface.StandardIO myUI = new standardiouserinterface.StandardIO();
//        client.Client myClient= new client.Client(7777, 1, myUI);
//        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myClient);
//        myUI.setCommandHandler(myUserCommandHandler);
//        Thread myUIthread = new Thread(myUI);
//        myUIthread.start();
//        myUI.update("1:\tQuit\n2:\tConnect to server\n3:\tDisconnect from server\n4:\tGet Temperature\n<5,6,7,8>\tLED<1,2,3,4> <On/Off>\n<9,10,11>\tPB_Status<1,2,3> <Up/Down>\n");
//    }
//}