package org.firstinspires.ftc.teamcode;

@Autonomous(name="Test")
public class Test extends LinearOpMode {
private DcMotor leftDrive, rightDrive;
double power = .5;

@Override
public void runOpMode()
{
leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

waitForStart();

leftDrive.setPower(power);
rightDrive.setPower(-power);
sleep(1000);
}
}
