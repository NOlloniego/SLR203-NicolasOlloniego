package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorIdentity;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import java.util.*;

public class SearchActorsWithNameOrPath {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		ActorSelection selection;
		
		// Instantiate first actor and broadcast
        final ActorRef a = system.actorOf(Sender.createActor(), "a");

		//creating children
		a.tell("create", ActorRef.noSender());
        a.tell("create", ActorRef.noSender());

		//Searching

		selection = system.actorSelection("/user/" + "a");
	    selection.tell(new Identify(1), a);
		
		selection = system.actorSelection("/user/a/" + "actor0");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/user/a/" + "actor1");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/user/a");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/user/a/actor0");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/user/a/actor1");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/user/*");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/system/*");
		selection.tell(new Identify(1), a);
		
		selection = system.actorSelection("/deadLetters/*");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/temp/*");
		selection.tell(new Identify(1), a);

		selection = system.actorSelection("/remote/*");
		selection.tell(new Identify(1), a);
        
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
