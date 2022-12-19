// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

//import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */


public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  //right motors
   //private final VictorSP rightMotor1 = new VictorSP(1);

  Timer timer;
  
  
  
  private final VictorSP rightMotor1 = new VictorSP(0);
   private final VictorSP rightMotor2 = new VictorSP(1);

   //left motors
   //private final VictorSP leftMotor1 = new VictorSP(1);

   
   private final VictorSP leftMotor1 = new VictorSP(2);
   private final VictorSP leftMotor2 = new VictorSP(3);

   //speedControllersGroup

   private final MotorControllerGroup rightMotorsGroup = new MotorControllerGroup(rightMotor1, rightMotor2);
   private final MotorControllerGroup leftMotorsGroup = new MotorControllerGroup(leftMotor1, leftMotor2);

   //driveTrain
   DifferentialDrive driveTrain = new DifferentialDrive(leftMotorsGroup,rightMotorsGroup);

   //joystick
   Joystick stick = new Joystick(0);

   //topAlma
  // private final Talon topAlma1 = new Talon(8);
   private final Talon topAlma2 = new Talon(9);
   private final Spark topAlmaust1 =new Spark(4);
   private final Spark topAlmaust2 =new Spark(5);
   private final Spark topTasi =new Spark(6);
   private final Spark shooter =new Spark(7);
   private boolean shoot = false;
   private Timer timer2 = new Timer();
   
   

   //private final MotorControllerGroup topAlmaGroup = new MotorControllerGroup(topAlma1, topAlma2);


   // DoubleSolenoid corresponds to a double solenoid.
  private final DoubleSolenoid m_doubleSolenoid =
  new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);

  //compresör ekleme
  Compressor pcmCompressor = new Compressor(0,PneumaticsModuleType.CTREPCM);//kompressor sinifi tanimlama
      


  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();
    


  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
  
    timer = new Timer();
    timer.reset();
    timer.start();
    while (timer.get() < 2){
      shooter.set(-1);
    }
    timer.reset();
    timer.start();
    while (timer.get() < 5) {
      shooter.set(-1);
      topTasi.set(-0.6);
      topAlmaust1.set(-1);
      topAlmaust2.set(-2);
    }  

    shooter.set(0);
    topTasi.set(0);
    topAlmaust1.set(0);
    topAlmaust2.set(0);


    timer.reset();
    timer.start();
    while (timer.get() < 2) {
      leftMotor1.set(0.5);
      leftMotor2.set(0.5);
      rightMotor1.set(-0.5);
      rightMotor2.set(-0.5);
    }
      leftMotor1.set(0);
      leftMotor2.set(0);
      rightMotor1.set(0);
      rightMotor2.set(0);
      timer.stop();
  }

  @Override
  public void autonomousPeriodic() {
 
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    z =stick.getZ();
    z2 = (z * z) / 1024;
    y= stick.getY();
    y2 = (y * y) / 1024;
    driveTrain.arcadeDrive(z2 , y2);

    if(stick.getRawButtonPressed(12)){
      pcmCompressor.disable();
      
    }

    if(stick.getRawButtonPressed(11)){
      pcmCompressor.enableDigital();
      
    }

    if(stick.getRawButtonPressed(1))
    {
      //yukarı götür
      topAlmaust1.set(-1);
      topAlmaust2.set(-1);

    }
    if(stick.getRawButtonReleased(1))
    {
    
    //topAlma1.set(0);
    topAlmaust1.set(0);
    topAlmaust2.set(0);
    topTasi.set(0);

    }
    if(stick.getRawButtonPressed(8)) {
    timer2.reset();
    timer2.start();
    while(timer2.get() < 3) {
      shooter.set(-0.2);
    }
    timer2.reset();

    while(timer2.get() < 2 ){
      shooter.set(-0.2);
      topAlmaust1.set(-0.2);
      topAlmaust2.set(-0.2);
    }
    
    topAlmaust1.set(0);
    topAlmaust2.set(0);
    shooter.set(0);

    timer2.stop();
    }
    if(stick.getRawButtonReleased(8)) {
        shooter.set(0);
        topAlmaust1.set(0);
        topAlmaust2.set(0);
    }
    
    if(stick.getRawButtonPressed(2))
    {
      //intake
      // topAlma1.set(-1);
      topAlma2.set(-1);
      topTasi.set(0.6);
    }

    if(stick.getRawButtonReleased(2))
    {
      
      
    topAlma2.set(0);
   // topAlma1.set(0);
   topAlmaust1.set(0);
   topAlmaust2.set(0);
   topTasi.set(0);

    }
    if(stick.getRawButtonPressed(6))
    {
      //shooter çalıştırılıyor
      shooter.set(-1);
      shoot = !shoot;
    }
    if(stick.getRawButtonReleased(6))
    {
      //shoot = false;
      shooter.set(0);
    //shooterı durduruyoruz
    }

    if (stick.getRawButtonPressed(9)) {
      shooter.set(-0.8);

    }
    if (stick.getRawButtonReleased(9)) {
      shooter.set(0);

    }
    if (stick.getRawButtonPressed(7)) {
      shooter.set(-0.9);

    }
    if (stick.getRawButtonReleased(7)) {
      shooter.set(0);

    }
    
    if (shoot == true) {
      shooter.set(-1);
    }
    if (shoot == false) {
      shooter.set(0);
    }

    if (stick.getRawButtonPressed(5)) {
      topAlmaust1.set(1);
      topAlmaust2.set(1);
      topTasi.set(0.6);
      shooter.set(1);
      topAlma2.set(1);
    }
    if(stick.getRawButtonReleased(5)) {
      topAlmaust1.set(0);
      topAlmaust2.set(0);
      topTasi.set(0);
      shooter.set(0);
      topAlma2.set(0); 
    } 
    //if(stick.getRawButtonPressed(5))
    //{
     // driveTrain.setMaxOutput(-1);
     //robotun yönünü ters çeviriyor
    //}
    //else
    //{
     // driveTrain.setMaxOutput(1);
     //robotun hızını yarıya düşürüyor
    //}

    //doubleSelenoid Forward, Reverse 
    if (stick.getRawButton(3)) {
      m_doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    } else if (stick.getRawButton(4)) {
      m_doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    //}
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}