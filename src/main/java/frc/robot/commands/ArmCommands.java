package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;
import java.util.function.DoubleSupplier;

public class ArmCommands {
  private ArmCommands() {}

  public static Command armToHomeCoral(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral), extend)
            .until(() -> extend.atSetpoint())
            .andThen(
                Commands.run(
                    () -> pivot.pivotToAngle(ArmConstants.Home.homePivotDegreesCoral), pivot)));
  }

  public static Command armToHomeAuto(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral), extend)
            .until(() -> extend.atSetpoint())
            .andThen(
                Commands.run(
                    () -> pivot.pivotToAngle(ArmConstants.Home.homePivotDegreesCoral), pivot))
            .until(() -> pivot.atSetpoint()));
  }

  public static Command armToSetpoint(
      Pivot pivot,
      Extend extend,
      DoubleSupplier pivotSetpointSupplier,
      DoubleSupplier extendSetpointSupplier) {
    return Commands.sequence(
        Commands.parallel(
                Commands.run(() -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble()), pivot),
                Commands.run(
                    () -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral), extend))
            .until(() -> pivot.atSetpoint())
            .andThen(
                Commands.run(
                    () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()),
                    extend))); // might need run().until()
  }


  public static Command armToSetpointLFour(
      Pivot pivot,
      Extend extend,
      DoubleSupplier pivotSetpointSupplier,
      DoubleSupplier extendSetpointSupplier,
      DoubleSupplier pivotScoreSetpointSupplier) {

    return new SequentialCommandGroup(
        new ParallelCommandGroup(
            new InstantCommand(
                () -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble()), pivot),
            new InstantCommand(
                () -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral),
                extend)), // keeps the extend at the home length as it pivots
        new WaitUntilCommand(() -> pivot.atSetpoint()),
        new InstantCommand(
            () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()), extend),
        new WaitUntilCommand(() -> extend.atSetpoint()),
        new InstantCommand(
            () -> pivot.pivotToAngle(pivotScoreSetpointSupplier.getAsDouble()), pivot),
        new WaitUntilCommand(100));
  }

  public static Command autoArmToSetpoint(
      Pivot pivot,
      Extend extend,
      DoubleSupplier pivotSetpointSupplier,
      DoubleSupplier extendSetpointSupplier,
      DoubleSupplier pivotScoreSetpointSupplier) {
    return Commands.sequence(
        Commands.parallel(
                Commands.run(() -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble()), pivot),
                Commands.run(
                    () -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral), extend))
            .until(() -> pivot.atSetpoint()),
        new InstantCommand(
            () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()), extend),
        new WaitUntilCommand(() -> extend.atSetpoint()),
        new InstantCommand(
            () -> pivot.pivotToAngle(pivotScoreSetpointSupplier.getAsDouble()), pivot),
        new WaitUntilCommand(() -> pivot.atSetpoint()));
  }

  public static Command pickupCoral(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> pivot.pivotToAngle(ArmConstants.Coral.coralPivotDegrees))
            .until(() -> pivot.atSetpoint()),
        Commands.run(() -> extend.extendToLength(ArmConstants.Coral.coralExtendInches))
            .until(() -> extend.atSetpoint()),
        Commands.waitSeconds(0.5),
        Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral))
            .until(() -> extend.atSetpoint()));
  }

  public static Command armRemoveTopalgae(
      Pivot pivot,
      Extend extend,
      DoubleSupplier pivotSetpointSupplier,
      DoubleSupplier extendSetpointSupplier,
      DoubleSupplier pivotScoreSetpointSupplier) {
    return Commands.sequence(
        Commands.parallel(
                Commands.run(() -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble()), pivot),
                Commands.run(
                    () -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral),
                    extend)) // keeps the extend at the home length as it pivots
            .until(() -> pivot.atSetpoint())
            .andThen(
                Commands.run(
                        () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()), extend)
                    .until(() -> extend.atSetpoint())
                    .andThen(
                        Commands.run(
                            () -> pivot.pivotToAngle(pivotScoreSetpointSupplier.getAsDouble()),
                            pivot)))); //
  }


  public static Command armRemoveBottomalgae(
    Pivot pivot,
    Extend extend,
    DoubleSupplier pivotSetpointSupplier,
    DoubleSupplier extendSetpointSupplier,
    DoubleSupplier pivotScoreSetpointSupplier,
    DoubleSupplier extendScoreSetpointSupplier) {
  return Commands.sequence(
      Commands.parallel(
              Commands.run(() -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble()), pivot),
              Commands.run(
                  () -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral),
                  extend)) // keeps the extend at the home length as it pivots
          .until(() -> pivot.atSetpoint())
          .andThen(
              Commands.run(
                      () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()), extend)
                  .until(() -> extend.atSetpoint())
                  .andThen(
                      Commands.run(
                          () -> pivot.pivotToAngle(pivotScoreSetpointSupplier.getAsDouble()),
                          pivot)).until(() -> pivot.atSetpoint())
                          .andThen(
                            Commands.run(
                                () -> extend.extendToLength(extendScoreSetpointSupplier.getAsDouble()),
                                extend)))); //
}

  public static Command joystickPivot(Pivot pivot, DoubleSupplier ySupplier) {
    double voltLimit = 2.5;
    return Commands.run(() -> pivot.runVolts(ySupplier.getAsDouble() * voltLimit), pivot);
  }

  public static Command joystickExtend(Extend extend, DoubleSupplier ySupplier) {
    double voltLimit = 8;
    return Commands.run(() -> extend.runVolts(ySupplier.getAsDouble() * voltLimit), extend);
  }
}
