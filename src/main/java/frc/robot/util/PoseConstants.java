package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import java.util.Map;

public class PoseConstants {

  private static final Map<Integer, Pose2d> leftPoses =
      Map.ofEntries(
          Map.entry(6, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(300))),
          Map.entry(7, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(8, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(9, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(10, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(11, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(17, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(18, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(180))),
          Map.entry(19, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(120))),
          Map.entry(20, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(60))),
          Map.entry(21, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0))),
          Map.entry(22, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(300))));

  private static final Map<Integer, Pose2d> rightPoses =
      Map.ofEntries(
          Map.entry(6, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(300))),
          Map.entry(7, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(8, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(9, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(10, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(11, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(17, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(240))),
          Map.entry(18, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(180))),
          Map.entry(19, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(120))),
          Map.entry(20, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(60))),
          Map.entry(21, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0))),
          Map.entry(22, new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(300))));

  public static Pose2d getTargetPose(int tagID, boolean isLeftTrigger) {
    return isLeftTrigger
        ? leftPoses.getOrDefault(tagID, null)
        : rightPoses.getOrDefault(tagID, null);
  }
}
