package frc.robot.subsystems.vacuum;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;

public class VacuumIOTalonSRX implements VacuumIO {

  TalonSRX _vacuumMotor;
  Servo _valveMotor;
  AnalogInput _vacuumSensor;

  public VacuumIOTalonSRX() {
    _vacuumMotor = new TalonSRX(30);
    _valveMotor = new Servo(0);
    _vacuumSensor = new AnalogInput(9);

    double supplyCurrentAmps = _vacuumMotor.getStatorCurrent();
  }

  public void toggleVacuum() {
    if (_vacuumMotor.getMotorOutputVoltage() > 0) {
      _vacuumMotor.set(ControlMode.PercentOutput, 0);
      _valveMotor.setAngle(100);
    } else {
      _vacuumMotor.set(ControlMode.PercentOutput, 1);
      _valveMotor.setAngle(40);
    }
  }

  public boolean getVacuumHasPressure() {
    double voltage = _vacuumSensor.getVoltage();

    if (voltage < 1.0) {
      return true;
    } else {
      return false;
    }
  }

  public void runVacuum(boolean runVacuum) {
    if (runVacuum) {
      _vacuumMotor.set(ControlMode.PercentOutput, 1);
      _valveMotor.setAngle(40); // change degree
    } else {
      _vacuumMotor.set(ControlMode.PercentOutput, 0);
      _valveMotor.setAngle(100);
    }
  }

  @Override
  public void updateInputs(VacuumIOInputs inputs) {
    inputs.supplyCurrentAmps = _vacuumMotor.getStatorCurrent();
    inputs.sensorVolts = _vacuumSensor.getVoltage();
  }
}
