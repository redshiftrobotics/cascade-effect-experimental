#pragma config(Sensor, S1,     ,               sensorI2CCustom)
#pragma config(Sensor, S2,     HTPB,           sensorI2CCustom9V)

#include "Rotation.c"
#include "Motors.h"

void Move(int SpeedLeft, int SpeedRight)
{
	Motors_SetSpeed(S1, 1, 1, SpeedRight);
	Motors_SetSpeed(S1, 1, 2, -SpeedLeft);
}

int DistanceOff(int Target, int Current)
{
	int Direction = (Target - Current) / abs(Target - Current);

	//rotate in the other direction
	if(abs(Target - Current) > 180)
	{
		return -Direction * (360 - abs(Target - Current));
	}
	else
	{
		return Direction * abs(Target - Current);
	}
}

task main()
{
	int TargetDegree = Rotation_Degrees();


	while(true)
	{
		//globals
		bool Connected = Rotation_SensorConnected();
		int Degree = Rotation_Degrees();

		//display debug to NXT
		eraseDisplay();
		nxtDisplayString(1, "Working: %i", Connected);
		nxtDisplayString(2, "Degree: %i", Degree);

		//move the fucking robot!
		int MotionConstant = DistanceOff(TargetDegree, Degree);
		MotionConstant = (int)(MotionConstant / 2);

		Move(50 + MotionConstant, 50 - MotionConstant);

		nxtDisplayString(3, "Motion C: %i", MotionConstant);
		//sleep
		wait1Msec(50);
	}
}
