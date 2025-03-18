package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;

public class L2 extends SequentialCommandGroup {

  public L2(Pivot pivot, Extend extend) {

    addCommands(
        new InstantCommand(
            () ->
                ArmCommands.armToSetpoint(
                    pivot,
                    extend,
                    () -> ArmConstants.Prep.L2PivotDegrees,
                    () -> ArmConstants.Prep.L2ExtendInches)));
  }
}
