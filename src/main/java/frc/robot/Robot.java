package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command autonomousCommand;
  private RobotContainer robotContainer;

  // Called when the robot is started
  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
  }

  // Called periodically while the robot is running
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  // Called at the start of autonomous mode
  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  // Called periodically in autonomous mode
  @Override
  public void autonomousPeriodic() {}

  // Called at the start of teleop mode
  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  // Called periodically in teleop mode
  @Override
  public void teleopPeriodic() {}
}
