package ECE3740.androidgui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String myServerIPAddress = "";
    String myPortNumber = "";
    TextView serverMessageWindow, IPAddress;
    Switch switchStateLED1,switchStateLED2,switchStateLED3,switchStateLED4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverMessageWindow = findViewById(R.id.id_Text_ServerMessages);
        IPAddress = findViewById(R.id.id_Text_portNumber);
        switchStateLED1 = findViewById(R.id.id_switch_LED1);
        switchStateLED2 = findViewById(R.id.id_switch_LED1);
        switchStateLED3 = findViewById(R.id.id_switch_LED1);
        switchStateLED4 = findViewById(R.id.id_switch_LED1);
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
    }

    public void buttonHandler_Connect(View view) {
        String myConnectCode = ""; myConnectCode = "2";
        update("Attempting to Connect to "+myServerIPAddress+" | Port: "+myPortNumber);
        // STILL MISSING SOMETHING!!
    }

    public void buttonHandler_GetTime(View view) {
        String myGetTime = ""; myGetTime = "4";
        // STILL MISSING SOMETHING!!
    }

    public void buttonHandler_GetTemperature(View view) {
        String myGetTemperature = "";myGetTemperature = "4";
        // STILL MISSING SOMETHING!!
    }

    public void switchHandler_LED1(View view) {
        String myLED1_onCode = "L1on", myLED1_offCode = "L1off" ;

        if(switchStateLED1.isChecked()){
            update("Sending Request to Switch LED1 ON..\n");
        }
        else{
            update("Sending Request to Switch LED1 OFF..\n");
        }
    }

    public void switchHandler_LED2(View view) {
        String myLED2_onCode = "L2on", myLED2_offCode = "L2off" ;

        if(switchStateLED2.isChecked()){
            serverMessageWindow.append("Sending Request to Switch LED2 ON..\n");
        }
        else{
            serverMessageWindow.append("Sending Request to Switch LED2 OFF..\n");
        }

    }

    public void switchHandler_LED3(View view) {
        String myLED3_onCode = "L3on", myLED3_offCode = "L3off" ;

        if(switchStateLED3.isChecked()){
            serverMessageWindow.append("Sending Request to Switch LED3 ON..\n");
        }
        else{
            update("Sending Request to Switch LED3 OFF..\n");
        }
    }

    public void switchHandler_LED4(View view) {
        String myLED4_onCode = "L4on", myLED3_offCode = "L4off" ;

        if(switchStateLED4.isChecked()){
            serverMessageWindow.append("Sending Request to Switch LED4 ON..\n");
        }
        else{
            serverMessageWindow.append("Sending Request to Switch LED4 OFF..\n");
        }
    }

    public void buttonHandler_PB1(View view) {
        String myPB1Code = ""; myPB1Code = "gpb1";
        update("Requesting state of PB1");
    }

    public void buttonHandler_PB2(View view) {
        String myPB2Code = ""; myPB2Code = "gpb2";
        update("Requesting state of PB2");
    }
    public void buttonHandler_PB3(View view) {
        String myPB3Code = ""; myPB3Code = "gpb3";
        update("Requesting state of PB3");
    }

    public void update(String message){
        serverMessageWindow.append(message);
    }

}