package frc.robot.subsystems.climb;

public class ClimbConstants {

  public static final int climbTalonId = 27;
  public static final boolean climbInvert = true; // confirm
  public static final boolean climbNeutralModeBrake = false;
  public static final double climbPeakVoltage = 12;
  public static final double climbGearRatio = 225;

  // Command Stop Points
  public static final double climbPrepAngleDegrees = 140; // update
  public static final double climbHangAngleDegrees = 260; // update

  // Soft Limits
  public static final boolean climbForwardSoftLimitEnabled = false;
  public static final double climbForwardSoftLimitDegrees = 150; // update
  public static final boolean climbReverseSoftLimitEnabled = false;
  public static final double climbReverseSoftLimitDegrees = -10; // update
}
