package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.vacuum.Vacuum;
import frc.robot.util.ArmConstants;

public class L4 extends SequentialCommandGroup {
  public L4(Pivot pivot, Extend extend, Vacuum vacuum) {
    addCommands(
        ArmCommands.autoArmToSetpoint(
            pivot,
            extend,
            () -> ArmConstants.Prep.L4PivotDegrees,
            () -> ArmConstants.Prep.L4ExtendInches),
        Commands.runOnce(() -> vacuum.runVacuum(false)),
        Commands.waitSeconds(1));
  }
}
