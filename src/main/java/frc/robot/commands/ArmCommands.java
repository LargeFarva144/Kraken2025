package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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

  //   public static Command armToHomeAlgae(Pivot pivot, Extend extend) {
  //     return Commands.sequence(
  //         Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInchesAlgae),
  // extend)
  //             .until(() -> extend.atSetpoint())
  //             .andThen(
  //                 Commands.run(
  //                     () -> pivot.pivotToAngle(ArmConstants.Home.homePivotDegreesCoral),
  // pivot)));
  //   }

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
            .until(() -> pivot.atSetpoint())
            .andThen(
                Commands.run(
                        () -> extend.extendToLength(extendSetpointSupplier.getAsDouble()), extend)
                    .until(() -> extend.atSetpoint())
                    .andThen(
                        Commands.run(
                            () -> pivot.pivotToAngle(pivotScoreSetpointSupplier.getAsDouble()),
                            pivot)))
            .until(() -> pivot.atSetpoint())); // might need run().until()
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

  //   public static Command armAlgaeLiftTop(Pivot pivot, Extend extend) {
  //     return Commands.sequence(
  //         Commands.runOnce(() -> extend.extendToLength(ArmConstants.Algae.algaeExtendBarge),
  // extend)
  //             .until(() -> extend.atSetpoint())
  //             .andThen(
  //                 Commands.runOnce(
  //                     () -> pivot.pivotToAngle(ArmConstants.Algae.algaePivotBarge), pivot)));
  //   }

  //   public static Command armAlgaeLiftBottom(Pivot pivot) {
  //     return
  //     // Commands.run(() -> extend.extendToLength(ArmConstants.Algae.algaeExtendBarge), extend)
  //     //     .until(() -> extend.atSetpoint())
  //     //     .andThen(
  //     Commands.runOnce(() -> pivot.pivotToAngle(ArmConstants.Algae.algaePivotBarge), pivot);
  //   }

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
                                pivot)
                            .andThen(
                                Commands.run(
                                    () ->
                                        extend.extendToLength(
                                            extendScoreSetpointSupplier.getAsDouble()),
                                    extend))))); //
  }

  //   public static Command pickupAlgae(Pivot pivot, Extend extend) {
  //     return Commands.sequence(
  //         Commands.run(() -> pivot.pivotToAngle(ArmConstants.Algae.algaePivotPickUpGround))
  //             .until(() -> pivot.atSetpoint()),
  //         Commands.run(() -> extend.extendToLength(ArmConstants.Algae.algaeExtendPickUpGround))
  //             .until(() -> extend.atSetpoint()),
  //         Commands.waitSeconds(0.5),
  //         Commands.run(() -> extend.extendToLength(ArmConstants.Home.homeExtendInchesCoral))
  //             .until(() -> extend.atSetpoint()));
  //   }

  public static Command joystickPivot(Pivot pivot, DoubleSupplier ySupplier) {
    double voltLimit = 2.5;
    return Commands.run(() -> pivot.runVolts(ySupplier.getAsDouble() * voltLimit), pivot);
    // return Commands.run(() -> pivot.runVolts(ySupplier.getAsDouble() * voltLimit
    // +(Math.cos(Unit.degreesToRadians(getAngle.)))), pivot);
  }

  public static Command joystickExtend(Extend extend, DoubleSupplier ySupplier) {
    double voltLimit = 8;
    return Commands.run(() -> extend.runVolts(ySupplier.getAsDouble() * voltLimit), extend);
  }
}
