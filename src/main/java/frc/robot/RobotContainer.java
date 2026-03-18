package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class RobotContainer {
  private final GyroSubsystem gyro = new GyroSubsystem();
  private final DriveSubsystem drive = new DriveSubsystem(gyro);
  private final CommandXboxController driverController = new CommandXboxController(0);
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  public RobotContainer() {
    drive.setDefaultCommand(
      new DriveCommand(
        drive,
        () -> -driverController.getLeftY(),
        () -> -driverController.getLeftX(),
        () -> driverController.getRightX()
      )
    );

    autoChooser.setDefaultOption("Do Nothing", new InstantCommand(drive::stop, drive));
    autoChooser.addOption("Drive Forward",
      new RunCommand(() -> drive.drive(0.3, 0.0, 0.0, false), drive).withTimeout(2).andThen(drive::stop, drive)
    );
    SmartDashboard.putData("Autonomous Mode", autoChooser);

    configureButtonBindings();
  }

  private void configureButtonBindings() {}

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}