package frc.robot.subsystems.extend;

public class ExtendConstants {
  public static final int extendTalonId = 20;
  public static final int extendCANCoderId = 22;
  public static final boolean extendInvert = false;
  public static final boolean extendNeutralModeBrake = false;
  public static final double extendPeakVoltage = 12;
  public static final double extendGearRatio = 18;
  public static final double feedCircumferenceInches = 2 * Math.PI;
  public static final double extendOffsetInches = 26;
  public static final double safePivotLengthInches = 18;

  // Soft Limit Parameters
  public static final boolean extendForwardSoftLimitEnabled = false;
  public static final boolean extendReverseSoftLimitEnabled = true;
  public static final double extendReverseSoftLimitInches = 18;
  public static final double defaultExtensionLimitInches = 24;
  public static final double fwdEnvelopeInches = 18 + 7;
  public static final double aftEnvelopeInches = 18 + 22;
  public static final double maxExtensionInches = 62;
  public static final double funnelAngleDegrees = 60;
  public static final double funnelToPivotInches = 25;

  // PID
  public static double kP = 15;
  public static double kI = 0;
  public static double kD = 0;
  public static double kG = 0;
  public static double kS = 0;
  public static double kV = 0;
  public static double kA = 0;

  // Motion Magic
  public static double motionMagicCruiseVelocity = 20;
  public static double motionMagicAcceleration = 20;
  public static double motionMagicJerk = 100;
}
