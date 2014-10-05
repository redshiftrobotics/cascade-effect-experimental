#pragma config(Sensor, S1, IROne, sensorI2CCustom)
#pragma config(Sensor, S2, IRTwo, sensorI2CCustom)

#include "IR.c"

task main()
{
	while(true)
	{
		//wait
		wait1Msec(100);

		//call the update (stores data in varaibles)
		IR_Update();

		//write updates varaibles to the display
		eraseDisplay();
		nxtDisplayString(1, "1B: %i", IR_OneValue.B);
		nxtDisplayString(2, "1C: %i", IR_OneValue.C);
		nxtDisplayString(3, "1D: %i", IR_OneValue.D);
		nxtDisplayString(4, "2B: %i", IR_TwoValue.B);
		nxtDisplayString(5, "2C: %i", IR_TwoValue.C);
		nxtDisplayString(6, "2D: %i", IR_TwoValue.D);

		//write to debug stream
		writeDebugStream("%i, ", IR_OneValue.B);
		writeDebugStream("%i, ", IR_OneValue.C);
		writeDebugStream("%i, ", IR_OneValue.D);
		writeDebugStream("%i, ", IR_TwoValue.B);
		writeDebugStream("%i, ", IR_TwoValue.C);
		writeDebugStreamLine("%i", IR_TwoValue.D);

		//does computation


		if(IR_OneValue.C > Threashold && IR_OneValue.D > Threashold && IR_TwoValue.B > Threashold && IR_TwoValue.C > Threashold)
		{
			nxtDisplayString(1, "Medium");
		}
		else if(IR_OneValue.C > Threashold && IR_TwoValue.C > Threashold && IR_OneValue.D < Threashold && IR_TwoValue.B < Threashold)
		{
			nxtDisplayString(1, "Long");
		}
		else if(IR_OneValue.D > Threashold && IR_TwoValue.B > Threashold && IR_OneValue.C < Threashold && IR_TwoValue.C < Threashold)
		{
			nxtDisplayString(1, "Short");
		}

		nxtDisplayString(6, "Left: %i", IR_OneDegree());
		nxtDisplayString(7, "Right: %i", IR_TwoDegree());

	}
}
