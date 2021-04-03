package myProject;
import simbad.sim.*;
import Subsumption.Behavior;
import Subsumption.Sensors;
import Subsumption.Velocities;

class ReachGoal extends Behavior {

    LightSensor light_r;
    LightSensor light_l;

    public ReachGoal(Sensors sensors, myRobot r) {
        super(sensors);
        light_l = r.getLightLeftSensor();
        light_r = r.getLightRightSensor();
    }

    public Velocities act() {
        return new Velocities(0.0, 0.0);
    }

    public boolean isActive() {
        double lL = light_l.getLux();
        double lR = light_r.getLux();

        if (lL >= 0.57 && lR >= 0.57) {
            return true;
        }
        return false;
    }

}
