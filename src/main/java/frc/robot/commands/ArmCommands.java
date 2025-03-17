package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;
import java.util.function.DoubleSupplier;

public class ArmCommands {
  private ArmCommands() {}

  public static Command armToHome(
      Pivot pivot,
      Extend extend,
      DoubleSupplier pivotHomeSupplier,
      DoubleSupplier extendHomeSupplier) {
    return Commands.sequence(
        Commands.run(() -> extend.extendToLength(extendHomeSupplier.getAsDouble()), extend)
            .until(() -> extend.atSetpoint())
            .andThen(
                Commands.run(() -> pivot.pivotToAngle(pivotHomeSupplier.getAsDouble()), pivot)));
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

  public static Command joystickPivot(Pivot pivot, DoubleSupplier ySupplier) {
    double voltLimit = 2;
    return Commands.run(() -> pivot.runVolts(ySupplier.getAsDouble() * voltLimit), pivot);
  }

  public static Command joystickExtend(Extend extend, DoubleSupplier ySupplier) {
    double voltLimit = 6;
    return Commands.run(() -> extend.runVolts(ySupplier.getAsDouble() * voltLimit), extend);
  }
}
