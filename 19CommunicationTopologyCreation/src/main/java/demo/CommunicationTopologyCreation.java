package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;

public class CommunicationTopologyCreation {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
        int matrix[][] = new int[4][4];
        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 0;
        matrix[1][2] = 0;
        matrix[1][3] = 1;
        matrix[2][0] = 1;
        matrix[2][1] = 0;
        matrix[2][2] = 0;
        matrix[2][3] = 1;
        matrix[3][0] = 1;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        ActorRef [] actors = new ActorRef[4];

        for(int i = 0; i<matrix[0].length; i++){
            actors[i] = system.actorOf(Sender.createActor(), Integer.toString(i));
        }

        for(int i = 0; i<matrix[0].length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                if(matrix[i][j]==1)
                    actors[i].tell("create", actors[j]);
            }
        }

	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}

}
