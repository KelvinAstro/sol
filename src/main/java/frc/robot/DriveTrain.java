/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  public WPI_TalonSRX leftMaster,  wrightMaster;


  public VictorSPX leftSlave, rightSlave;

  public DifferentialDrive drive;

  public Encoder leftEncoder, rightEncoder;

  public final double DISTANCE_PER_ENCODER_PULSE = 12 / 215; // 12 inches / ~215 ticks


  public DriveTrain() {
    
    leftMaster = new WPI_TalonSRX(11);
    wrightMaster = new WPI_TalonSRX(12);
    leftSlave = new VictorSPX(13);
    rightSlave = new VictorSPX(14);

    leftSlave.follow(leftMaster);
    rightSlave.follow(wrightMaster);


    leftMaster.configOpenloopRamp(0.2);
    wrightMaster.configOpenloopRamp(0.2);

    drive = new DifferentialDrive(leftMaster, wrightMaster);
    drive.setMaxOutput(1);

    leftEncoder = new Encoder(2, 3);
    rightEncoder = new Encoder(0, 1);
    rightEncoder.setName("right");
    leftEncoder.setName("left");
    leftEncoder.setDistancePerPulse(DISTANCE_PER_ENCODER_PULSE);
    rightEncoder.setDistancePerPulse(DISTANCE_PER_ENCODER_PULSE);


   
  }

  public void drive(double speedInput, double rotationInput){
    double speed = desensitize(speedInput, 2);
        double rotation = desensitize(rotationInput, 2);
        drive.curvatureDrive(speed, rotation, true);
  }

  private double desensitize(double val, double power) {
    return Math.pow(Math.abs(val), power - 1) * val;
}


public void setLeftRightMotorSpeeds(double leftPower, double rightPower) {
  leftMaster.set(leftPower);
  wrightMaster.set(-rightPower);
  drive.arcadeDrive(rightPower, 0);
}

public double getLeftDistance() {
  return leftEncoder.getDistance();
  }

  public double getRightDistance() {
  return rightEncoder.getDistance();
  }

//   public double getHeading() {
//     return gyro.getAngle();
// }

  public void reset() {
    // gyro.reset();
    leftEncoder.reset();
    rightEncoder.reset();
    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
