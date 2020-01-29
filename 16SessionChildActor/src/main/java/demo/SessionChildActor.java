package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;
import demo.MyMessage;

public class SessionChildActor {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first actor and broadcast
        final ActorRef sessionManager = system.actorOf(SessionManager.createActor(), "sessionManager");
        final ActorRef client = system.actorOf(Client.createActor(sessionManager), "client");
	
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
