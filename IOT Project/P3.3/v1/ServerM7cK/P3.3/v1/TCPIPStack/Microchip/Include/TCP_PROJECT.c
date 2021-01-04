#include "TCPIPConfig.h"
#if defined(STACK_USE_TCP_TO_UPPER_SERVER)
#include "TCPIP Stack/TCPIP.h"
#include <ctype.h> 
#include <plib.h>
#include <string.h>
#include "PortConfig.h"

// Defines which port the server will listen on
#define TCP_PROJECT_1_PORT    7777

static enum _myState {
    SM_OPEN_SERVER_SOCKET = 0,
    SM_LISTEN_FOR_CLIENT_CONNECTION,
    SM_WAIT_FOR_COMPLETE_CLIENT_MESSAGE,
    SM_PROCESS_COMMAND,
    SM_DISCONNECT_CLIENT,
    //SM_DISPLAY_MENU
} myState = SM_OPEN_SERVER_SOCKET;

void commands(BYTE);
static TCP_SOCKET mySocket;
static int commandIndex = 0, ackMessageSize = 0;
static char command[7], ackMessage[15];
int size = 0;

void TCPProjectOne(void) {

    WORD numBytes = 0;
    BYTE theChar = 0;
    BYTE lastChar = 0;

    switch (myState) {
        case SM_OPEN_SERVER_SOCKET:
            mySocket = TCPOpen(0, TCP_OPEN_SERVER, TCP_PROJECT_1_PORT, TCP_PURPOSE_TCP_TO_UPPER_SERVER);
            if (mySocket == INVALID_SOCKET)
                return;
            myState = SM_LISTEN_FOR_CLIENT_CONNECTION;
            break;


        case SM_LISTEN_FOR_CLIENT_CONNECTION:
            if (TCPIsConnected(mySocket) == FALSE)
                return;
            else {
                size = sizeof ("Hello: ") - 1;
                TCPPutArray(mySocket, (BYTE*) "Hello: ", size);
                TCPPut(mySocket, (BYTE) 0xFF);
                TCPFlush(mySocket);
                myState = SM_WAIT_FOR_COMPLETE_CLIENT_MESSAGE;
                break;
            }


            //        case SM_DISPLAY_MENU:
            //            if (!TCPIsConnected(mySocket)) return;
            //
            //            int size = sizeof ("\x1B[2J \x1B[31m  LED SERVER MENU\n\r"
            //                    "1:LED1 ON\t2:LED1 OFF\n\r"
            //                    "3:LED2 ON\t4:LED2 OFF\n\r"
            //                    "5:LED3 ON\t6:LED3 OFF\n\r"
            //                    "7:LED4 ON\t8:LED4 OFF\n\r"
            //                    "a:BTN 1\ts:BTN 2\td:BTN 3\n\r");
            //
            //            if (TCPIsPutReady(mySocket) < size)
            //                return;
            //
            //            TCPPutArray(mySocket, (BYTE*) "\x1B[2J \x1B[31m  LED SERVER MENU\n\r"
            //                    "1:LED1 ON\t5:LED1 OFF\n\r"
            //                    "2:LED2 ON\t6:LED2 OFF\n\r"
            //                    "3:LED3 ON\t7:LED3 OFF\n\r"
            //                    "4:LED4 ON\t8:LED4 OFF\n\r"
            //                    "\ta:BTN 1\ts:BTN 2\td:BTN 3\n\r", size);
            //
            //            TCPFlush(mySocket);
            //
            //            myState = SM_PROCESS_COMMAND;
            //            break;

        case SM_WAIT_FOR_COMPLETE_CLIENT_MESSAGE:
            if (TCPIsConnected(mySocket) == FALSE) {
                myState = SM_DISCONNECT_CLIENT;
                return;
            }
            if (TCPIsGetReady(mySocket) < 1) {
                return;
            }

            TCPGet(mySocket, &theChar);

            if (theChar == (BYTE) 0xFF) {
                commandIndex = 0;
                myState = SM_PROCESS_COMMAND;
            } else {
                command[commandIndex] = theChar;
                commandIndex++;
            }
            break;

        case SM_PROCESS_COMMAND:
            if (TCPIsConnected(mySocket) == FALSE) {
                myState = SM_DISCONNECT_CLIENT;
                return;
            }

            if (command[0] == 'd') {
                size = sizeof ("Disconnect Ack: ") - 1;
                while (TCPIsPutReady(mySocket) < (WORD) size) {
                    return;
                }
                TCPPutArray(mySocket, (BYTE*) "Disconnect Ack: ", size);
                TCPPut(mySocket, (BYTE) 0xFF);
                TCPFlush(mySocket);
                myState = SM_DISCONNECT_CLIENT;
                break;
            } else if (command[0] == 'q') {
                size = sizeof ("Quit Ack: ") - 1;
                while (TCPIsPutReady(mySocket) < (WORD) size) {
                    return;
                }
                TCPPutArray(mySocket, (BYTE*) "Quit Ack: ", size);
                TCPPut(mySocket, (BYTE) 0xFF);
                TCPFlush(mySocket);
                myState = SM_DISCONNECT_CLIENT;
                break;
            }
            else if (command[0] == 'g' && command[1] == 'p') {
                if (command[3] == '1') {
                    if (BUTTON0_IO == BUTTON_DOWN) {
                        size = sizeof ("PB1Down ") - 1;
                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                            return;
                        }
                        TCPPutArray(mySocket, (BYTE*) "PB1Down", size);
                        TCPPut(mySocket, (BYTE) 0xFF);
                        TCPFlush(mySocket);
                    } else {
                        size = sizeof ("PB1Up ") - 1;
                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                            return;
                        }
                        TCPPutArray(mySocket, (BYTE*) "PB1Up", size);
                        TCPPut(mySocket, (BYTE) 0xFF);
                        TCPFlush(mySocket);
                    }
                }
                    if (command[3] == '2') {
                        if (BUTTON1_IO == BUTTON_DOWN) {
                            size = sizeof ("PB2Down ") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "PB2Down", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else {
                            size = sizeof ("PB2Up ") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "PB2Up", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        }
                    }
                    if (command[3] == '3') {
                        if (BUTTON2_IO == BUTTON_DOWN) {
                            size = sizeof ("PB3Down ") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "PB3Down", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else {
                            size = sizeof ("PB3Up ") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "PB3Up", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        }
                    }
                        //                else if (command[3] == '2') {
                        //                    if (BUTTON1_IO == BUTTON_DOWN) {
                        //
                        //                        size = sizeof ("PB2Down_Ack") - 1;
                        //                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                        //                            return;
                        //                        }
                        //                        //                      while ((TCPIsPutReady(MySocket)) < sizeof ("PB2Down"));
                        //                        TCPPutArray(mySocket, (BYTE) "PB2Down_Ack", size);
                        //                        TCPPut(mySocket, (BYTE) 0xFF);
                        //                        TCPFlush(mySocket);
                        //                    } else {
                        //                        size = sizeof ("PB2Up_Ack") - 1;
                        //                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                        //                            return;
                        //                        }
                        //                        TCPPutArray(mySocket, (BYTE) "PB2Up", size);
                        //                        TCPPut(mySocket, (BYTE) 0xFF);
                        //                        TCPFlush(mySocket);
                        //                    }
                        //                } else if (command[3] == '3') {
                        //                    if (BUTTON2_IO == BUTTON_DOWN) {
                        //                        size = sizeof ("PB3Down") - 1;
                        //                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                        //                            return;
                        //                        }
                        //                        TCPPutArray(mySocket, (BYTE) "PB3Down", size);
                        //                        TCPPut(mySocket, (BYTE) 0xFF);
                        //                        TCPFlush(mySocket);
                        //                    } else {
                        //                        size = sizeof ("PB3Up") - 1;
                        //                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                        //                            return;
                        //                        }
                        //                        TCPPutArray(mySocket, (BYTE) "PB3Up", size);
                        //                        TCPPut(mySocket, (BYTE) 0xFF);
                        //                        TCPFlush(mySocket);
                        //                    }
                        //                } else if (command[3] == '3') {
                        //                    if (BUTTON2_IO == BUTTON_DOWN) {
                        //                        size = sizeof ("PB3Down") - 1;
                        //                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                        //                            return;
                        //                        }
                        //                        TCPPutArray(mySocket, (BYTE) "PB3Down", size);
                        //                        TCPPut(mySocket, (BYTE) 0xFF);
                        //                        TCPFlush(mySocket);
                        //                    } else {
                        //                        size = sizeof ("PB3Up") - 1;
                        //                        while (TCPIsPutReady(mySocket) < (WORD) size) {
                        //                            return;
                        //                        }
                        //                        TCPPutArray(mySocket, (BYTE) "PB3Up", size);
                        //                        TCPPut(mySocket, (BYTE) 0xFF);
                        //                        TCPFlush(mySocket);
                        //                    }
                        //                }
                    } else if (command[0] = 'L') {
                        if (command[1] == '1' && command [2] == 'o' & command [3] == 'n') {
                            LED0_IO = 1;
                            size = sizeof ("L1on Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L1on Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                            //                    myState = SM_WAIT_FOR_COMPLETE_CLIENT_MESSAGE;
                        } else if (command[1] == '1' && command [3] == 'f') { //L1off
                            LED0_IO = 0;
                            size = sizeof ("L1off Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L1off Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                            //                    myState = SM_WAIT_FOR_COMPLETE_CLIENT_MESSAGE;
                        } else if (command[1] == '2' && command [2] == 'o' & command [3] == 'n') {
                            LED1_IO = 1;
                            size = sizeof ("L2on Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L2on Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else if (command[1] == '2' && command [3] == 'f') {
                            LED1_IO = 0;
                            size = sizeof ("L2off Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L2off Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else if (command[1] == '3' && command [2] == 'o' & command [3] == 'n') {
                            LED2_IO = 1;
                            size = sizeof ("L3on Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L3on Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else if (command[1] == '3' && command [3] == 'f') {
                            LED2_IO = 0;
                            size = sizeof ("L3off Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L3off Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else if (command[1] == '4' && command [2] == 'o' & command [3] == 'n') {
                            LED3_IO = 1;
                            size = sizeof ("L4on Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L4on Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        } else if (command[1] == '4' && command [3] == 'f') {
                            LED3_IO = 0;
                            size = sizeof ("L4off Ack:") - 1;
                            while (TCPIsPutReady(mySocket) < (WORD) size) {
                                return;
                            }
                            TCPPutArray(mySocket, (BYTE*) "L4off Ack: ", size);
                            TCPPut(mySocket, (BYTE) 0xFF);
                            TCPFlush(mySocket);
                        }
                    }


                    myState = SM_WAIT_FOR_COMPLETE_CLIENT_MESSAGE;
                    break;

                    case SM_DISCONNECT_CLIENT:
                    TCPDisconnect(mySocket);
                    myState = SM_LISTEN_FOR_CLIENT_CONNECTION;
                    break;
                }
            }

#endif