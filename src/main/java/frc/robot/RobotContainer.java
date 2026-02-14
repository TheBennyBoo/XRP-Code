package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutonomousDistance;
import frc.robot.commands.AutonomousTime;
import frc.robot.commands.MoveArmDown;
import frc.robot.commands.MoveArmUp;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.xrp.XRPOnBoardIO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // Creates the drivetrain subsystem (controls the wheels)
  private final Drivetrain m_drivetrain = new Drivetrain();
  // Creates the onboard IO subsystem (access to the physical button on the XRP)
  private final XRPOnBoardIO m_onboardIO = new XRPOnBoardIO();
  // Creates the arm subsystem (controls both servos)
  private final Arm m_arm = new Arm();

  // The joystick/gamepad plugged into port 0 on the driver station
  private final Joystick m_controller = new Joystick(0);

  // Dropdown menu on SmartDashboard to pick which auto routine to run
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  public RobotContainer() {
    // Set up all button bindings when the robot starts
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // Arcade drive runs by default whenever no other command is using the drivetrain
    // Left stick = forward/back, Right stick = turning
    m_drivetrain.setDefaultCommand(getArcadeDriveCommand());

    // The physical button on top of the XRP board
    // Prints a message to the console when pressed or released (useful for testing)
    Trigger userButton = new Trigger(m_onboardIO::getUserButtonPressed);
    userButton
        .onTrue(new PrintCommand("USER Button Pressed"))
        .onFalse(new PrintCommand("USER Button Released"));

    // Button 1 - Hold to move the UPPER arm servo up 1 degree per cycle (~50 deg/sec)
    // Releases automatically when button is let go
    JoystickButton joystickAButton = new JoystickButton(m_controller, 2);
    joystickAButton.whileTrue(new MoveArmUp(m_arm));

    // Button 2 - Hold to move the UPPER arm servo down 1 degree per cycle (~50 deg/sec)
    // Releases automatically when button is let go
    JoystickButton joystickBButton = new JoystickButton(m_controller, 1);
    joystickBButton.whileTrue(new MoveArmDown(m_arm));

    // Button 3 - Snaps the LOWER arm servo to 120 degrees while held
    // Returns to 45 degrees when released
    JoystickButton joystickCButton = new JoystickButton(m_controller, 3);
    joystickCButton
        .onTrue(new InstantCommand(() -> m_arm.setAngle(120.0), m_arm))
        .onFalse(new InstantCommand(() -> m_arm.setAngle(45.0), m_arm));

    // Button 4 - Snaps the LOWER arm servo to 95 degrees while held
    // Returns to 45 degrees when released
    JoystickButton joystickEButton = new JoystickButton(m_controller, 4);
    joystickEButton
        .onTrue(new InstantCommand(() -> m_arm.setAngle(95.0), m_arm))
        .onFalse(new InstantCommand(() -> m_arm.setAngle(45.0), m_arm));

    // Button 5 - Snaps the LOWER arm servo to 95 degrees while held
    // Returns to 45.5 degrees when released (slightly different from button 4's release angle)
    
    // Adds both auto routines to the SmartDashboard dropdown
    // Default is distance-based auto (uses the arm too)
    // Alternative is time-based auto (drivetrain only)
    m_chooser.setDefaultOption("Auto Routine Distance", new AutonomousDistance(m_drivetrain, m_arm));
    m_chooser.addOption("Auto Routine Time", new AutonomousTime(m_drivetrain));
    SmartDashboard.putData(m_chooser);
  }

  // Returns whichever auto routine was selected on SmartDashboard
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  // Arcade drive command - left stick controls speed, right stick controls turning
  // The negative signs flip the axis so pushing forward actually goes forward
  public Command getArcadeDriveCommand() {
    return new ArcadeDrive(
        m_drivetrain, () -> -m_controller.getRawAxis(1), () -> -m_controller.getRawAxis(2));
  }
}