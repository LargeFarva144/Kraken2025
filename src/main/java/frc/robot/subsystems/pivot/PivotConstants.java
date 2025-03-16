package frc.robot.subsystems.pivot;

public class PivotConstants {
  public static final int pivotTalonId = 21;
  public static final int pivotCANCoderId = 23;
  public static final boolean pivotInvert = false; // confirm and match cancoder
  public static final boolean pivotNeutralModeBrake = false;
  public static final double pivotPeakVoltage = 12;
  public static final double pivotGearRatio = 60; // confirm

  // Soft Limits
  public static final boolean pivotForwardSoftLimitEnabled = false;
  public static final double pivotForwardSoftLimit = 0; // update
  public static final boolean pivotReverseSoftLimitEnabled = false;
  public static final double pivotReverseSoftLimit = 0; // update

  // PID
  public static double kP = 65;
  public static double kI = 0;
  public static double kD = 0.5;
  public static double kG = 0;
  public static double kS = 0.25;
  public static double kV = 0.12;
  public static double kA = 0.01;

  public static double motionMagicCruiseVelocity = 0.5;
  public static double motionMagicAcceleration = 1;
  public static double motionMagicJerk = 100;
}
