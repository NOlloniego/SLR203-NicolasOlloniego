package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import demo.MyMessage;

public class RespondTo {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor, transmitter
        final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
        final ActorRef transmitter = system.actorOf(Transmitter.createActor(), "transmitter");
	    final ActorRef a = system.actorOf(FirstActor.createActor(b, transmitter), "a");
	
	    // We wait 5 seconds before ending system (by default)
	    // But this is not the best solution.
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
