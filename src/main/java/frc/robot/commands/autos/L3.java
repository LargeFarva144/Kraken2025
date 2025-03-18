package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;

public class L3 extends SequentialCommandGroup {

  public L3(Pivot pivot, Extend extend) {

    addCommands(
        new InstantCommand(
            () ->
                ArmCommands.armToSetpoint(
                    pivot,
                    extend,
                    () -> ArmConstants.Prep.L3PivotDegrees,
                    () -> ArmConstants.Prep.L3ExtendInches)));
  }
}
