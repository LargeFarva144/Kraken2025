package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;

public class Home extends SequentialCommandGroup {

  public Home(Pivot pivot, Extend extend) {

    addCommands(ArmCommands.armToHomeAuto(pivot, extend));
  }
}
