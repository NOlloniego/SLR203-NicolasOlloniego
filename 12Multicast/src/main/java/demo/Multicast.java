package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;

public class Multicast {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		ArrayList<ActorRef> receiversList = new ArrayList<ActorRef>();
		
		// Instantiate first actor and broadcast
		final ActorRef rec1 = system.actorOf(Receiver.createActor(), "receiver1");
		receiversList.add(rec1);
		final ActorRef rec2 = system.actorOf(Receiver.createActor(), "receiver2");
		receiversList.add(rec2);
		final ActorRef rec3 = system.actorOf(Receiver.createActor(), "receiver3");
		receiversList.add(rec3);
        final ActorRef multicaster = system.actorOf(Multicaster.createActor(), "multicaster");
        final ActorRef sender = system.actorOf(Sender.createActor(receiversList, multicaster), "sender");
            //sends to group
        sender.tell("send", ActorRef.noSender());
	
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
