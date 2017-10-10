package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Lance He on 9/16/2017.
 */

public class Hardware {

    // Motor variable names
    public DcMotor frontLeftMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor leftLiftMotor = null;
    public DcMotor rightLiftMotor = null;

    // Servo variable names
    public Servo leftLiftServo = null;
    public Servo rightLiftServo = null;

    // Sensor variable names
    //VuForia**************************
    OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia;

    //IMU******************************
    private BNO055IMU imu;

    // Other variable names
    HardwareMap hwMap;
    private Orientation angles;
    private Acceleration gravity;

    // Constant variable names
    public static final double LEFT_LIFT_OPEN = 0;
    public static final double LEFT_LIFT_CLOSE = 0;
    public static final double RIGHT_LIFT_OPEN = 0;
    public static final double RIGHT_LIFT_CLOSE = 0;

    public Hardware()
    {
        hwMap = null;
    }

    public void init(HardwareMap hwMap)
    {

        // Save reference to Hardware map
        this.hwMap = hwMap;

        // Define Motors
        frontLeftMotor = hwMap.dcMotor.get("left_front");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        backRightMotor = hwMap.dcMotor.get("right_back");
       // leftLiftMotor = hwMap.dcMotor.get("left_lift");
       // rightLiftMotor = hwMap.dcMotor.get("right_lift");

        // Initialize Motors
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      //  leftLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      //  rightLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // ******MAY CHANGE *******  Fix Forward/Reverse under testing
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
       // leftLiftMotor.setDirection(DcMotor.Direction.FORWARD);
       // rightLiftMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
       // leftLiftMotor.setPower(0);
       // rightLiftMotor.setPower(0);

        // May use RUN_USING_ENCODERS if encoders are installed
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       // leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       // rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define servos
        //leftLiftServo = hwMap.servo.get("left_lift");
        //rightLiftServo = hwMap.servo.get("right_lift");

        // Initialize servos
        //leftLiftServo.setPosition(LEFT_LIFT_CLOSE);
        //rightLiftServo.setPosition(RIGHT_LIFT_CLOSE);

        // Define sensors
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters Vuparameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        Vuparameters.vuforiaLicenseKey = "AW8UaHn/////AAAAGSEYBhrOd0rKoinNfInqojQEhkiiEXypTuQY/KgFQY8k+6dx0JDcvHPtVpMjrNdnPY2boqh97cPelF2Si0HqBGdDErR3pyMYpV5evj1cppHIRqDHO0HjNkdbnvnoILWRJtn5+MLWocscbvi2Kbc9PBKxziwcIfl82Dl1t62Y5C8mL3iFF0fAtmTifuB0qp4r1wekhd9hScm6NTybtyBEk9QduDH8kyMOW56geGGhot9Oq+A/wk6spIv8NCQLJHgD30pj9TrtVBmWmA59x/pT9nBKBuI/ah1b+SZ5cSm5CTPv+Gra53m3y4k/usz66j8rCakKdj5DDg6+Ivpc3V6uQxDNpzh0HBE+x/zEGr7dMFRz";
        Vuparameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(Vuparameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibrafion sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");

        // Initialize sensors
        imu.initialize(parameters);

    }

    public void initTeleOp(HardwareMap hwMap){
        // Save reference to Hardware map
        this.hwMap = hwMap;

        // Define Motors
        frontLeftMotor = hwMap.dcMotor.get("left_front");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        backRightMotor = hwMap.dcMotor.get("right_back");
        // leftLiftMotor = hwMap.dcMotor.get("left_lift");
        // rightLiftMotor = hwMap.dcMotor.get("right_lift");

        // Initialize Motors
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  leftLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  rightLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // ******MAY CHANGE *******  Fix Forward/Reverse under testing
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        // leftLiftMotor.setDirection(DcMotor.Direction.FORWARD);
        // rightLiftMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        // leftLiftMotor.setPower(0);
        // rightLiftMotor.setPower(0);

        // May use RUN_USING_ENCODERS if encoders are installed
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define servos
        //leftLiftServo = hwMap.servo.get("left_lift");
        //rightLiftServo = hwMap.servo.get("right_lift");

        // Initialize servos
        //leftLiftServo.setPosition(LEFT_LIFT_CLOSE);
        //rightLiftServo.setPosition(RIGHT_LIFT_CLOSE);

        // Define sensors
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibrafion sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");

        // Initialize sensors
        imu.initialize(parameters);
    }

    public BNO055IMU getImu(){
        return imu;
    }
}
