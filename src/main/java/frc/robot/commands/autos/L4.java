// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmCommands;
import frc.robot.subsystems.extend.Extend;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.ArmConstants;

public class L4 extends SequentialCommandGroup {

  public L4(Pivot pivot, Extend extend) {

    addCommands(
        new InstantCommand(
            () ->
                ArmCommands.armToSetpoint(
                    pivot,
                    extend,
                    () -> ArmConstants.Prep.L4PivotDegrees,
                    () -> ArmConstants.Prep.L4ExtendInches)),
        new InstantCommand(
            () ->
                ArmCommands.armToSetpoint(
                    pivot,
                    extend,
                    () -> ArmConstants.Score.L4PivotDegrees,
                    () -> ArmConstants.Score.L4ExtendInches)));
  }
}
