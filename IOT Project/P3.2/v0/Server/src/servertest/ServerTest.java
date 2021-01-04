package servertest;

/**
 *
 * @author ferens
 */
public class ServerTest {

    public static void main(String[] args) {

        final gui.GUI myUI = new gui.GUI();
        server.Server myServer = new server.Server(5555, 1, myUI);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myServer);
        myUI.setCommand(myUserCommandHandler);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                myUI.setVisible(true);
            }
        });
    }
    
//    public static void main(String[] args) {
//
//        standardiouserinterface.StandardIO myUI = new standardiouserinterface.StandardIO();
//        server.Server myServer = new server.Server(5555, 1, myUI);
//        usercommandhandler.UserCommandHandler myUserCommandHandler = new usercommandhandler.UserCommandHandler(myUI, myServer);
//        myUI.setCommand(myUserCommandHandler);
//        Thread myUIthread = new Thread(myUI);
//        myUIthread.start();     
//        myUI.update("1:\tQuit\n2:\tlisten\n3:\tSet Port\n4:\tGet Port\n5:\tStop listening\n6:\tStart Server Socket\n");
//    }
    
}
