/******************************************************************************

Software License Agreement

The software supplied herewith by Microchip Technology Incorporated
(the “Company”) for its PICmicro® Microcontroller is intended and
supplied to you, the Company’s customer, for use solely and
exclusively on Microchip PICmicro Microcontroller products. The
software is owned by the Company and/or its supplier, and is
protected under applicable copyright laws. All rights are reserved.
Any use in violation of the foregoing restrictions may subject the
user to criminal sanctions under applicable laws, as well as to
civil liability for the breach of the terms and conditions of this
license.

THIS SOFTWARE IS PROVIDED IN AN “AS IS” CONDITION. NO WARRANTIES,
WHETHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT NOT LIMITED
TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
PARTICULAR PURPOSE APPLY TO THIS SOFTWARE. THE COMPANY SHALL NOT,
IN ANY CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL OR
CONSEQUENTIAL DAMAGES, FOR ANY REASON WHATSOEVER.

*******************************************************************************/

// Clock Constants
#if defined (__32MX460F512L__) || defined (__32MX360F512L__) || defined (__32MX795F512L__)
#define SYS_CLOCK (80000000L)
#elif defined (__32MX220F032D__) || defined (__32MX250F128D__)
#define SYS_CLOCK (40000000L)
#endif
#define GetSystemClock()            (SYS_CLOCK)
#define GetPeripheralClock()        (SYS_CLOCK/2)
#define GetInstructionClock()       (SYS_CLOCK)
#define I2C_CLOCK_FREQ              5000

// EEPROM Constants
#define TMP2_I2C_BUS	              I2C1      //change to I2C2 in case of using the other pin!
#define TMP2_ADDRESS                0x4B        // 0b1010000 TMP2 address
#define CONFIG_REG									0x03				// Configuration register address
#define TEMP_REG_MSB								0x00				// MSB temperature register address
#define TMP2_DEV_ID_REG_ADDR				0x0B				// device ID (Register Address 0x0B)
#define TMP2_DEV_ID									0xCB
#define TMP2_ADDR_WRITE							0b10010110
#define TMP2_ADDR_READ 							0b10010111
#define	USING_START_METHOD					FALSE
#define	USING_RESTART_METHOD 				TRUE
#define ACK													TRUE
#define NACK												FALSE
#define TMP2_RESOLUTION							0.0078
