package myProject;
import simbad.sim.*;
import Subsumption.Behavior;
import Subsumption.Sensors;
import Subsumption.Velocities;
import Subsumption.Tools;
import javax.vecmath.*;


public class CircumNavigate extends Behavior{
    
    myRobot rob;
    boolean CLOCKWISE;
    static double K1 = 5;
    static double K2 = 0.8;
    static double K3 = 1;  
    static double SAFETY =0.8;
    boolean completed;
    double lL = 0;
    double lR = 0;
    public CircumNavigate(Sensors sensors,myRobot r, boolean CLOCKWISE) {
       super(sensors);
       this.rob = r;
       this.CLOCKWISE=CLOCKWISE;
       completed=true;
    }
    public Velocities act() {
        RangeSensorBelt sonars = this.rob.getSonars();
        
        int min;
        min=0;
        for (int i=1;i<sonars.getNumSensors();i++)
            if (sonars.getMeasurement(i)<sonars.getMeasurement(min))
                min=i;
        Point3d p = Tools.getSensedPoint(rob,sonars,min);
        double d = p.distance(new Point3d(0,0,0));  
        Vector3d v;
        v = CLOCKWISE? new Vector3d(-p.z,0,p.x): new Vector3d(p.z,0,-p.x);
        double phLin = Math.atan2(v.z,v.x);
        double phRot =Math.atan(K3*(d-SAFETY));
        if (CLOCKWISE)
            phRot=-phRot;
        double phRef = Tools.wrapToPi(phLin+phRot); 
                
        return new Velocities(K2*Math.cos(phRef),K1*phRef);
    }
    public boolean isActive() {
        RangeSensorBelt sonars = this.rob.getSonars();
        if (!completed){
            if (sonars.getFrontQuadrantHits() == 0) {
                if (CLOCKWISE) {
                    if (!rightHasHit()) {
                        completed = true;
                        
                        return false;
                    }
                }
                else if (!leftHasHit()) {
                    completed = true;
                    return false;
                }


                if (this.rob.getLightRightSensor().getLux() > lR && this.rob.getLightLeftSensor().getLux() > lL) {
                    completed = true;
                    
                    return false;
                }
            }
            return true;
        }

        if (sonars.getFrontQuadrantHits() > 0) {
            completed=false;
            lL = this.rob.getLightLeftSensor().getLux();
            lR = this.rob.getLightRightSensor().getLux();
            this.CLOCKWISE = lL < lR;
            return true;
        }
        return false;

    }           
         
    
    
    private boolean leftHasHit() {
        if (this.rob.getSonars().hasHit(2)||this.rob.getSonars().hasHit(3)) {
            return true;
        }
        
        
        return false;
    }

    private boolean rightHasHit() {
    	if (this.rob.getSonars().hasHit(9)|| this.rob.getSonars().hasHit(10)) {
            return true;}
        return false;}
    
    
    
    
}
