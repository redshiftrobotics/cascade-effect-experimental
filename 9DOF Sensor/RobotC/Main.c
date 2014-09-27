#pragma config(Sensor, S1,     ,               sensorI2CCustom)
#pragma config(Sensor, S2,     HTPB,           sensorI2CCustom9V)

#include "Rotation.c"
#include "Motors.h"

void Move(int SpeedLeft, int SpeedRight)
{
	Motors_SetSpeed(S1, 1, 1, SpeedRight);
	Motors_SetSpeed(S1, 1, 2, -SpeedLeft);
}

task main()
{
	int TargetDegree = 180;

	while(true)
	{
		//globals
		bool Connected = Rotation_SensorConnected();
		int Degree = Rotation_Degrees();

		//display debug to NXT
		eraseDisplay();
		nxtDisplayString(1, "Working: %i", Connected);
		nxtDisplayString(2, "Degree: %i", Degree);

		writeDebugStreamLine("%i", Degree);

		//move
		int Error = (Degree - TargetDegree) / 7.2;
		nxtDisplayString(3, "Error: %i", Error);
		Move(10, -10);

		//sleep
		wait1Msec(50);
	}
}
