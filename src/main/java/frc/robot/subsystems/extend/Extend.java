// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.extend;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class Extend extends SubsystemBase {
  private ExtendIO io;
  private ExtendIOInputsAutoLogged inputs = new ExtendIOInputsAutoLogged();
  private double setpointLengthInches;

  public Extend(ExtendIO io) {
    this.io = io;
  }

  /** updates Extend values periodically */
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Extend", inputs);
  }

  public void runVolts(double volts) {}

  public void extendToLength(double lengthInches) {
    io.extendToLength(lengthInches);
    this.setpointLengthInches = lengthInches;
    Logger.recordOutput("Extend/setpoint", lengthInches);
  }

  public Rotation2d getRotation() {
    return io.getRotation();
  }

  public boolean atSetpoint() {
    Debouncer setpointDebouncer = new Debouncer(0.5);
    double closedLoopError = (io.getRotation().getRotations() * ExtendConstants.feedCircumferenceInches) - setpointLengthInches;
    boolean atSetpoint = setpointDebouncer.calculate(Math.abs(closedLoopError) < 1);
    Logger.recordOutput("Extend/atSetPoint", atSetpoint);
    Logger.recordOutput("Extend/closedLoopError", closedLoopError);
    return atSetpoint;
  }
}
