package servertest;

public class ServerTest {

    public static void main(String[] args) {

        standardiouserinterface.StandardIO myUI = 
                new standardiouserinterface.StandardIO();
        myserver.MyServer myServer = new myserver.MyServer(7777, 1, myUI);
        clientmessagehandler.ClientMessageHandler myClientMessageHandler = new clientmessagehandler.ClientMessageHandler(myServer);
        myServer.setClientMessageHandler(myClientMessageHandler);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myServer);
        myUI.setUserCommandHandler(myUserCommandHandler);
        Thread myUIthread = new Thread(myUI);
        myUIthread.start();     
        myUI.update("1:\tQuit\n2:\tlisten\n3:\tSet Port\n4:\tGet " + "Port\n5:\tStop listening\n6:\tStart Server Socket\n");
    }
}

