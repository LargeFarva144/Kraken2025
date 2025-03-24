package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import java.util.Map;

public class PoseConstants {
  private static double flippedX = 337.37;

  private static final Map<Integer, Pose2d> leftPoses =
      Map.ofEntries(
          Map.entry(
              11,
              new Pose2d(
                  new Translation2d(
                      Units.inchesToMeters(441.28),
                      Units.inchesToMeters(110.03)), // X:141.02 + xflipped, Y:110.42
                  // +y goes left from drivers station -y goes right from drivers station
                  // +X goes up from driver station, -X goes down from driver station
                  Rotation2d.fromDegrees(60))),
          Map.entry(
              10,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(454.39), Units.inchesToMeters(164.9)),
                  Rotation2d.fromDegrees(2))), // X:117.00 + xflipped, Y:163.97
          Map.entry(
              9,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(489.88), Units.inchesToMeters(213.45)),
                  Rotation2d.fromDegrees(300))), // X:152.23 + xflipped, Y:213.05
          Map.entry(
              8,
              new Pose2d(
                  new Translation2d(
                      Units.inchesToMeters(549.6 + flippedX), Units.inchesToMeters(206.9)),
                  Rotation2d.fromDegrees(240))), // X:211.48  + xflipped, Y:206.58
          Map.entry(
              7,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(573.8), Units.inchesToMeters(152.02)),
                  Rotation2d.fromDegrees(180))), // X:235.50 + xflipped, Y:152.03
          Map.entry(
              6,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(538.38), Units.inchesToMeters(103.55)),
                  Rotation2d.fromDegrees(120))), // X:204.7 + xflipped, Y:101.08
          Map.entry(
              17,
              new Pose2d(
                  new Translation2d(
                      Units.inchesToMeters(141.02),
                      Units.inchesToMeters(110.42)), // X:141.02, Y:110.42
                  // +y goes left from drivers station -y goes right from drivers station
                  // +X goes up from driver station, -X goes down from driver station
                  Rotation2d.fromDegrees(60))),
          Map.entry(
              18,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(117.00), Units.inchesToMeters(163.97)),
                  Rotation2d.fromDegrees(2))),
          Map.entry(
              19,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(152.23), Units.inchesToMeters(213.05)),
                  Rotation2d.fromDegrees(300))),
          Map.entry(
              20,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(211.48), Units.inchesToMeters(206.58)),
                  Rotation2d.fromDegrees(240))),
          Map.entry(
              21,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(235.50), Units.inchesToMeters(152.03)),
                  Rotation2d.fromDegrees(180))),
          Map.entry(
              22,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(200.27), Units.inchesToMeters(103.95)),
                  Rotation2d.fromDegrees(120))));

  private static final Map<Integer, Pose2d> rightPoses =
      Map.ofEntries(
          Map.entry(
              11,
              new Pose2d(
                  new Translation2d(
                      Units.inchesToMeters(489.88),
                      Units.inchesToMeters(103.55)), // X:152.00 + xflipped, Y:104.00
                  Rotation2d.fromDegrees(60))),
          Map.entry(
              10,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(454.39), Units.inchesToMeters(152.02)),
                  Rotation2d.fromDegrees(2))), // X:117.00 + xflipped, Y:150.03
          Map.entry(
              9,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(478.66), Units.inchesToMeters(206.97)),
                  Rotation2d.fromDegrees(300))), // X:141.02 + xflipped, Y:206.58
          Map.entry(
              8,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(538.38), Units.inchesToMeters(213.45)),
                  Rotation2d.fromDegrees(240))), // X:200.27 + xflipped, Y:213.23
          Map.entry(
              7,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(573.87), Units.inchesToMeters(164.98)),
                  Rotation2d.fromDegrees(180))), // X:235.50 + xflipped, Y:164.97
          Map.entry(
              6,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(549.6), Units.inchesToMeters(110.03)),
                  Rotation2d.fromDegrees(120))), // X:215.97 + xflipped, Y:107.56
          Map.entry(
              17,
              new Pose2d(
                  new Translation2d(
                      Units.inchesToMeters(152.00),
                      Units.inchesToMeters(104.0)), // X:152.23, Y:103.95
                  Rotation2d.fromDegrees(60))),
          Map.entry(
              18,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(117.00), Units.inchesToMeters(150.03)),
                  Rotation2d.fromDegrees(2))),
          Map.entry(
              19,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(141.02), Units.inchesToMeters(206.58)),
                  Rotation2d.fromDegrees(300))),
          Map.entry(
              20,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(200.27), Units.inchesToMeters(213.05)),
                  Rotation2d.fromDegrees(240))),
          Map.entry(
              21,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(235.50), Units.inchesToMeters(164.97)),
                  Rotation2d.fromDegrees(180))),
          Map.entry(
              22,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(211.48), Units.inchesToMeters(110.42)),
                  Rotation2d.fromDegrees(120))));

  public static Pose2d getTargetPose(int tagID, boolean isLeftTrigger) {
    return isLeftTrigger
        ? leftPoses.getOrDefault(tagID, null)
        : rightPoses.getOrDefault(tagID, null);
  }
}
