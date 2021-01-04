package clienttest;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientTest {

    public static void main(String[] args) {

        userinterface.StandardIO myUI = new userinterface.StandardIO();
        client.Client myClient= new client.Client(5555, 1);
        byte msg = ' ';

        /*The server accepts the following client commands:
         d:	Disconnect From Server
         t:	Request the time
         */

        //Server user menu
        myUI.display("1:\tQuit\n2:\tConnect to server\n3:\tDisconnect from server\n4:\tGet Time\n");
        while (true) {
            String myCommand = myUI.getUserInput(); 
            switch (Integer.parseInt(myCommand)) {
                case 1: //QUIT
                   if(myClient.isConnected()){
                        myUI.display("Cant Quit without Disconnecting first.");
                    }else{
                        myUI.display("Exiting Program.");
                        System.exit(0);
                    }
                   
                    break;
                case 2: //Connect
                    myClient.openConnectionToServer("localhost");
                    myUI.display("Client Succesfully Connected to server");
                    break;    
                case 3: //Disconect
                    myClient.sendMessageToServer((byte)'d');
                    myClient.disconnectfromServer();
                    myUI.display("Client Succesfully Disconnected from server");
                    break;
                case 4: //Get Time
                    myClient.sendMessageToServer((byte)'t');
                    myUI.display("Time recieved as:");
                    boolean bool = true;
                    String time = "";
                    byte b;
                    while(bool){
                        b = myClient.getMessageFromServer();
                        time += (char)b;
                        if(time.length()==8){
                            bool = false;
                        }
                    }
                    myUI.display(time);
                    break;
                default:
                    break;
            }
        }
    }
}
