package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.vacuum.Vacuum;

public class PickUpCoral extends ParallelCommandGroup {
  public PickUpCoral(Pivot pivot, Extend extend, Vacuum vacuum) {
    addCommands(
        ArmCommands.pickupCoral(pivot, extend), Commands.runOnce(() -> vacuum.runVacuum(true)));
  }
}
