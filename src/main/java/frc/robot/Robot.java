/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


import easypath.EasyPath;
import easypath.EasyPathConfig;
import easypath.FollowPath;
import easypath.Path;
import easypath.PathUtil;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  Solenoid gear, hatch;
  Compressor c;


  Joystick Driver, Operator;

  
  VictorSPX left, right, aim;

  Encoder leftEncoder, rightEncoder;

  


  Double moveSpeed, rotation;

  Boolean state;

  Timer time;

  UsbCamera frontCamera;
  UsbCamera lowCamera;

  DriveTrain dt;

  double rDist;

 EasyPathConfig config;
  private FollowPath m_autonomousCommand;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() { 
    
    left  = new VictorSPX(21);
    right = new VictorSPX(22);
    aim   = new VictorSPX(23);
    
    gear = new Solenoid(50, 0);
    //hatch = new Solenoid(50, 1);
    
    c = new Compressor(0);
    c.setClosedLoopControl(true);

    Driver = new Joystick(0);
    Operator = new Joystick(1);

    state = false;

    
    frontCamera = CameraServer.getInstance().startAutomaticCapture();
    lowCamera   = CameraServer.getInstance().startAutomaticCapture();

    

     dt = new DriveTrain();
     
     config = new EasyPathConfig(
        dt, // the subsystem itself
        dt::setLeftRightMotorSpeeds, // function to set left/right speeds
        // function to give EasyPath the length driven
        () -> PathUtil.defaultLengthDrivenEstimator(dt::getLeftDistance, dt::getRightDistance),
        dt::getHeading, // function to give EasyPath the heading of your robot
        dt::reset, // function to reset your encoders to 0
        0.07 // kP value for P loop
    );

    EasyPath.configure(config);
    
    // m_autonomousCommand = new FollowPath(
    //   new Path(t -> 
		// /* {"start":{"x":62,"y":218},"mid1":{"x":128,"y":221},"mid2":{"x":126,"y":176},"end":{"x":218,"y":175}} */
		// (276 * Math.pow(t, 2) + -288 * t + 9) / (486 * Math.pow(t, 2) + -408 * t + 198),
		// 164.34), x -> {
    //   if(x < 0.15) return 0.6;
    //   else if (x < 0.75) return 0.8;
    //   else return 0.25;
    //     }
    //   );
    
    



    time = new Timer();
    
    
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
  
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
        // -----Use this code------
    
     //Forward 
     //moveBotAuto(1.8, 1.0, 0); 
     //Turn left
    //moveBotAuto(0.45, 0, 1);
     //Forward
     // moveBotAuto(0.5, 1.0, 0); 
      //Turn right 
     //moveBotAuto(0.45, 0, -1);
      //Forward 
     //moveBotAuto(0.2, 1.0, 0);

    //EasyPath
     m_autonomousCommand = new FollowPath(
      new Path(t -> 
		/* {"start":{"x":62,"y":218},"mid1":{"x":128,"y":221},"mid2":{"x":126,"y":176},"end":{"x":218,"y":175}} */
		(276 * Math.pow(t, 2) + -288 * t + 9) / (486 * Math.pow(t, 2) + -408 * t + 198),
		164.34), x -> {
      if(x < 0.15) return 0.6;
      else if (x < 0.75) return 0.8;
      else return 0.25;
        }
      );
    m_autonomousCommand.start();
     
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override 
  public void teleopPeriodic() {
    //shooter
    // if(Operator.getRawAxis(2) < 0.01){
    //   left.set(ControlMode.PercentOutput, 1.0);
    //   right.set(ControlMode.PercentOutput, 1.0);
    // }
    // else if(Operator.getRawAxis(3) < 0.01){
    //   left.set(ControlMode.PercentOutput, -0.3);
    //   right.set(ControlMode.PercentOutput, -0.3);
    // }
    // else{
    //   left.set(ControlMode.PercentOutput, 0); 
    //   right.set(ControlMode.PercentOutput, 0);
    // }

    //Cargo rotation
    //aim.set(ControlMode.PercentOutput, Operator.getRawAxis(1));


    //Hatch
    // if (Operator.getRawButton(5)){
    //   hatch.set(true);
    // }
    // else if (Operator.getRawButton(6)){
    //   hatch.set(false);
    // }
    //Hatch push code
    /*
     if(Operator.getRawAxis(5)){

     }
    */
    //drive train
    moveSpeed = Driver.getRawAxis(3) - Driver.getRawAxis(2);
    rotation = Driver.getRawAxis(0);
    dt.drive.arcadeDrive(-moveSpeed, - rotation);

    gear.set(! Driver.getRawButton(3));

    rDist = rightEncoder.getDistance();

    System.out.print("LEFT ENCODER" + leftEncoder.getDistance());
    System.out.print("RIGHT ENCODER   " + rDist);
    


    
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void moveBotAuto(double timeEnd, double speed, double rotate) {
    time.reset();
    time.start();
      while (time.get() < timeEnd) {
        dt.drive.arcadeDrive(-(speed), rotate);
      }
      time.reset();
    }
}
