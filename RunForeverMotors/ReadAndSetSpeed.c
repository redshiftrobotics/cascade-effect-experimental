#pragma config(Sensor, S1,     ,               sensorI2CCustom)

#include "../Libraries/I2C.h"

task main()
{
	while(true)
	{
		I2C_SetMotorSpeed(S1, 1, 1, 10);
		writeDebugStreamLine("Current encoder position: %i", I2C_GetEncoderPosition(S1, 1, 1));
		Sleep(1);
	}
}
