#pragma config(Sensor, S3, IROne, sensorI2CCustom)
#pragma config(Sensor, S2, IRTwo, sensorI2CCustom)
#pragma config(Sensor, S1, Motor, sensorI2CCustom)

#include "../Libraries/IR.c"
#include "../Libraries/Motors.h"

const int MinorThreashold = 5;

void SetPower(int Left, int Right)
{
	Motors_SetSpeed(S3, 1, 2, -Right);
	Motors_SetSpeed(S3, 1, 1, Left);
}

int CheckPosition()
{
	IR_Update();

	if((IR_RightValue.C + IR_LeftValue.C > 100)
	{
		return 3;
	}
	else if(IR_LeftValue.C + IR_RightValue.C > 50)
	{
		return 2;
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
		writeDebugStreamLine("%i, ", IR_LeftValue.D);

		writeDebugStream("%i, ", IR_RightValue.B);
		writeDebugStream("%i, ", IR_RightValue.C);
		writeDebugStreamLine("%i", IR_RightValue.D);

		//writeDebugStreamLine("%i", IR_LeftValue.C + IR_RightValue.C + IR_RightValue.B + IR_LeftValue.B + IR_RightValue.D + IR_LeftValue.D);
}
