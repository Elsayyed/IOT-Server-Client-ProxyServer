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
    standardiouserinterface.StandardIO myUI;
    client.Client myClient;
    String myCommand="";
    
    public UserCommandHandler(standardiouserinterface.StandardIO myUI, client.Client myClient) {
        this.myUI = myUI;
        this.myClient = myClient;
    }

    public void execute(String myCommand) {
        this.myCommand = myCommand;
        Thread casesThread = new Thread(this);
        casesThread.start();
    }
    
    @Override
    public void run() {
         switch (Integer.parseInt(myCommand)) {
                case 1: //QUIT
                        myUI.update("Exiting Program.");
                        System.exit(0);
                    break;
                case 2: //Connect
                    myClient.openConnectionToServer("localhost");
                    myUI.update("Client Succesfully Connected to server");
                    break;    
                case 3: //Disconect
                    myClient.sendMessageToServer((byte)'d');
                    myClient.sendMessageToServer((byte)0xFF);
                    myClient.disconnectfromServer();
                    myUI.update("Client Succesfully Disconnected from server");
                    break;
                case 4: //Get Time
                    myClient.sendMessageToServer((byte)'t');
                    myClient.sendMessageToServer((byte)0xFF);
                    myUI.update("Time recieved as:");
                    myClient.getMessageFromServer();
                   break;
                default:
                    break;
            }
    }
}