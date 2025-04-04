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
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.ArmCommands;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.autos.*;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.climb.ClimbConstants;
import frc.robot.subsystems.climb.ClimbTalonFX;
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
import frc.robot.subsystems.vacuum.Vacuum;
import frc.robot.subsystems.vacuum.VacuumIOTalonSRX;
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
  private final Vacuum vacuum;
  private final Hopper hopper;
  private final Climb climb;

  // Controller
  private final CommandXboxController controllerDriver = new CommandXboxController(0);
  private final CommandXboxController controllerOperator = new CommandXboxController(1);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  // Named commands
  private static Command L2;
  private static Command L3;
  private static Command L4;
  private static Command PickUpCoral;
  public static Command Home;

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
                new VisionIOPhotonVision(camera0Name, robotToCamera1),
                new VisionIOPhotonVision(camera1Name, robotToCamera0),
                new VisionIOPhotonVision(camera2Name, robotToCamera2));
        // new VisionIOPhotonVision(camera3Name, robotToCamera3));
        pivot = new Pivot(new PivotIOTalonFX());
        extend =
            new Extend(new ExtendIOTalonFX(), pivot); // pivot must be first, is passed into extend
        hopper = new Hopper(new HopperIOCANrange());
        vacuum = new Vacuum(new VacuumIOTalonSRX());
        climb = new Climb(new ClimbTalonFX());
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
                new VisionIOPhotonVisionSim(camera2Name, robotToCamera2, drive::getPose));
        // new VisionIOPhotonVisionSim(camera3Name, robotToCamera3, drive::getPose));
        pivot = new Pivot(null);
        extend = new Extend(null, pivot);
        hopper = new Hopper(null);
        vacuum = new Vacuum(null);
        climb = new Climb(null);
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
                drive::addVisionMeasurement, new VisionIO() {}, new VisionIO() {}, new VisionIO() {}
                // new VisionIO() {}
                );
        pivot = new Pivot(null);
        extend = new Extend(null, pivot);
        hopper = new Hopper(null);
        vacuum = new Vacuum(null);
        climb = new Climb(null);
        break;
    }

    // Only use the below if arm is bottomed in funnel while deploying,
    // should only be needed if different code is deployed
    // extend.resetZero();

    // Register named commands
    L2 = new L2(pivot, extend);
    L3 = new L3(pivot, extend);
    L4 = new L4(pivot, extend, vacuum);
    PickUpCoral = new PickUpCoral(pivot, extend, vacuum);
    Home = new Home(pivot, extend);

    NamedCommands.registerCommand("L2", L2);
    NamedCommands.registerCommand("L3", L3);
    NamedCommands.registerCommand("L4", L4);
    NamedCommands.registerCommand("Home", Home);
    NamedCommands.registerCommand("PickUpCoral", PickUpCoral);

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

    pivot.setDefaultCommand(ArmCommands.armToHomeCoral(pivot, extend));

    // Automatic Triggers

    // new Trigger(() -> hopper.hasCoral())
    //     .onTrue(
    //         Commands.parallel(
    //             ArmCommands.pickupCoral(pivot, extend),
    //             Commands.runOnce(() -> vacuum.runVacuum(true))));

    // Driver Controller Bindings

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

    // Climb to prep angle on RB
    // controllerDriver
    //     .y()
    //     .and(() -> climb.setAngle() < ClimbConstants.climbPrepAngleDegrees)
    //     .onTrue(
    //         Commands.run(() -> climb.runVolts(3))
    //             .until(() -> climb.setAngle() >= ClimbConstants.climbPrepAngleDegrees));

    // // Climb to hang on RB
    // controllerDriver
    //     .y()
    //     .and(() -> climb.setAngle() >= ClimbConstants.climbPrepAngleDegrees)
    //     .whileTrue(Commands.run(() -> climb.runVolts(3)))
    //     .onFalse(Commands.runOnce(() -> climb.stop()));

    // Operator Controller Bindings

    // Joystick control of arm requires LT
    controllerOperator
        .leftTrigger(0.1)
        .whileTrue(
            Commands.parallel(
                ArmCommands.joystickPivot(pivot, () -> -controllerOperator.getLeftY()),
                ArmCommands.joystickExtend(extend, () -> -controllerOperator.getRightY())))
        .onFalse(
            Commands.parallel(
                Commands.runOnce(() -> pivot.stop()), Commands.runOnce(() -> extend.stop())))
        .onFalse(ArmCommands.armToHomeCoral(pivot, extend));

    controllerOperator.x().onTrue(Commands.runOnce(() -> vacuum.toggleVacuum()));

    // L2 on A button
    controllerOperator
        .a()
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Prep.L2PivotDegrees,
                () -> ArmConstants.Prep.L2ExtendInches));

    // L3 on B button
    controllerOperator
        .b()
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Prep.L3PivotDegrees,
                () -> ArmConstants.Home.homeExtendInchesCoral));

    // L4 on Y button
    controllerOperator
        .y()
        .whileTrue(
            ArmCommands.armToSetpointLFour(
                pivot,
                extend,
                () -> ArmConstants.Prep.L4PivotDegrees,
                () -> ArmConstants.Prep.L4ExtendInches,
                () -> ArmConstants.Score.L4PivotDegrees));

    // controllerOperator
    //     .povUp()
    //     .whileTrue(
    //         ArmCommands.armToSetpoint(
    //             pivot,
    //             extend,
    //             () -> ArmConstants.Algae.algaePivotBarge,
    //             () -> ArmConstants.Algae.algaeExtendBarge));

    // controllerOperator
    //     .button(0)
    //     .whileTrue(
    //         Commands.parallel(
    //             ArmCommands.pickupAlgae(pivot, extend),
    //             Commands.runOnce(() -> vacuum.runVacuum(true)))).onFalse(
    //                 Commands.parallel(
    //                     Commands.runOnce(() -> pivot.stop()), Commands.runOnce(() ->
    // extend.stop())));

    // Coral pickup on RB
    controllerOperator
        .rightBumper()
        .onTrue(
            Commands.parallel(
                ArmCommands.pickupCoral(pivot, extend),
                Commands.runOnce(() -> vacuum.runVacuum(true))));

    // Drop coral on RT
    controllerOperator.rightTrigger(0.1).onTrue(Commands.runOnce(() -> vacuum.runVacuum(false)));

    // Reset encoder on Start button
    controllerOperator.start().onTrue(Commands.runOnce(() -> extend.resetZero()));
    controllerOperator.start().onTrue(
        Commands.sequence(
            Commands.parallel(
                ArmCommands.pickupCoral(pivot, extend),
                Commands.runOnce(() -> vacuum.runVacuum(true)))
                .until(() -> extend.atSetpoint()),
                Commands.run(() -> extend.)
            Commands.runOnce(() -> extend.resetZero())));

    controllerDriver
        .y()
        .and(() -> climb.setAngle() < ClimbConstants.climbPrepAngleDegrees)
        .onTrue(
            Commands.run(() -> climb.runVolts(4))
                .until(() -> climb.setAngle() >= ClimbConstants.climbPrepAngleDegrees));

    controllerDriver
        .y()
        .and(() -> climb.setAngle() >= ClimbConstants.climbPrepAngleDegrees)
        .whileTrue(Commands.run(() -> climb.runVolts(4)));

    controllerDriver
        .rightBumper()
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Algae.algaeTopPrepPivotDegrees,
                () -> ArmConstants.Algae.algaeTopPrepExtendInches))
        .onFalse(ArmCommands.armToHomeCoral(pivot, extend));

    controllerDriver
        .rightBumper()
        .and(controllerDriver.leftBumper())
        .whileTrue(
            ArmCommands.armToSetpoint(
                pivot,
                extend,
                () -> ArmConstants.Algae.algaeTopPivotDegrees,
                () -> ArmConstants.Algae.algaeTopExtendInches))
        .onFalse(ArmCommands.armToHomeCoral(pivot, extend));

    controllerDriver
        .leftBumper()
        .whileTrue(
            ArmCommands.armRemoveBottomalgae(
                pivot,
                extend,
                () -> ArmConstants.Algae.algaeBottomPrepPivotDegrees,
                () -> ArmConstants.Algae.algaeBottomPrepExtendInches,
                () -> ArmConstants.Algae.algaeBottomPivotDegrees,
                () -> ArmConstants.Algae.algaeBottomExtendInches));

    // controllerDriver
    //     .leftBumper()
    //     .whileTrue(
    //         Commands.parallel(
    //             ArmCommands.armToSetpoint(
    //                 pivot,
    //                 extend,
    //                 () -> ArmConstants.Algae.algaeBottomPrepPivotDegrees,
    //                 () -> ArmConstants.Algae.algaeBottomPrepExtendInches),
    //             Commands.runOnce(() -> vacuum.runVacuum(true))))
    //     .onFalse(
    //         Commands.sequence(
    //             ArmCommands.armAlgaeLiftTop(pivot, extend)
    //                 .andThen(ArmCommands.armToHomeAlgae(pivot, extend))));
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
