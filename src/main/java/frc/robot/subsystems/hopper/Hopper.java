package frc.robot.subsystems.hopper;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Hopper extends SubsystemBase {
  private HopperIO io;
  private HopperIOInputsAutoLogged inputs = new HopperIOInputsAutoLogged();

  public Hopper(HopperIO io) {
    this.io = io;
  }

  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Hopper", inputs);
  }

  public boolean getObjectDetection() {
    return io.getObjectDetection();
  }
}
