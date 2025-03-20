package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Climb extends SubsystemBase {
  // Gear Ratio 180/1

  private ClimbIO io;
  private ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged(); /*updates IO inputs
 /* and creates a place to update them */
  private double angle;

  public Climb(ClimbIO io) {
    this.io = io;
  }

  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Climb", inputs);
  }

  public void runVolts(double volts) {
    io.runVolts(volts);
  }


  public void stop() {
    io.stop();
  }

  // public Rotation2d getAngle() {
  //   return io.getAngle();
  // }

  
  public double setAngle() {
    return io.setAngle();
  }
  // public Rotation2d getAngle() {
  //   return io.getAngle();
  // }

  // public void setClimbPosition(Rotation2d climbAngle) {
  //   io.setAngle(climbAngle.getRotations());
  //   /*  above, is this to get from rotations to degrees? I know it records something and
  // processes it because its IO. */
  //   this.angle = climbAngle.getDegrees();
  // }

  // public boolean atClimb() {
  //   if (angle < (io.setAngle().getDegrees() + 1) && angle > io.setAngle().getRotations() - 1) {
  //     return true;
  //   } else {
  //     return false;
  //   }
  // }
}
