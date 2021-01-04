#include <plib.h>
#include <HardwareProfileI2C.h>


// ****************************************************************************
// ****************************************************************************
// Local Support Routines
// ****************************************************************************
// ****************************************************************************

/*******************************************************************************
  Function:
    BOOL BeginTransfer( BOOL restart )

  Summary:
    Starts (or restarts) a transfer to/from the I2C Slave.

  Description:
    This routine starts (or restarts) a transfer to/from the I2C Slave, waiting (in
    a blocking loop) until the start (or re-start) condition has completed.

  Precondition:
    The I2C module must have been initialized.

  Parameters:
    restart - If FALSE, send a "Start" condition
            - If TRUE, send a "Restart" condition
    
  Returns:
    TRUE    - If successful
    FALSE   - If a collision occured during Start signaling
    
  Example:
    <code>
    BeginTransfer(USING_START_METHOD);
    </code>

  Remarks:
    This is a blocking routine that waits for the bus to be idle and the Start
    (or Restart) signal to complete.
 *****************************************************************************/

BOOL BeginTransfer(BOOL restart) {
    I2C_STATUS status;

    // Send the Start (or Restart) signal
    if (restart) {
        I2CRepeatStart(TMP2_I2C_BUS);
    } else {
        // Wait for the bus to be idle, then start the transfer
        while (!I2CBusIsIdle(TMP2_I2C_BUS));

        if (I2CStart(TMP2_I2C_BUS) != I2C_SUCCESS) {
            DBPRINTF("Error: Bus collision during transfer Start\n");
            return FALSE;
        }
    }

    // Wait for the signal to complete
    do {
        status = I2CGetStatus(TMP2_I2C_BUS);

    } while (!(status & I2C_START));

    return TRUE;
}

/*******************************************************************************
  Function:
    BOOL TransmitOneByte( UINT8 data )

  Summary:
    This transmits one byte to the I2C Slave.

  Description:
    This transmits one byte to the I2C Slave, and reports errors for any bus
    collisions.

  Precondition:
    The transfer must have been previously started.

  Parameters:
    data    - Data byte to transmit

  Returns:
    TRUE    - Data was sent successfully
    FALSE   - A bus collision occured

  Example:
    <code>
    TransmitOneByte(0xAA);
    </code>

  Remarks:
    This is a blocking routine that waits for the transmission to complete.
 *****************************************************************************/

BOOL TransmitOneByte(UINT8 data) {
    // Wait for the transmitter to be ready
    while (!I2CTransmitterIsReady(TMP2_I2C_BUS));

    // Transmit the byte
    if (I2CSendByte(TMP2_I2C_BUS, data) == I2C_MASTER_BUS_COLLISION) {
        DBPRINTF("Error: I2C Master Bus Collision\n");
        return FALSE;
    }

    // Wait for the transmission to finish
    while (!I2CTransmissionHasCompleted(TMP2_I2C_BUS));

    return TRUE;
}

/*******************************************************************************
  Function:
    void StopTransfer( void )

  Summary:
    Stops a transfer to/from the I2C Slave.

  Description:
    This routine Stops a transfer to/from the I2C Slave, waiting (in a
    blocking loop) until the Stop condition has completed.

  Precondition:
    The I2C module must have been initialized & a transfer started.

  Parameters:
    None.
    
  Returns:
    None.
    
  Example:
    <code>
    StopTransfer();
    </code>

  Remarks:
    This is a blocking routine that waits for the Stop signal to complete.
 *****************************************************************************/

void StopTransfer(void) {
    I2C_STATUS status;

    // Send the Stop signal
    I2CStop(TMP2_I2C_BUS);

    // Wait for the signal to complete
    do {
        status = I2CGetStatus(TMP2_I2C_BUS);

    } while (!(status & I2C_STOP));
}

void Acknowledge(BOOL ack) {
    I2CAcknowledgeByte(TMP2_I2C_BUS, ack);
    while (I2CAcknowledgeHasCompleted(TMP2_I2C_BUS) == FALSE);
}

float mainI2C(void)   {
    float temp;
    UINT8 i, dummy;
    UINT32 actualClock;

    typedef union {

        struct {
            char LB;
            char HB;
        } bytes;
        short int _16bitValue;
    } TMP2;
    TMP2 myTemp;

    // Set the I2C baudrate
    actualClock = I2CSetFrequency(TMP2_I2C_BUS, GetPeripheralClock(), I2C_CLOCK_FREQ);
    if (abs(actualClock - I2C_CLOCK_FREQ) > I2C_CLOCK_FREQ / 10) {
        DBPRINTF("Error: TMP2_I2C_BUS clock frequency (%u) error exceeds 10%%.\n", (unsigned) actualClock);
    }

    // Enable the I2C bus
    I2CEnable(TMP2_I2C_BUS, TRUE);

    for (i = 0; i < 4; i++) {
        while (BeginTransfer(USING_START_METHOD) == FALSE); //Keep on trying to set the start condition
        TransmitOneByte(TMP2_ADDR_WRITE); //device address with WRITE MODE
        while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
        TransmitOneByte(TMP2_DEV_ID_REG_ADDR); //select configuration register
        while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
        while (BeginTransfer(USING_RESTART_METHOD) == FALSE); //Keep on trying to set the restart condition
        TransmitOneByte(TMP2_ADDR_READ); //device address with READ MODE
        while (I2CReceiverEnable(TMP2_I2C_BUS, TRUE) == I2C_RECEIVE_OVERFLOW);
        while (!I2CReceivedDataIsAvailable(TMP2_I2C_BUS));
        Acknowledge(NACK);
        while ((dummy = I2CGetByte(TMP2_I2C_BUS) != TMP2_DEV_ID));
        StopTransfer();
    }

    while (BeginTransfer(USING_START_METHOD) == FALSE); //Keep on trying to set the start condition
    TransmitOneByte(TMP2_ADDR_WRITE); //device address with WRITE MODE
    while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
    TransmitOneByte(CONFIG_REG); //select configuration register
    while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
    TransmitOneByte(0b10000000); //set configuration register to 16-bit mode, all others default
    while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
    StopTransfer();

    for (i = 0; i < 4; i++) {
        while (BeginTransfer(USING_START_METHOD) == FALSE); //Keep on trying to set the start condition
        TransmitOneByte(TMP2_ADDR_WRITE); //device address with WRITE MODE
        while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
        TransmitOneByte(CONFIG_REG); //select configuration register
        while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
        while (BeginTransfer(USING_RESTART_METHOD) == FALSE); //Keep on trying to set the restart condition
        TransmitOneByte(TMP2_ADDR_READ); //device address with READ MODE
        while (I2CReceiverEnable(TMP2_I2C_BUS, TRUE) == I2C_RECEIVE_OVERFLOW);
        while (!I2CReceivedDataIsAvailable(TMP2_I2C_BUS));
        Acknowledge(NACK);
        while ((dummy = I2CGetByte(TMP2_I2C_BUS) != 0b10000000));
        StopTransfer();
    }

    
        while (BeginTransfer(USING_START_METHOD) == FALSE); //Keep on trying to set the start condition
        TransmitOneByte(TMP2_ADDR_WRITE); //device address with WRITE MODE
        while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
        TransmitOneByte(TEMP_REG_MSB); //select configuration register
        while (I2CByteWasAcknowledged(TMP2_I2C_BUS) == FALSE);
        while (BeginTransfer(USING_RESTART_METHOD) == FALSE); //Keep on trying to set the restart condition
        TransmitOneByte(TMP2_ADDR_READ); //device address with READ MODE
        while (I2CReceiverEnable(TMP2_I2C_BUS, TRUE) == I2C_RECEIVE_OVERFLOW);
        while (!I2CReceivedDataIsAvailable(TMP2_I2C_BUS));
        Acknowledge(ACK);
        myTemp.bytes.HB = I2CGetByte(TMP2_I2C_BUS);
        while (I2CReceiverEnable(TMP2_I2C_BUS, TRUE) == I2C_RECEIVE_OVERFLOW);
        while (!I2CReceivedDataIsAvailable(TMP2_I2C_BUS));
        Acknowledge(NACK);
        
        StopTransfer();
        myTemp.bytes.LB = I2CGetByte(TMP2_I2C_BUS);
        return temp = (float) myTemp._16bitValue*TMP2_RESOLUTION;

}