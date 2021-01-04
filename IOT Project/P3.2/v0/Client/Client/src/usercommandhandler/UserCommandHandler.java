/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usercommandhandler;

/**
 *
 * @author ferens
 */

public class UserCommandHandler implements Runnable {

    userinterface.UserInterface myUI;
    client.Client myClient;
    String myCommand = "";
    boolean toggle1 = true;
    boolean toggle2 = true;
    boolean toggle3 = true;
    boolean toggle4 = true;
    boolean toggle5 = true;
    boolean toggle6 = true;
    boolean toggle7 = true;

    public UserCommandHandler(userinterface.UserInterface myUI, client.Client myClient) {
        this.myUI = myUI;
        this.myClient = myClient;
    }

    public void handleUserCommand(String myCommand) {
        this.myCommand = myCommand;
        Thread casesThread = new Thread(this);
        casesThread.start();
    }

    @Override
    public void run() {
        switch (Integer.parseInt(myCommand)) {
            case 1: //QUIT
                if (myClient.isConnected()){
                    myClient.sendMessageToServer((byte) 'q');
                    myClient.sendMessageToServer((byte) 0xFF);    
                    myUI.update("Exiting Program.");
                }
                else{
                    System.exit(0);
                }
                break;
            case 2: //Connect
                if (myClient.isConnected() == false){
                myClient.openConnectionToServer("localhost");
                myUI.update("Client Succesfully Connected to server");
                }
                else{
                    myUI.update("Already connected to server.");
                }
                break;
            case 3: //Disconnect
                myClient.sendMessageToServer((byte) 'd');
                myClient.sendMessageToServer((byte) 0xFF);
//                myClient.getMessageFromServer();
//                myClient.disconnectfromServer();
                myUI.update("Client Succesfully Disconnected from server");
                break;
            case 4: //Get Time
                myClient.sendMessageToServer((byte) 't');
                myClient.sendMessageToServer((byte) 0xFF);
                myUI.update("Time recieved as:");
//                myClient.getMessageFromServer();
                break;
            case 5: //Turn LED1 on/off
                if (toggle1) {
                    toggle1 = false;
                    myClient.sendMessageToServer("L1on");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L1on request");
                    myClient.getMessageFromServer();
                } else {
                    toggle1 = true;
                    myClient.sendMessageToServer("L1off");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L1off request");
                    myClient.getMessageFromServer();
                }
                break;
            case 6: //Turn LED2 on/off
                if (toggle2) {
                    toggle2 = false;
                    myClient.sendMessageToServer("L2on");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L2on request");
                    myClient.getMessageFromServer();
                } else {
                    toggle2 = true;
                    myClient.sendMessageToServer("L2off");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L2off request");
                    myClient.getMessageFromServer();
                }
                break;
            case 7: //Turn LED3 on/off
                if (toggle3) {
                    toggle3 = false;
                    myClient.sendMessageToServer("L3on");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L3on request");
                    myClient.getMessageFromServer();
                } else {
                    toggle3 = true;
                    myClient.sendMessageToServer("L3off");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L3off request");
                    myClient.getMessageFromServer();
                }
                break;
            case 8: //Turn LED3 on/off
                if (toggle4) {
                    toggle4 = false;
                    myClient.sendMessageToServer("L4on");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L4on request");
                    myClient.getMessageFromServer();
                } else {
                    toggle4 = true;
                    myClient.sendMessageToServer("L4off");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch L4off request");
                    myClient.getMessageFromServer();
                }
                break;
            case 9: //Turn LED3 on/off
                if (toggle5) {
                    toggle5 = false;
                    myClient.sendMessageToServer("gpb1");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch gpb1 request");
                    myClient.getMessageFromServer();
                } else {
                    toggle4 = true;
                    myClient.sendMessageToServer("gpb1");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch gpb1 request");
                    myClient.getMessageFromServer();
                }
                break;
            case 10: //Turn LED3 on/off
                if (toggle6) {
                    toggle6 = false;
                    myClient.sendMessageToServer("gpb2");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch gpb2 request");
                    myClient.getMessageFromServer();
                } else {
                    toggle6 = true;
                    myClient.sendMessageToServer("gpb2");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch gpb2 request");
                    myClient.getMessageFromServer();
                }
                break;
            case 11: //Turn LED3 on/off
                if (toggle7) {
                    toggle7 = false;
                    myClient.sendMessageToServer("gpb3");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch gpb3 request");
                    myClient.getMessageFromServer();
                } else {
                    toggle7 = true;
                    myClient.sendMessageToServer("gpb3");
                    myClient.sendMessageToServer((byte) 0xFF);
                    myUI.update("Server has acknowledged the switch gpb3 request");
                    myClient.getMessageFromServer();
                }
                break;
            default:
                break;
        }
    }
}
