package myProject;

import simbad.sim.*;
import Subsumption.Behavior;
import Subsumption.Sensors;
import Subsumption.Velocities;

import javax.vecmath.Vector3d;

public class myRobot extends Agent {
	
	private LightSensor light_l;
    private LightSensor light_r;
    private RangeSensorBelt sonars;
    private Sensors sensors;
    private Behavior[] behaviors;
    private boolean suppresses[][];
    private int currentBehaviorIndex;
    private int previousBehavior = 0;
    
    public myRobot (Vector3d position, String name) 
    {
       super(position,name);

        light_l = RobotFactory.addLightSensorLeft(this);
        light_r = RobotFactory.addLightSensorRight(this);
        sonars = RobotFactory.addSonarBeltSensor(this,12);
        sensors = new Sensors(sonars);       
        Behavior[] behaviors = { new ReachGoal(sensors, this),new CircumNavigate(sensors, this, true),new goToLight(sensors, this)};
        boolean subsumes[][] = { { false, true, true },{ false, false, true },{ false, false, false }};
        this.initBehaviors(behaviors, subsumes);
        
   }
    
    public void initBehaviors(Behavior[] behaviors, boolean subsumptionRelationships[][]) {
        this.behaviors = behaviors;
        this.suppresses = subsumptionRelationships;
     }
    protected void initBehavior() {
        currentBehaviorIndex = 0;
     }
    protected void performBehavior() {
        boolean isActive[] = new boolean[behaviors.length];
        for (int i = 0; i < isActive.length; i++) {
           isActive[i] = behaviors[i].isActive();
        }
        boolean ranABehavior = false;
        while (!ranABehavior) {
           boolean runCurrentBehavior = isActive[currentBehaviorIndex];
           if (runCurrentBehavior) {
              for (int i = 0; i < suppresses.length; i++) {
                 if (isActive[i] && suppresses[i][currentBehaviorIndex]) {
                    runCurrentBehavior = false;

                    break;
                 }
              }
           }

           if (runCurrentBehavior) {
              if (currentBehaviorIndex < behaviors.length) {
                  if (currentBehaviorIndex != previousBehavior) {
                      previousBehavior = currentBehaviorIndex;
                      System.out.println("Running " + behaviors[currentBehaviorIndex].toString());
                  }
                  Velocities newVelocities = behaviors[currentBehaviorIndex].act();
                  this.setTranslationalVelocity(newVelocities.getTranslationalVelocity());
                  this.setRotationalVelocity(newVelocities.getRotationalVelocity());
              }
              ranABehavior = true;
           }

           if (behaviors.length > 0) {
              currentBehaviorIndex = (currentBehaviorIndex + 1)
                    % behaviors.length;
           }
        }}
    
    
    
    
    
    public LightSensor getLightLeftSensor() {
        return light_l;
    }

    public LightSensor getLightRightSensor() {
        return light_r;
    }	
	
    public RangeSensorBelt getSonars() {
        return sonars;
    }
	
	
	}