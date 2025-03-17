// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import static frc.robot.subsystems.vision.VisionConstants.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.ArmCommands;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.autos.L4;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.extend.ExtendIOTalonFX;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.hopper.HopperIOCANrange;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotIOTalonFX;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
import frc.robot.util.ArmConstants;
import frc.robot.util.PoseConstants;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;
  private final Vision vision;
  private final Pivot pivot;
  private final Extend extend;
  private final Hopper hopper;

  // Controller
  private final CommandXboxController controllerDriver = new CommandXboxController(0);
  private final CommandXboxController controllerOperator = new CommandXboxController(1);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  // Named commands
  private Command L4;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));
        vision =
            new Vision(
                drive::addVisionMeasurement,
                new VisionIOPhotonVision(camera0Name, robotToCamera0),
                new VisionIOPhotonVision(camera1Name, robotToCamera1),
                new VisionIOPhotonVision(camera2Name, robotToCamera2),
                new VisionIOPhotonVision(camera3Name, robotToCamera3));
        pivot = new Pivot(new PivotIOTalonFX());
        extend = new Extend(new ExtendIOTalonFX(), pivot); //pivot must be first, is passed into extend
        hopper = new Hopper(new HopperIOCANrange());
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));
        vision =
            new Vision(
                drive::addVisionMeasurement,
                new VisionIOPhotonVisionSim(camera0Name, robotToCamera0, drive::getPose),
                new VisionIOPhotonVisionSim(camera1Name, robotToCamera1, drive::getPose),
                new VisionIOPhotonVisionSim(camera2Name, robotToCamera2, drive::getPose),
                new VisionIOPhotonVisionSim(camera3Name, robotToCamera3, drive::getPose));
        pivot = new Pivot(null);
        extend = new Extend(null, pivot);
        hopper = new Hopper(null);
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        vision =
            new Vision(
                drive::addVisionMeasurement,
                new VisionIO() {},
                new VisionIO() {},
                new VisionIO() {},
                new VisionIO() {});
        pivot = new Pivot(null);
        extend = new Extend(null, pivot);
        hopper = new Hopper(null);
        break;
    }

    // Register named commands
    L4 = new L4(pivot, extend);

    NamedCommands.registerCommand("L4", L4);

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> -controllerDriver.getLeftY(),
            () -> -controllerDriver.getLeftX(),
            () -> -controllerDriver.getRightX()));

    pivot.setDefaultCommand(
        ArmCommands.armToHome(
            pivot,
            extend,
            () -> ArmConstants.Home.homePivotDegrees,
            () -> ArmConstants.Home.homeExtendInches));

    //Automatic Triggers

    new Trigger(() -> hopper.getObjectDetection())
        .onTrue(ArmCommands.armToSetpoint(pivot, extend, () -> ArmConstants.Coral.coralPivotDegrees, () -> ArmConstants.Coral.coralExtendInches));
    
    //Driver Controller Bindings
    
    // Lock to 0° when A button is held
    controllerDriver
        .a()
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> -controllerDriver.getLeftY(),
                () -> -controllerDriver.getLeftX(),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controllerDriver.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    controllerDriver
        .b()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    controllerDriver
        .leftTrigger(0.1)
        .and(() -> PoseConstants.getTargetPose(vision.getForwardTargetID(), true) != null)
        .onTrue(
            DriveCommands.driveToPose(
                drive, () -> PoseConstants.getTargetPose(vision.getForwardTargetID(), true)));

    controllerDriver
        .rightTrigger(0.1)
        .and(() -> PoseConstants.getTargetPose(vision.getForwardTargetID(), false) != null)
        .onTrue(
            DriveCommands.driveToPose(
                drive, () -> PoseConstants.getTargetPose(vision.getForwardTargetID(), false)));

    // Operator Controller Bindings

    new Trigger(() -> Math.abs(controllerOperator.getLeftY()) > 0.05)
        .whileTrue(ArmCommands.joystickPivot(pivot, () -> -controllerOperator.getLeftY()));
    new Trigger(() -> Math.abs(controllerOperator.getRightY()) > 0.05)
        .whileTrue(ArmCommands.joystickPivot(pivot, () -> -controllerOperator.getRightY()));

    controllerOperator
        .a()
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Prep.L2PivotDegrees,
                () -> ArmConstants.Prep.L2ExtendInches));
    controllerOperator
        .leftBumper()
        .and(controllerOperator.a())
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Score.L2PivotDegrees,
                () -> ArmConstants.Score.L2ExtendInches));

    controllerOperator
        .b()
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Prep.L3PivotDegrees,
                () -> ArmConstants.Prep.L3ExtendInches));
    controllerOperator
        .leftBumper()
        .and(controllerOperator.b())
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Score.L3PivotDegrees,
                () -> ArmConstants.Score.L3ExtendInches));

    controllerOperator
        .y()
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Prep.L4PivotDegrees,
                () -> ArmConstants.Prep.L4ExtendInches));
    controllerOperator
        .leftBumper()
        .and(controllerOperator.y())
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Score.L4PivotDegrees,
                () -> ArmConstants.Score.L4ExtendInches));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
