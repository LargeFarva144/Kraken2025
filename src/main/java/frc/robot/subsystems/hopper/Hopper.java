package frc.robot.subsystems.hopper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.extend.ExtendIOInputsAutoLogged;

public class Hopper extends SubsystemBase{
    private HopperIO io;
    private HopperIOInputsAutoLogged inputs = new ExtendIOInputsAutoLogged();

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
