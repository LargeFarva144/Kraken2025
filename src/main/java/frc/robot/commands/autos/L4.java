package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;

public class L4 extends SequentialCommandGroup {
  public L4(Pivot pivot, Extend extend) {

    System.out.println("*** Running L4 ***");

    addCommands(
        Commands.run(
            () ->
                ArmCommands.armToSetpoint(
                    pivot,
                    extend,
                    () -> ArmConstants.Prep.L4PivotDegrees,
                    () -> ArmConstants.Prep.L4ExtendInches)));
  }
}
