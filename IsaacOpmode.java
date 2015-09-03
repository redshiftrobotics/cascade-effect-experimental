package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

public class IsaacOpmode extends OpMode

{
    //declare motor controller
    private DcMotorController v_dc_motor_controller_drive;

    //declare variables for left and right motors
    private DcMotor v_motor_left_drive;
    private DcMotor v_motor_right_drive;

    @Override public void init ()

    {
        //this is where hardware interfaces are initialized and bound
        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("drive_controller");

        //this sets up the left and right motors over bluetooth
        //the motors are named on the robot
        v_motor_left_drive = hardwareMap.dcMotor.get ("left_drive");

        //reverses the left motor
        v_motor_left_drive.setDirection(DcMotor.Direction.REVERSE);

        v_motor_right_drive = hardwareMap.dcMotor.get ("right_drive");


    }

    //this is basically the setup loop
    @Override public void start ()

    {

    }

    //this is the constructor
    public IsaacOpmode()
    {

    }

    //this is a loop that continues to execute
    @Override public void loop ()
    {
        // gets the power
        float drive_power = gamepad1.left_stick_y;

        //if this is left, turn left
        float drive_difference = gamepad1.right_stick_x / 4;

        float left_power = drive_power + drive_difference;
        float right_power = drive_power - drive_difference;

        telemetry.addData( "01", "Right Stick X: " + gamepad1.right_stick_x);
        telemetry.addData( "02", "Left Stick Y: " + gamepad1.left_stick_y);

        telemetry.addData( "03", "Left Processed: " + left_power);
        telemetry.addData( "04", "Right Processed: " + right_power);

        //reverse the left motor
        v_motor_left_drive.setPower(Constrain(1, -1, -left_power));
        v_motor_right_drive.setPower(Constrain(1, -1, right_power));
    }

    float Constrain(float TopValue, float BottomValue, float Value)
    {
        if(Value > TopValue)
        {
            Value = TopValue;
        }

        if(Value < BottomValue)
        {
            Value = BottomValue;
        }

        return Value;
    }
}