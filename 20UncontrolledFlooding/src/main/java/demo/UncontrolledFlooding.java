package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;

public class UncontrolledFlooding {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
        int [][] matrix1 = {{0,1,1,0,0},{0,0,0,1,0},{0,0,0,1,0},{0,0,0,0,1},{0,0,0,0,0}};
        int [][] matrix2 = {{0,1,1,0,0},{0,0,0,1,0},{0,0,0,1,0},{0,0,0,0,1},{0,1,0,0,0}};

        ActorRef [] actors = new ActorRef[matrix1[0].length];

    //First example
        for(int i = 0; i<matrix1[0].length; i++){
            actors[i] = system.actorOf(Sender.createActor(), Integer.toString(i));
        }

        for(int i = 0; i<matrix1[0].length; i++){
            for(int j = 0; j<matrix1[0].length; j++){
                if(matrix1[i][j]==1)
                    actors[i].tell("create", actors[j]);
            }
        }
        

    //Second example
    /*    for(int i = 0; i<matrix2[0].length; i++){
            actors[i] = system.actorOf(Sender.createActor(), Integer.toString(i));
        }

        for(int i = 0; i<matrix2[0].length; i++){
            for(int j = 0; j<matrix2[0].length; j++){
                if(matrix2[i][j]==1)
                    actors[i].tell("create", actors[j]);
            }
        }
        */

        //A starts 
        actors[0].tell("hello", ActorRef.noSender());

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
