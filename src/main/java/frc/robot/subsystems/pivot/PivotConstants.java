package frc.robot.subsystems.pivot;

public class PivotConstants {
  public static final int pivotTalonId = 21;
  public static final int pivotCANCoderId = 23;
  public static final boolean pivotInvert = false; // confirm and match cancoder
  public static final boolean pivotNeutralModeBrake = false;
  public static final double pivotPeakVoltage = 12;
  public static final double pivotGearRatio = 60; // confirm

  // Soft Limits
  public static final boolean pivotForwardSoftLimitEnabled = true;
  public static final double pivotForwardSoftLimitDegrees = 225;
  public static final boolean pivotReverseSoftLimitEnabled = true;
  public static final double pivotReverseSoftLimitDegrees = -81;

  // PID

  public static double kP = 80;
  public static double kI = 0.00001;
  public static double kD = 0.5;
  public static double kG = 0;
  public static double kS = 0.28;
  public static double kV = 0.13;
  public static double kA = 0.01;

  public static double motionMagicCruiseVelocity = 1.5;
  public static double motionMagicAcceleration = 2;
  public static double motionMagicJerk = 100;
}
