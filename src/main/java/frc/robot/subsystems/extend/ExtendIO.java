// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.extend;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.AutoLog;

/** Add your docs here. */
public interface ExtendIO {

  @AutoLog
  public static class ExtendIOInputs {

    public boolean connected = false;
    public double motorPositionRotations = 0.0;
    public double encoderPositionRotations = 0.0;
    public double positionInches = 0.0;
    public double velocityRotationsPerSecond = 0.0;
    public double positionRotations = 0.0;
    public double appliedVoltage = 0.0;
    public double supplyCurrentAmps = 0.0;
    public double torqueCurrentAmps = 0.0;
    public double tempCelsius = 0.0;
  }

  public default void setBrakeMode(boolean brakeMode) {}

  /**
   * @param inch extends to distance in inches from start
   */
  public default void extendToLength(double lengthInches) {}

  public default void runVolts(double volts) {}

  public default void stop() {}

  public default Rotation2d getRotation() {
    return new Rotation2d();
  }

  public default void updateExtensionLimit(double extensionLimitInches) {}

  public default void updateInputs(ExtendIOInputs inputs) {}
}
