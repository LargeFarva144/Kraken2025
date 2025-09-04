package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;
import java.util.function.DoubleSupplier;

public class ArmCommands {
  private ArmCommands() {}

  public static Command armToHome(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInches), extend)
            .until(() -> extend.atSetpoint())
            .andThen(
                Commands.run(() -> pivot.pivotToAngle(ArmConstants.Home.homePivotDegrees), pivot)));
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
                    () -> extend.extendToLength(ArmConstants.Home.homeExtendInches), extend))
            .until(() -> pivot.atSetpoint())
            .andThen(
                Commands.run(
                    () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()),
                    extend))); // might need run().until()
  }

  public static Command autoArmToSetpoint(
      Pivot pivot,
      Extend extend,
      DoubleSupplier pivotSetpointSupplier,
      DoubleSupplier extendSetpointSupplier) {
    return Commands.sequence(
        Commands.parallel(
                Commands.run(() -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble()), pivot),
                Commands.run(
                    () -> extend.extendToLength(ArmConstants.Home.homeExtendInches), extend))
            .until(() -> pivot.atSetpoint())
            .andThen(
                Commands.run(
                        () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()), extend)
                    .until(() -> extend.atSetpoint()))); // might need run().until()
  }

  public static Command pickupCoral(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> pivot.pivotToAngle(ArmConstants.Coral.coralPivotDegrees))
            .until(() -> pivot.atSetpoint()),
        Commands.run(() -> extend.extendToLength(ArmConstants.Coral.coralExtendInches))
            .until(() -> extend.atSetpoint()),
        Commands.waitSeconds(1.5),
        Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInches))
            .until(() -> extend.atSetpoint()));
  }

  public static Command armRemoveAlgae(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> pivot.pivotToAngle(ArmConstants.Algae.algaePrepRemovePivotDegrees))
            .until(() -> pivot.atSetpoint()),
        Commands.run(() -> extend.extendToLength(ArmConstants.Algae.algaePrepRemoveExtendInches))
            .until(() -> extend.atSetpoint()),
        Commands.waitSeconds(1.5),
        Commands.run(() -> pivot.pivotToAngle(ArmConstants.Algae.algaeRemovePivotDegrees))
            .until(() -> pivot.atSetpoint()),
        Commands.run(() -> extend.extendToLength(ArmConstants.Algae.algaeRemoveExtendInches))
            .until(() -> extend.atSetpoint()));
  }

  public static Command armFloorPickUpCoral(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> pivot.pivotToAngle(ArmConstants.groundPickUp.groundPickUpPivotDegrees))
            .until(() -> pivot.atSetpoint()),
        Commands.run(
                () -> extend.extendToLength(ArmConstants.groundPickUp.groundPickUpExtendInches))
            .until(() -> extend.atSetpoint()));
  }

  public static Command armReefPickUpAlgae(Pivot pivot, Extend extend) {
    return Commands.sequence(
        Commands.run(() -> pivot.pivotToAngle(ArmConstants.Algae.algaePickUpReefPivotDegrees))
            .until(() -> pivot.atSetpoint()),
        Commands.run(() -> extend.extendToLength(ArmConstants.Algae.algaePickUpReefExtendInches))
            .until(() -> extend.atSetpoint()));
  }

  //   public static Command armScoreProcessor(p)
  // {}
  public static Command joystickPivot(Pivot pivot, DoubleSupplier ySupplier) {
    double voltLimit = 2.5;
    return Commands.run(() -> pivot.runVolts(ySupplier.getAsDouble() * voltLimit), pivot);
  }

  public static Command joystickExtend(Extend extend, DoubleSupplier ySupplier) {
    double voltLimit = 8;
    return Commands.run(() -> extend.runVolts(ySupplier.getAsDouble() * voltLimit), extend);
  }
}
