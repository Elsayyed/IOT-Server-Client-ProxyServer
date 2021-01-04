/* 
 * File:   PortConfig.h
 * Author: Elsayyed
 *
 * Created on September 16, 2020, 10:18 PM
 */

#ifndef PORTCONFIG_H
#define	PORTCONFIG_H

// IOPORT bit masks can be found in ports.h
#define LED1_IOPORT	IOPORT_G
#define	LED1_BIT	BIT_12
#define LED2_IOPORT	IOPORT_G
#define	LED2_BIT	BIT_13
#define LED3_IOPORT	IOPORT_G
#define	LED3_BIT	BIT_14
#define LED4_IOPORT	IOPORT_G
#define	LED4_BIT	BIT_15

#define BTN1_IOPORT IOPORT_G //only 1 used
#define BTN1_BIT    BIT_6

#define BTN2_IOPORT IOPORT_G //extra for later projects
#define BTN2_BIT    BIT_7

#define BTN3_IOPORT IOPORT_A //extra for later projects
#define BTN3_BIT    BIT_0

#ifdef	__cplusplus
extern "C" {
#endif

    
#ifdef	__cplusplus
}
#endif

#endif	/* PORTCONFIG_H */

