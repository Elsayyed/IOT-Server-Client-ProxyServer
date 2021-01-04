#include <plib.h>
#include <xc.h>
#include "PortConfig.h"

void portInit(void) {

    PORTSetPinsDigitalOut(LED1_IOPORT, LED1_BIT); //Set the pin connected to LED1 as output
    PORTSetPinsDigitalOut(LED2_IOPORT, LED2_BIT); //Set the pin connected to LED2 as output
    PORTSetPinsDigitalOut(LED3_IOPORT, LED3_BIT); //Set the pin connected to LED3 as output
    PORTSetPinsDigitalOut(LED4_IOPORT, LED4_BIT); //Set the pin connected to LED4 as output
   
    PORTSetPinsDigitalIn (BTN1_IOPORT, BTN1_BIT); //Set the pin connected to BTN1 as input
    PORTSetPinsDigitalIn (BTN2_IOPORT, BTN2_BIT); //Set the pin connected to BTN2 as input
    PORTSetPinsDigitalIn (BTN3_IOPORT, BTN3_BIT); //Set the pin connected to BTN3 as input
    
    PORTClearBits(LED1_IOPORT, LED1_BIT); // LED1 off
    PORTClearBits(LED2_IOPORT, LED2_BIT); // LED2 off
    PORTClearBits(LED3_IOPORT, LED3_BIT); // LED3 off
    PORTClearBits(LED4_IOPORT, LED4_BIT); // LED4 off
}

//void ledBlink(int ledValue, int delayValue){
//    
//    unsigned int i;
//    
//    if (ledValue == 1){    
//    PORTSetBits(LED1_IOPORT, LED1_BIT); // LED1 on
//    for (i = 0; i < delayValue; i++);
//    PORTClearBits(LED1_IOPORT, LED1_BIT); // LED1 off
//    }
//    
//    if (ledValue == 2){
//        PORTSetBits(LED2_IOPORT, LED2_BIT); // LED2 on
//        for (i = 0; i < delayValue; i++); // Delay some time
//        PORTClearBits(LED2_IOPORT, LED2_BIT); // LED1 off
//    }
//    
//    if (ledValue == 3){
//        PORTSetBits(LED3_IOPORT, LED3_BIT); // LED3 on
//        for (i = 0; i < delayValue; i++); // Delay some time
//        PORTClearBits(LED3_IOPORT, LED3_BIT); // LED1 off
//    }
//    
//    if (ledValue == 4){
//        PORTSetBits(LED4_IOPORT, LED4_BIT); // LED4 on
//        for (i = 0; i < delayValue; i++); // Delay some time
//        PORTClearBits(LED4_IOPORT, LED4_BIT); // LED4 off  
//    }
//    
//}