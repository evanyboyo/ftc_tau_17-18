package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;

/**
 * Created by Evan Yu on 3/25/2018.
 */
//@TeleOp
public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor left_drive;
    private DcMotor right_drive;
    private DcMotor lift_motor;
    private Servo claw;
    @Override
    public void runOpMode(){
        // private double GPLX = 0;
        double GPLY = 0;
        // private double GPRX = 0;
        double GPRY = 0;

        //boolean firstTime = true;


        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        right_drive = hardwareMap.get(DcMotor.class,"right_drive");
        lift_motor = hardwareMap.get(DcMotor.class,"lift_motor");
        claw = hardwareMap.get(Servo.class,"claw");
        claw.setPosition(1);
        telemetry.addData("Readiness", "Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            // GPLX = gamepad1.left_stick_x;
            GPLY = -this.gamepad1.left_stick_y;
            // GPRX = gamepad1.right_stick_x;
            GPRY = this.gamepad1.right_stick_y;



                left_drive.setPower(250 * GPLY);
                //telemetry.addData("Target power",250*GPLY);
                //telemetry.addData("Left Motor power", left_drive.getPower());
                //telemetry.addData("Stick value", this.gamepad1.left_stick_y);
                //telemetry.addData("Status", "Running");
                //telemetry.update();


                right_drive.setPower(GPRY  * 250);
                //telemetry.addData("Target power",250*GPRY);
                //telemetry.addData("Motor power", right_drive.getPower());
                //telemetry.addData("Stick value", this.gamepad1.right_stick_y);
                //telemetry.addData("Status", "Running");
                //telemetry.update();
            if(gamepad1.left_bumper){
                claw.setPosition(0.5);
            }
            if(gamepad1.right_bumper){
                claw.setPosition(1);
            }
            if(gamepad1.dpad_up){
                while(gamepad1.dpad_up)
                    lift_motor.setPower(-100);
                lift_motor.setPower(0);
            }
            if(gamepad1.dpad_down){
                while(gamepad1.dpad_down)
                    lift_motor.setPower(100);
                lift_motor.setPower(0);
            }


        telemetry.addData("Left drive target power", 250*GPLY);
        telemetry.addData("Right drive target power", 250*GPRY);
        telemetry.addData("Left motor power", left_drive.getPower());
        telemetry.addData("Right motor power", right_drive.getPower());
        telemetry.addData("Left stick value", this.gamepad1.left_stick_y);
        telemetry.addData("Right stick value", this.gamepad1.right_stick_y);
        telemetry.addData("Status","Running");
        telemetry.update();
    }


    //Hardware robot = new Hardware();

    }
}
