// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.extend;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.pivot.Pivot;
import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class Extend extends SubsystemBase {
  private ExtendIO io;
  private Pivot pivot;
  private ExtendIOInputsAutoLogged inputs = new ExtendIOInputsAutoLogged();
  private double setpointLengthInches;

  public Extend(ExtendIO io, Pivot pivot) {
    this.io = io;
    this.pivot = pivot;
  }

  /** updates Extend values periodically */
  public void periodic() {
    updateExtensionLimit(calculateExtensionLimit((pivot.getRotation())));
    io.updateInputs(inputs);
    Logger.processInputs("Extend", inputs);
  }

  public void runVolts(double volts) {
    io.runVolts(volts);
  }

  public void extendToLength(double lengthInches) {
    io.extendToLength(lengthInches);
    this.setpointLengthInches = lengthInches;
    Logger.recordOutput("Extend/setpoint", lengthInches);
  }

  public void stop() {
    io.stop();
  }

  public Rotation2d getRotation() {
    return io.getRotation();
  }

  public double calculateExtensionLimit(Rotation2d pivotAngleRotation) {
    double pivotAngleRadians = pivotAngleRotation.getRadians();
    if (pivotAngleRadians > Units.degreesToRadians(-81)
        && pivotAngleRadians < Units.degreesToRadians(0)) {
      return ExtendConstants.funnelToPivotInches
          / Math.cos(
              pivotAngleRadians + Units.degreesToRadians(ExtendConstants.funnelAngleDegrees));
    } else if (pivotAngleRadians >= Units.degreesToRadians(0)
        && pivotAngleRadians < Units.degreesToRadians(90)) {
      return Math.min(
          ExtendConstants.aftEnvelopeInches / Math.cos(pivotAngleRadians),
          ExtendConstants.maxExtensionInches);
    } else if (pivotAngleRadians >= Units.degreesToRadians(90)
        && pivotAngleRadians < Units.degreesToRadians(225)) {
      return Math.min(
          ExtendConstants.fwdEnvelopeInches / -Math.cos(pivotAngleRadians),
          ExtendConstants.maxExtensionInches);
    } else {
      return ExtendConstants.defaultExtensionLimitInches;
    }
  }

  public void updateExtensionLimit(double extensionLimitInches) {
    Logger.recordOutput("Extend/extensionLimitInches", extensionLimitInches);
  }

  public void resetZero() {
    io.resetZero();
  }

  public boolean atSetpoint() {
    // Debouncer setpointDebouncer = new Debouncer(0.5);
    double closedLoopError =
        (io.getRotation().getRotations() * ExtendConstants.feedCircumferenceInches)
            - setpointLengthInches;
    // boolean atSetpoint = setpointDebouncer.calculate(Math.abs(closedLoopError) < 1);
    boolean atSetpoint = Math.abs(closedLoopError) < 1;
    Logger.recordOutput("Extend/atSetPoint", atSetpoint);
    Logger.recordOutput("Extend/closedLoopError", closedLoopError);
    return atSetpoint;
  }
}
