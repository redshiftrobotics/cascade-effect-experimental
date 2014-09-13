#pragma config(Sensor, S1,     HTPB,           sensorI2CCustom9V)
#include "Rotation.c"

task main()
{
	while(true)
	{
		eraseDisplay();

		nxtDisplayString(1, "Working: %i", Rotation_SensorConnected());
		nxtDisplayString(2, "Degree: %i", Rotation_Degrees());

		wait1Msec(50);
	}
}
