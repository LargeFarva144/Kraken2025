package frc.robot.subsystems.vacuum;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Vacuum extends SubsystemBase {

  private VacuumIO io;
  private VacuumIOInputsAutoLogged inputs = new VacuumIOInputsAutoLogged();

  public Vacuum(VacuumIO io) {
    this.io = io;
  }

  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Vacuum", inputs);
  }

  public void runVacuum(boolean runVacuum) {
    io.runVacuum(runVacuum);
  }

  public void toggleVacuum() {
    io.toggleVacuum();
  }
}
