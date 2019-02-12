/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  
  
  Solenoid gear, hatch;
  Compressor c;


  Joystick joy;

  WPI_TalonSRX leftMaster,  rightMaster;
  VictorSPX leftSlave, rightSlave, left, right;

  DifferentialDrive drive;

  Encoder leftEncoder, rightEncoder;


  Double moveSpeed, rotation;

  Boolean state;



  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    left = new VictorSPX(21);
    right = new VictorSPX(22);
    leftMaster = new WPI_TalonSRX(11);
    rightMaster = new WPI_TalonSRX(12);
    leftSlave = new VictorSPX(13);
    rightSlave = new VictorSPX(14);

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);
    
    leftMaster.configOpenloopRamp(0.4);
    rightMaster.configOpenloopRamp(0.4);

    drive = new DifferentialDrive(leftMaster, rightMaster);
    drive.setMaxOutput(1);

    leftEncoder = new Encoder(0, 1);
    rightEncoder = new Encoder(2, 3);

    
    gear = new Solenoid(50, 0);
    hatch = new Solenoid(50, 1);
    
    c = new Compressor(0);
    c.setClosedLoopControl(true);

    joy = new Joystick(0);

    state = false;

    
     
    
    
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override 
  public void teleopPeriodic() {
    //shooter
    if(joy.getRawButton(5)){
      left.set(ControlMode.PercentOutput, 0.7);
      right.set(ControlMode.PercentOutput, -0.7);
    }
    else if(joy.getRawButton(6)){
      left.set(ControlMode.PercentOutput, -0.4);
      right.set(ControlMode.PercentOutput, 0.4);
    }
    else{
      left.set(ControlMode.PercentOutput, 0); 
      right.set(ControlMode.PercentOutput, 0);
    }


    //Hatch
    if (joy.getRawButton(7)){
      hatch.set(true);
    }
    else if (joy.getRawButton(8)){
      hatch.set(false);
    }

    //drive train
    moveSpeed = joy.getRawAxis(3) - joy.getRawAxis(2);
    rotation = joy.getRawAxis(0);
    drive.arcadeDrive(moveSpeed, - rotation);

    gear.set(joy.getRawButton(9));

  
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
