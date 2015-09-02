package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class MattsAmazingOpmode extends OpMode

{
    //declare motor controller
    private DcMotorController v_dc_motor_controller_drive;

    //declare variables for left and right motors
    private DcMotor v_motor_left_drive;
    final int v_channel_left_drive = 1;

    private DcMotor v_motor_right_drive;
    final int v_channel_right_drive = 2;

    @Override public void init ()

    {
        //this is where hardware interfaces are initialized and bound
        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("drive_controller");

        v_motor_left_drive = hardwareMap.dcMotor.get ("left_drive");

        v_motor_right_drive = hardwareMap.dcMotor.get ("right_drive");


    }

    @Override public void start ()

    {
       //Actions in this method will execute before the 'Start' button is pressed on the controller, its the equivalent of the setup loop in Arduino programming.

    }

    public MattsAmazingOpmode()

    {

    }

    @Override public void loop ()

    {
        v_motor_left_drive.setPower (1.00);
        v_motor_right_drive.setPower (1.00);

    }

}
