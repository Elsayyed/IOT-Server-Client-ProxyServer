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
    standardiointerface.StandardIO myUI;
    client.Client myClient;
    String myCommand ="";

    public UserCommandHandler(standardiointerface.StandardIO myUI, client.Client myClient) {
        this.myUI = myUI;
        this.myClient = myClient;
    }

    public void execute(String myCommand) {
        this.myCommand = myCommand;
        Thread messageHandlerThread = new Thread(this);
        messageHandlerThread.start();
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
                    myClient.disconnectfromServer();
                    myUI.update("Client Succesfully Disconnected from server");
                    break;
                case 4: //Get Time
                    myClient.sendMessageToServer((byte)'t');
                    myUI.update("Time recieved as:");
                    myClient.getMessageFromServer();
//                    boolean bool = true;
//                    String time = "";
//                    byte b;
//                    while(bool){
//                        b = myClient.getMessageFromServer();
//                        time += (char)b;
//                        if(time.length()==8){
//                            bool = false;
//                        }
//                    }
//                    myUI.display(time);
                    
                   break;
                default:
                    break;
            }
    }
    }

