package myProject;
import simbad.sim.*;
import Subsumption.Behavior;
import Subsumption.Sensors;
import Subsumption.Velocities;


public class goToLight extends Behavior {

    
    LightSensor light_r;
    LightSensor light_l;

    public goToLight(Sensors sensors, myRobot r) {
        super(sensors);
        light_l = r.getLightLeftSensor();
        light_r = r.getLightRightSensor();
    }

    public Velocities act() {
        double lL = light_l.getLux();
        double lR = light_r.getLux();
        if ((Math.abs(lL - lR) < 0.001f)&&(((lL+lR)/2.0)<0.25)) return new Velocities(1, Math.PI/2 * (0.5 * Math.signum(lL - lR)));
        if (Math.abs(lL - lR) < 0.001f) return new Velocities(0.5, 0.0);
        return new Velocities(0.5, Math.PI/2 * (0.5 * Math.signum(lL - lR)));
    }

    public boolean isActive() {
    	
        return true;
       
    }

}
