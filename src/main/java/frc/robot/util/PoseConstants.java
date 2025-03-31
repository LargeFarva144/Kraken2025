package frc.robot.util;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.vision.VisionConstants;
import java.util.Optional;

public class PoseConstants {
  //   private static double flippedX = 337.37;
  private static final AprilTagFieldLayout field =
      AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
  private static final double distanceFromReefFace = Units.inchesToMeters(26); // Old: 26, New: 20
  private static final double poleDistanceRight = Units.inchesToMeters(8.5);
  private static final double poleDistanceLeft = Units.inchesToMeters(5.5);

  //   private static final Map<Integer, Pose2d> leftPoses =
  //       Map.ofEntries(
  //           Map.entry(
  //               11,
  //               new Pose2d(
  //                   new Translation2d(
  //                       Units.inchesToMeters(475.66),
  //                       Units.inchesToMeters(104.83)), // X:141.02 + xflipped, Y:110.42
  //                   // +y goes left from drivers station -y goes right from drivers station
  //                   // +X goes up from driver station, -X goes down from driver station
  //                   Rotation2d.fromDegrees(60))),
  //           Map.entry(
  //               10,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(448.39),
  // Units.inchesToMeters(164.98)),
  //                   Rotation2d.fromDegrees(2))), // X:117.00 + xflipped, Y:163.97
  //           Map.entry(
  //               9,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(486.88),
  // Units.inchesToMeters(218.65)),
  //                   Rotation2d.fromDegrees(300))), // X:152.23 + xflipped, Y:213.05
  //           Map.entry(
  //               8,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(552.60),
  // Units.inchesToMeters(212.17)),
  //                   Rotation2d.fromDegrees(240))), // X:211.48  + xflipped, Y:206.58
  //           Map.entry(
  //               7,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(567.87),
  // Units.inchesToMeters(152.52)),
  //                   Rotation2d.fromDegrees(180))), // X:235.50 + xflipped, Y:152.03
  //           Map.entry(
  //               6,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(541.38), Units.inchesToMeters(98.35)),
  //                   Rotation2d.fromDegrees(120))), // X:204.7 + xflipped, Y:101.08
  //           Map.entry(
  //               17,
  //               new Pose2d(
  //                   new Translation2d(
  //                       Units.inchesToMeters(147.01),
  //                       Units.inchesToMeters(115.96)), // X:141.02, Y:110.42
  //                   // +y goes left from drivers station -y goes right from drivers station
  //                   // +X goes up from driver station, -X goes down from driver station
  //                   Rotation2d.fromDegrees(60))),
  //           Map.entry(
  //               18,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(124.00),
  // Units.inchesToMeters(162.98)),
  //                   Rotation2d.fromDegrees(2))),
  //           Map.entry(
  //               19,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(149.5), Units.inchesToMeters(218.65)),
  //                   Rotation2d.fromDegrees(300))),
  //           Map.entry(
  //               20,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(215.21),
  // Units.inchesToMeters(212.17)),
  //                   Rotation2d.fromDegrees(240))),
  //           Map.entry(
  //               21,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(230.49),
  // Units.inchesToMeters(153.02)),
  //                   Rotation2d.fromDegrees(180))),
  //           Map.entry(
  //               22,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(203.99), Units.inchesToMeters(98.35)),
  //                   Rotation2d.fromDegrees(120))));

  //   private static final Map<Integer, Pose2d> rightPoses =
  //       Map.ofEntries(
  //           Map.entry(
  //               11,
  //               new Pose2d(
  //                   new Translation2d(
  //                       Units.inchesToMeters(486.88),
  //                       Units.inchesToMeters(98.35)), // X:152.00 + xflipped, Y:104.00
  //                   Rotation2d.fromDegrees(60))),
  //           Map.entry(
  //               10,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(448.39),
  // Units.inchesToMeters(149.02)),
  //                   Rotation2d.fromDegrees(2))), // X:117.00 + xflipped, Y:150.03
  //           Map.entry(
  //               9,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(475.66),
  // Units.inchesToMeters(212.17)),
  //                   Rotation2d.fromDegrees(300))), // X:141.02 + xflipped, Y:206.58
  //           Map.entry(
  //               8,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(541.38),
  // Units.inchesToMeters(218.65)),
  //                   Rotation2d.fromDegrees(240))), // X:200.27 + xflipped, Y:213.23
  //           Map.entry(
  //               7,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(567.87),
  // Units.inchesToMeters(166.48)),
  //                   Rotation2d.fromDegrees(180))), // X:235.50 + xflipped, Y:164.97
  //           Map.entry(
  //               6,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(552.6), Units.inchesToMeters(104.83)),
  //                   Rotation2d.fromDegrees(120))), // X:215.97 + xflipped, Y:107.56
  //           Map.entry(
  //               17,
  //               new Pose2d(
  //                   new Translation2d(
  //                       Units.inchesToMeters(159.1),
  //                       Units.inchesToMeters(108.97)), // X:152.23, Y:103.95
  //                   Rotation2d.fromDegrees(60))),
  //           Map.entry(
  //               18,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(124.00),
  // Units.inchesToMeters(149.02)),
  //                   Rotation2d.fromDegrees(2))),
  //           Map.entry(
  //               19,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(138.28),
  // Units.inchesToMeters(212.17)),
  //                   Rotation2d.fromDegrees(300))),
  //           Map.entry(
  //               20,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(203.99),
  // Units.inchesToMeters(218.65)),
  //                   Rotation2d.fromDegrees(240))),
  //           Map.entry(
  //               21,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(232.49),
  // Units.inchesToMeters(165.98)),
  //                   Rotation2d.fromDegrees(180))),
  //           Map.entry(
  //               22,
  //               new Pose2d(
  //                   new Translation2d(Units.inchesToMeters(215.21),
  // Units.inchesToMeters(104.83)),
  //                   Rotation2d.fromDegrees(120))));

  public static Optional<Pose2d> getTargetPose(int tagID, boolean isLeftTrigger) {
    if (!VisionConstants.reefTags.contains(tagID)) {
      return Optional.empty();
    }

    Optional<Pose3d> maybeTagPose = field.getTagPose(tagID);
    if (maybeTagPose.isEmpty()) {
      return Optional.empty();
    }

    Pose2d tagPose = maybeTagPose.get().toPose2d();
    Pose2d adjustedTagPose =
        tagPose.transformBy(
            new Transform2d(
                distanceFromReefFace,
                isLeftTrigger ? -poleDistanceLeft : poleDistanceRight,
                Rotation2d.kZero));
    Pose2d robotPose = adjustedTagPose.transformBy(new Transform2d(0, 0, Rotation2d.k180deg));

    return Optional.of(robotPose);
  }
}
