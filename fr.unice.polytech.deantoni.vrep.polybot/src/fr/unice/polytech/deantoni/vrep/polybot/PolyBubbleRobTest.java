package fr.unice.polytech.deantoni.vrep.polybot;
import java.util.ArrayList;

import fr.unice.polytech.deantoni.vrep.polybot.robot.PolyBubbleRob;
import fr.unice.polytech.deantoni.vrep.polybot.utils.Blob;

public class PolyBubbleRobTest {
	
	public static void main(String[] args) {
		PolyBubbleRob rob = new PolyBubbleRob("127.0.0.1", 19997);
		
		//redFishStuff(rob);
		
		rob.start();
		rob.openGrip();
	
		rob.sleep(800);
		
		
		 double dist = 10;
		while (!rob.hasDetectedAnObject() || dist > 2) {
			 dist = rob.detectedObjectPoint.getArray()[0]*rob.detectedObjectPoint.getArray()[0]+rob.detectedObjectPoint.getArray()[1]*rob.detectedObjectPoint.getArray()[1]+rob.detectedObjectPoint.getArray()[2]*rob.detectedObjectPoint.getArray()[2];
			ArrayList<Blob> blobs = rob.getViewableBlobs();
			for(Blob b : blobs) {
				System.out.println("\t blob ["+b.positionX+" ; "+b.positionY+"]");
			}
			int[] pos = rob.getPosition();
			System.out.println("rob ["+pos[0]+" ; "+pos[1]+"]");
			int sl = (int) (Math.random()*20);
			int sr = (int) (Math.random()*20);
			rob.goCurved(sl, sr);
			rob.sleep(100);
		}
		
	
		
		
		rob.goStraight(0);
		// Now send some data to V-REP in a non-blocking fashion:

       dist = rob.detectedObjectPoint.getArray()[0]*rob.detectedObjectPoint.getArray()[0]+rob.detectedObjectPoint.getArray()[1]*rob.detectedObjectPoint.getArray()[1]+rob.detectedObjectPoint.getArray()[2]*rob.detectedObjectPoint.getArray()[2];
       rob.log2vrep("dist "+dist);

		while(dist > 0.0045) {
			
			System.out.println(dist+"\n\t"+rob.detectedObjectPoint.getArray()[0]+" "+rob.detectedObjectPoint.getArray()[1]+" "+rob.detectedObjectPoint.getArray()[2]);
			rob.goStraight(1);
			if (rob.detectedObjectPoint.getArray()[0] > 0){
				rob.goCurved(1,2);
				System.out.println("turn left");
			}
			else{
				rob.goCurved(2,1);
				System.out.println("turn right");
			}
			rob.log2vrep("dist "+dist);
			rob.sleep(100);
			rob.hasDetectedAnObject();
			dist = rob.detectedObjectPoint.getArray()[0]*rob.detectedObjectPoint.getArray()[0]+rob.detectedObjectPoint.getArray()[1]*rob.detectedObjectPoint.getArray()[1]+rob.detectedObjectPoint.getArray()[2]*rob.detectedObjectPoint.getArray()[2];
		}
		rob.log2vrep("last dist "+dist);
		
		rob.closeGrip();
		rob.sleep(1500);
		rob.goStraight(-6);
		rob.sleep(600);
		rob.goCurved(2, 5);
		rob.sleep(6000);
		rob.stopSimulation();
	}

	protected static void redFishStuff(PolyBubbleRob rob) {
		int i = 0;
		rob.start();
		while(i++ < 10) {
			if(i%2 == 0) {
				rob.openGrip();
			}else {
				rob.closeGrip();
			}
			rob.goStraight(5);
			while (!rob.hasDetectedAnObject()) {
				rob.sleep(100);
			}
			rob.goStraight(-6);
			rob.sleep(400);
			if(rob.detectedObjectPoint.getArray()[2] > rob.detectedObjectPoint.getArray()[0]) {
				rob.turnRight(6);
				rob.sleep(600);
			}else {
				rob.turnRight(6);
				rob.sleep(600);
			}
		}
		rob.stopSimulation();
	}

}
