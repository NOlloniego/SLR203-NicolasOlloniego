
package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RequestBlockWaitForResponse {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
        final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
	    final ActorRef a = system.actorOf(FirstActor.createActor(b), "a");
	
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
