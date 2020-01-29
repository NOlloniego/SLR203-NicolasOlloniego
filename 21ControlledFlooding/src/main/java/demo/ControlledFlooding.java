package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;
import demo.MyMessage;

public class ControlledFlooding {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
        int [][] matrix = {{0,1,1,0,0},{0,0,0,1,0},{0,0,0,1,0},{0,0,0,0,1},{0,1,0,0,0}};

        ActorRef [] actors = new ActorRef[matrix[0].length];

        for(int i = 0; i<matrix[0].length; i++){
            actors[i] = system.actorOf(Sender.createActor(), Integer.toString(i));
        }

        for(int i = 0; i<matrix[0].length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                if(matrix[i][j]==1)
                    actors[i].tell("create", actors[j]);
            }
        }

        //A starts 
        MyMessage msg = new MyMessage("hello",0);
        actors[0].tell(msg, ActorRef.noSender());

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
