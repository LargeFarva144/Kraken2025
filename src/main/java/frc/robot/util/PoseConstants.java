package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import java.util.Map;

public class PoseConstants {

  private static final Map<Integer, Pose2d> leftPoses =
      Map.ofEntries(
          Map.entry(
              17,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(141.02), Units.inchesToMeters(110.42)),
                  Rotation2d.fromDegrees(60))),
          Map.entry(
              18,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(117.00), Units.inchesToMeters(164.97)),
                  Rotation2d.fromDegrees(0))),
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
              17,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(152.23), Units.inchesToMeters(103.95)),
                  Rotation2d.fromDegrees(60))),
          Map.entry(
              18,
              new Pose2d(
                  new Translation2d(Units.inchesToMeters(117.00), Units.inchesToMeters(152.03)),
                  Rotation2d.fromDegrees(0))),
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
