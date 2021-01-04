package servertest;

/**
 *
 * @author ferens
 */
public class ServerTest {

    public static void main(String[] args) {

        userinterface.StandardIO myUI = new userinterface.StandardIO();
        server.Server myServer = new server.Server(5555, 100, myUI);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myServer);
        myUI.setCommandHandler(myUserCommandHandler);
        Thread myUIthread = new Thread(myUI);
        myUIthread.start();     
        myUI.display("1:\tQuit\n2:\tlisten\n3:\tSet Port\n4:\tGet Port\n5:\tStop listening\n6:\tStart Server Socket\n");
    }
}
