package ECE3740.androidgui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements userinterface.UserInterface {

    String myServerIPAddress = "";
    String myPortNumber = "";
    TextView serverMessageWindow, IPAddress, PB1Label,PB2Label,PB3Label;
    Switch switchStateLED1,switchStateLED2,switchStateLED3,switchStateLED4;

    ECE3740.usercommandhandler.UserCommandHandler myUserCommandHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ECE3740.client.Client myClient = new ECE3740.client.Client(7777,"10.0.2.2",this);
        myUserCommandHandler = new ECE3740.usercommandhandler.UserCommandHandler(this,myClient);
        serverMessageWindow = findViewById(R.id.id_Text_ServerMessages);
        IPAddress = findViewById(R.id.id_Text_portNumber);
        switchStateLED1 = findViewById(R.id.id_switch_LED1);
        switchStateLED2 = findViewById(R.id.id_switch_LED1);
        switchStateLED3 = findViewById(R.id.id_switch_LED1);
        switchStateLED4 = findViewById(R.id.id_switch_LED1);
        PB1Label = findViewById(R.id.id_text_PB1Label);
        PB2Label = findViewById(R.id.id_text_PB2Label);
        PB3Label = findViewById(R.id.id_text_PB3Label);
    }

    public void update(String message){
        Message msg = Message.obtain();
        msg.obj = message;
        handler.sendMessage(msg);

//        String [] pb = msg.obj.toString().split(" ");
//        if(message.equals("Pushbutton")){
//            if(pb[1].equals("1")){
//                if(pb[3].equals("UP")){
//                    setPB1Text("UP");
//                }else{
//                    setPB1Text("DOWN");
//                }
//            }
//            else if(pb[1].equals("2")){
//                if(pb[3].equals("UP")){
//                    textpb2.setText("UP");
//                }else{
//                    textpb2.setText("DOWN");
//                }
//            }
//            else{
//                if(pb[3].equals("UP")){
//                    textpb3.setText("UP");
//                }else{
//                    textpb3.setText("DOWN");
//                }
//            }
//        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            serverMessageWindow.append(msg.obj.toString());
        }
    };

    Handler PB1handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            PB1Label.setText(msg.obj.toString());
        }
    };

    Handler PB2handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            PB2Label.setText(msg.obj.toString());
        }
    };

    Handler PB3handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            PB3Label.setText(msg.obj.toString());
        }
    };

    public void setPB1Text(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        PB1handler.sendMessage(msg);
    }
    public void setPB2Text(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        PB2handler.sendMessage(msg);
    }
    public void setPB3Text(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        PB3handler.sendMessage(msg);
    }

    public void buttonHandler_setServerIPAddress(View view) {
        TextView IPAddress = findViewById(R.id.id_Text_IPAddress);
        myServerIPAddress = IPAddress.getText().toString();
        update("Server IP Address set to: "+myServerIPAddress+"\n");
    }

    public void buttonHandler_setPortNumber(View view) {
        myPortNumber = IPAddress.getText().toString();
        update("Port Number set to: " + myPortNumber+"\n");
    }

    public void buttonHandler_Disconnect(View view) {
        String myDisconnectCode = ""; myDisconnectCode = "3"; //
        update("Attempting to Disconnect.. \n");
        // STILL MISSING SOMETHING!!
        myUserCommandHandler.handleUserCommand(myDisconnectCode);
    }

    public void buttonHandler_Connect(View view) {
        String myConnectCode = ""; myConnectCode = "2";
        update("Attempting to Connect to "+myServerIPAddress+" | Port: "+myPortNumber+"\n");
        // STILL MISSING SOMETHING!!
        myUserCommandHandler.handleUserCommand(myConnectCode);
    }

    public void buttonHandler_GetTime(View view) {
        String myGetTime = ""; myGetTime = "4";
        myUserCommandHandler.handleUserCommand(myGetTime);
    }

    public void buttonHandler_GetTemperature(View view) {
        String myGetTemperature = ""; myGetTemperature = "12";
        // STILL MISSING SOMETHING!!
        myUserCommandHandler.handleUserCommand(myGetTemperature);
    }

    public void switchHandler_LED1(View view) {
        String myLED1_Code = "5";

        if(switchStateLED1.isChecked()){
            update("Sending Request to Switch LED1 ON..\n");
        }
        else{
            update("Sending Request to Switch LED1 OFF..\n");
        }
        myUserCommandHandler.handleUserCommand(myLED1_Code);

    }

    public void switchHandler_LED2(View view) {
        String myLED2_Code = "6";

        if(switchStateLED2.isChecked()){
            update("Sending Request to Switch LED2 ON..\n");
        }
        else{
            update("Sending Request to Switch LED2 OFF..\n");
        }

        myUserCommandHandler.handleUserCommand(myLED2_Code);
    }

    public void switchHandler_LED3(View view) {
        String myLED3_Code = "7";

        if(switchStateLED3.isChecked()){
            update("Sending Request to Switch LED3 ON..\n");
        }
        else{
            update("Sending Request to Switch LED3 OFF..\n");
        }

        myUserCommandHandler.handleUserCommand(myLED3_Code);
    }

    public void switchHandler_LED4(View view) {
        String myLED4_Code = "8";

        if(switchStateLED4.isChecked()){
            update("Sending Request to Switch LED4 ON..\n");
        }
        else{
            update("Sending Request to Switch LED4 OFF..\n");
        }
        myUserCommandHandler.handleUserCommand(myLED4_Code);
    }

    public void buttonHandler_PB1(View view) {
        String myPB1Code = ""; myPB1Code = "9";
        update("Requesting state of PB1\n");
        myUserCommandHandler.handleUserCommand(myPB1Code);
    }

    public void buttonHandler_PB2(View view) {
        String myPB2Code = ""; myPB2Code = "10";
        update("Requesting state of PB2\n");
        myUserCommandHandler.handleUserCommand(myPB2Code);
    }

    public void buttonHandler_PB3(View view) {
        String myPB3Code = ""; myPB3Code = "11";
        update("Requesting state of PB3 \n");
        myUserCommandHandler.handleUserCommand(myPB3Code);
    }

}