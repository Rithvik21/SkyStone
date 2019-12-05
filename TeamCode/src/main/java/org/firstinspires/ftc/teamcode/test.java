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

drive(motorPower(.4),1000);
drive(motorPower(.2),300);
drive(motorPower(-.3),100);
} 
 public double motorPower (double speed) {
   double power = 1*speed;
   return power;
 }
  public void drive(double power, double time) {
    leftDrive.setPower(power);
      rightDrive.setPower(-power);
    sleep(time);
}

