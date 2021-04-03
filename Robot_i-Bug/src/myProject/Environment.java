package myProject;
import javax.vecmath.*;
import simbad.sim.*;
public class Environment extends EnvironmentDescription {
	public Environment()
	{
		add(new myRobot(new Vector3d(-6, 0, -2),"Robot")); 
		
		
		add(new Box(new Vector3d(4,0,0),new Vector3f(3f,1.5f,9f),this));
		
		 	
		
		light1IsOn=true;
	    light1Color= new Color3f(0,1,1);
	    light1Position = new Vector3d(8,0,6);
	    
	       
    }

}
