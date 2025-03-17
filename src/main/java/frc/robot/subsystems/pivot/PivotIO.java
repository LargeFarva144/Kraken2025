// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.pivot;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.AutoLog;

/** Add your docs here. */
public interface PivotIO {
  @AutoLog // Auto logs inputs
  public static class PivotIOInputs {

    public boolean connected = false;
    public double motorPositionRotations = 0.0;
    public double encoderPositionRotations = 0.0;
    public double positionAngleDegrees = 0.0;
    public double velocityRotationsPerSecond = 0.0;
    public double appliedVoltage = 0.0;
    public double supplyCurrentAmps = 0.0;
    public double torqueCurrentAmps = 0.0;
    public double tempCelsius = 0.0;
  }

  public default void setBrakeMode(boolean brakeMode) {}

  public default void runVolts(double volts) {}

  public default void pivotToAngle(double angleDegrees) {}

  public default void stop() {}

  /**
   * gives current angle of actual arm
   *
   * @return rotation2d
   */
  public default Rotation2d getRotation() {
    return new Rotation2d();
  }

  public default void updateInputs(PivotIOInputs inputs) {} // Updates inputs
}
