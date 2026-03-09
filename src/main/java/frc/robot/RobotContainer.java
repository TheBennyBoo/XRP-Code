package frc.robot;

// Import all commands and subsystems
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Subsystems
  private final DriveSubsystem drive = new DriveSubsystem();

  // Controller
  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    // Set drive command
    drive.setDefaultCommand(
      new DriveCommand(
        drive,
        () -> -driverController.getLeftY(),
        () -> -driverController.getLeftX(),
        () -> driverController.getRightX()
      )
    );

    configureButtonBindings();
  }

  private void configureButtonBindings() {

  }

  public Command getAutonomousCommand() {
    // TODO: Add autonomous routines
    return null;
  }
}