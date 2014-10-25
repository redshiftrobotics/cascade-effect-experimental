#pragma config(Sensor, S1, IROne, sensorI2CCustom)
#pragma config(Sensor, S2, IRTwo, sensorI2CCustom)
#pragma config(Sensor, S3, Motor, sensorI2CCustom)

#include "IR.c"
#include "Motors.h"

const int Threashold = 10;
const int MinorThreashold = 5;

void SetPower(int Left, int Right)
{
	Motors_SetSpeed(S3, 1, 2, -Right);
	Motors_SetSpeed(S3, 1, 1, Left);
}

int CheckPosition()
{
	IR_Update();

	if((IR_RightValue.D < (.25) * IR_RightValue.C || IR_RightValue.D < (.25) * IR_RightValue.B) && IR_RightValue.C < IR_LeftValue.C * (.6))
	{
		return 2;
	}
	else if(IR_LeftValue.C * (.6) < IR_RightValue.C && IR_RightValue.C * (.6) < IR_LeftValue.C)
	{
		return 3;
	}
	else
	{
		return 1;
	}
}

task main()
{
	//IR_Update();

	//int test = 13;
	//writeDebugStreamLine("");
	//writeDebugStreamLine("%i", IR_RightValue.C);
	//writeDebugStreamLine("%i", (.25) * IR_RightValue.C);
	writeDebugStreamLine("Position Number: %i", CheckPosition());

		//update IR
		IR_Update();


		writeDebugStream("%i, ", IR_LeftValue.B);
		writeDebugStream("%i, ", IR_LeftValue.C);
		writeDebugStream("%i, ", IR_LeftValue.D);

		writeDebugStream("%i, ", IR_RightValue.B);
		writeDebugStream("%i, ", IR_RightValue.C);
		writeDebugStreamLine("%i", IR_RightValue.D);


		//SetPower(10, -10);
		//writeDebugStreamLine("%i", Distance());

		//writeDebugStreamLine("%i", IR_LeftValue.C + IR_RightValue.C + IR_RightValue.B + IR_LeftValue.B + IR_RightValue.D + IR_LeftValue.D);
}
