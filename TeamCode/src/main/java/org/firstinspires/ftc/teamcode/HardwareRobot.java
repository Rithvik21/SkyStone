package org.firstinspires.ftc.teamcode;

public class HardwareRobot {
public DcMotor leftDrive, rightDrive;

public HardwareRobot()  {}
public void init(hardwareMap hwMap) {
hardwareMap = hwMap;
leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

leftDrive.setDirection(DcMotor.Direction.FORWARD);
rightDrive.setDirection(DcMotor.Direction.BACKWARD);
}
}
