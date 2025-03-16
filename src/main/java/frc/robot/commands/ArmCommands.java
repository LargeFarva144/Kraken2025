package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;

public class ArmCommands {
  private ArmCommands() {}

  public static Command armToHome(Pivot pivot, Extend extend, DoubleSupplier pivotHomeSupplier, DoubleSupplier extendHomeSupplier) {
    return Commands.sequence(Commands.run(() -> extend.extendToLength(extendHomeSupplier.getAsDouble())).until(() -> extend.atSetpoint()).andThen(Commands.run(() -> pivot.pivotToAngle(pivotHomeSupplier.getAsDouble()))));
  }
  
  public static Command armToSetpoint(Pivot pivot, Extend extend, DoubleSupplier pivotSetpointSupplier, DoubleSupplier extendSetpointSupplier) {
    return Commands.sequence(Commands.run(() -> pivot.pivotToAngle(pivotSetpointSupplier.getAsDouble())).until(() -> pivot.atSetpoint()).andThen(Commands.run(() -> extend.extendToLength(extendSetpointSupplier.getAsDouble()))));
  }

}
