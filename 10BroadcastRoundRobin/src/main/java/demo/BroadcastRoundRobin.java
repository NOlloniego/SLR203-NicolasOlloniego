package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class BroadcastRoundRobin {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first actor and broadcast
        final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");
        final ActorRef b = system.actorOf(SecondActor.createActor(broadcaster), "b");
        final ActorRef c = system.actorOf(SecondActor.createActor(broadcaster), "c");
	    final ActorRef a = system.actorOf(FirstActor.createActor(broadcaster), "a");

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
