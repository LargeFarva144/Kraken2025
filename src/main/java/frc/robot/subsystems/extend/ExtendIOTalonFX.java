// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.extend;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Second;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public class ExtendIOTalonFX implements ExtendIO {

  private final TalonFX _extendMotorK;
  private final CANcoder _extendCANCoder;

  private StatusSignal<Angle> positionRotations;
  private StatusSignal<AngularVelocity> velocityRotationsPerSecond;
  private StatusSignal<Angle> absolutePositionRotations;
  private StatusSignal<AngularVelocity> absoluteVelocityRotationsPerSecond;
  private StatusSignal<Voltage> voltage;
  private StatusSignal<Current> supplyCurrentAmps;
  private StatusSignal<Current> torqueCurrentAmps;
  private StatusSignal<Temperature> tempCelsius;

  private MotionMagicVoltage positionVoltageRequest;
  private VoltageOut voltageRequest;

  public ExtendIOTalonFX() {
    _extendMotorK = new TalonFX(ExtendConstants.extendTalonId);
    _extendCANCoder = new CANcoder(ExtendConstants.extendCANCoderId);

    positionRotations = _extendMotorK.getPosition();
    velocityRotationsPerSecond = _extendMotorK.getVelocity();
    voltage = _extendMotorK.getMotorVoltage();
    supplyCurrentAmps = _extendMotorK.getSupplyCurrent();
    torqueCurrentAmps = _extendMotorK.getTorqueCurrent();
    tempCelsius = _extendMotorK.getDeviceTemp();
    absolutePositionRotations = _extendCANCoder.getAbsolutePosition();
    absoluteVelocityRotationsPerSecond = _extendCANCoder.getVelocity();

    TalonFXConfiguration cfg = new TalonFXConfiguration();
    // spotless:off
    cfg.MotorOutput
        .withInverted(
            ExtendConstants.extendInvert
            ? InvertedValue.Clockwise_Positive
            : InvertedValue.CounterClockwise_Positive
        )
        .withNeutralMode(ExtendConstants.extendNeutralModeBrake ? NeutralModeValue.Brake : NeutralModeValue.Coast);
    cfg.CurrentLimits
        .withSupplyCurrentLimitEnable(true)
        .withSupplyCurrentLimit(40);
    cfg.ClosedLoopGeneral.ContinuousWrap = false;
    cfg.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.1;
    cfg.Slot0.kP = ExtendConstants.kP;
    cfg.Slot0.kI = ExtendConstants.kI;
    cfg.Slot0.kD = ExtendConstants.kD;
    cfg.Slot0.kG = ExtendConstants.kG;
    cfg.Slot0.kS = ExtendConstants.kS;
    cfg.Slot0.kV = ExtendConstants.kV;
    cfg.Slot0.kA = ExtendConstants.kA;
    cfg.Slot0.GravityType = GravityTypeValue.Elevator_Static;

    cfg.SoftwareLimitSwitch.ForwardSoftLimitEnable = ExtendConstants.extendForwardSoftLimitEnabled;
    cfg.SoftwareLimitSwitch.ForwardSoftLimitThreshold = ExtendConstants.defaultExtensionLimitInches;
    cfg.SoftwareLimitSwitch.ReverseSoftLimitEnable = ExtendConstants.extendReverseSoftLimitEnabled;
    cfg.SoftwareLimitSwitch.ReverseSoftLimitThreshold = ExtendConstants.extendReverseSoftLimit;
    
    cfg.Feedback.SensorToMechanismRatio = 1;
    cfg.Feedback.RotorToSensorRatio = ExtendConstants.extendGearRatio;
    cfg.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
    cfg.Feedback.FeedbackRemoteSensorID = _extendCANCoder.getDeviceID();
    
    // Motion Magic
    cfg.MotionMagic.withMotionMagicCruiseVelocity(RotationsPerSecond.of(ExtendConstants.motionMagicCruiseVelocity));
    cfg.MotionMagic.withMotionMagicAcceleration(RotationsPerSecondPerSecond.of(ExtendConstants.motionMagicAcceleration));
    cfg.MotionMagic.withMotionMagicJerk(RotationsPerSecondPerSecond.per(Second).of(ExtendConstants.motionMagicJerk));

    _extendMotorK.setPosition(_extendCANCoder.getAbsolutePosition().getValueAsDouble());
    // spotless:on

    BaseStatusSignal.setUpdateFrequencyForAll(
        50,
        positionRotations,
        velocityRotationsPerSecond,
        voltage,
        supplyCurrentAmps,
        torqueCurrentAmps,
        tempCelsius,
        absolutePositionRotations,
        absoluteVelocityRotationsPerSecond);

    _extendMotorK.optimizeBusUtilization(0.0, 1.0);
    _extendCANCoder.optimizeBusUtilization(0.0, 1.0);

    _extendMotorK.getConfigurator().apply(cfg);

    positionVoltageRequest = new MotionMagicVoltage(0.0);
    voltageRequest = new VoltageOut(0.0);
  }

  @Override
  public void setBrakeMode(boolean brakeMode) {
    _extendMotorK.setNeutralMode(brakeMode ? NeutralModeValue.Brake : NeutralModeValue.Coast);
  }

  @Override
  public void runVolts(double volts) {
    _extendMotorK.setControl(voltageRequest.withOutput(volts));
  }

  @Override
  public void extendToLength(double lengthInches) {
    double targetExtendRotations =
        (lengthInches - ExtendConstants.extendOffsetInches)
            / ExtendConstants.feedCircumferenceInches;
    _extendMotorK.setControl(
        positionVoltageRequest.withPosition(targetExtendRotations).withSlot(0));
  }

  @Override
  public Rotation2d getRotation() {
    return new Rotation2d(Units.rotationsToRadians(_extendMotorK.getPosition().getValueAsDouble()));
  }

  @Override
  public void updateExtensionLimit(double extensionLimitInches) {
    TalonFXConfigurator configurator = _extendMotorK.getConfigurator();
    TalonFXConfiguration cfg = new TalonFXConfiguration();

    cfg.SoftwareLimitSwitch.ForwardSoftLimitThreshold =
        extensionLimitInches / ExtendConstants.feedCircumferenceInches;

    configurator.refresh(cfg);

    if (positionRotations.getValueAsDouble() > extensionLimitInches) {
      extendToLength(extensionLimitInches);
    }
  }

  @Override
  public void updateInputs(ExtendIOInputs inputs) {
    inputs.connected =
        BaseStatusSignal.refreshAll(
                positionRotations,
                velocityRotationsPerSecond,
                voltage,
                supplyCurrentAmps,
                torqueCurrentAmps,
                tempCelsius,
                absolutePositionRotations,
                absoluteVelocityRotationsPerSecond)
            .isOK();

    inputs.motorPositionRotations = positionRotations.getValueAsDouble();
    inputs.encoderPositionRotations = absolutePositionRotations.getValueAsDouble();
    inputs.positionInches =
        positionRotations.getValueAsDouble() * (ExtendConstants.feedCircumferenceInches * Math.PI);
    inputs.velocityRotationsPerSecond = velocityRotationsPerSecond.getValueAsDouble();
    inputs.appliedVoltage = voltage.getValueAsDouble();
    inputs.supplyCurrentAmps = supplyCurrentAmps.getValueAsDouble();
    inputs.torqueCurrentAmps = torqueCurrentAmps.getValueAsDouble();
    inputs.tempCelsius = tempCelsius.getValueAsDouble();
  }
}
