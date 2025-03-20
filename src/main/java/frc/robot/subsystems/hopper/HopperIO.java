package frc.robot.subsystems.hopper;

import org.littletonrobotics.junction.AutoLog;

public interface HopperIO {

  @AutoLog
  public static class HopperIOInputs {

    public boolean connected = false;
    public boolean objectDetected = false;
    public double distanceInches = 0.0;
    public double signalStrength = 0.0;
  }

  // public default boolean hasCoral() {
  //   return false;
  // }

  public default void updateInputs(HopperIOInputs inputs) {}
}
