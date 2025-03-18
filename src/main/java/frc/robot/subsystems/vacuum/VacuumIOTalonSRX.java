package frc.robot.subsystems.vacuum;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Servo;

public class VacuumIOTalonSRX implements VacuumIO {

  TalonSRX _vacuumMotor;
  Servo _valveMotor;

  public VacuumIOTalonSRX() {
    _vacuumMotor = new TalonSRX(30);
    _valveMotor = new Servo(0);
  }

  public void runVacuum(boolean runVacuum) {
    if (runVacuum) {
      _vacuumMotor.set(ControlMode.PercentOutput, 1);
      _valveMotor.setAngle(50);
    } else {
      _vacuumMotor.set(ControlMode.PercentOutput, 0);
      _valveMotor.setAngle(90);
    }
  }
}
