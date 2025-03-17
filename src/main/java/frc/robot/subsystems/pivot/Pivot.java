// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pivot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class Pivot extends SubsystemBase {
  private PivotIO io;
  private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
  private double setpointAngleDegrees;

  public Pivot(PivotIO io) {
    this.io = io;
  }

  /** updates arm values periodically */
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Pivot", inputs);
  }

  public void setBrakeMode(boolean brakeMode) {
    io.setBrakeMode(brakeMode);
  }

  public void runVolts(double volts) {
    io.runVolts(volts);
  }

  public void pivotToAngle(double angleDegrees) {
    io.pivotToAngle(angleDegrees);
    this.setpointAngleDegrees = angleDegrees;
    Logger.recordOutput("Pivot/setpoint", angleDegrees);
  }

  public void stop() {
    io.stop();
  }

  public Rotation2d getRotation() {
    return io.getRotation();
  }

  public boolean atSetpoint() {
    // Debouncer setpointDebouncer = new Debouncer(0.5);
    double closedLoopErrorDegrees = io.getRotation().getDegrees() - setpointAngleDegrees;
    // boolean atSetpoint = setpointDebouncer.calculate(Math.abs(closedLoopError) < 1);
    boolean atSetpoint = Math.abs(closedLoopErrorDegrees) < 1;
    Logger.recordOutput("Pivot/atSetPoint", atSetpoint);
    Logger.recordOutput("Pivot/closedLoopError", closedLoopErrorDegrees);
    return atSetpoint;
  }
}
