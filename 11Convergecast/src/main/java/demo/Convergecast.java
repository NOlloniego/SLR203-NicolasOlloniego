package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Convergecast {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first actor and broadcast
        final ActorRef receiver = system.actorOf(SecondActor.createActor(), "receiver");
        final ActorRef merger = system.actorOf(Merger.createActor(receiver), "merger");
        final ActorRef b = system.actorOf(FirstActor.createActor(merger), "b");
        final ActorRef c = system.actorOf(FirstActor.createActor(merger), "c");
	    final ActorRef a = system.actorOf(FirstActor.createActor(merger), "a");
            
             //a, b and c hi
            a.tell("start", ActorRef.noSender());
            b.tell("start", ActorRef.noSender());
            c.tell("start", ActorRef.noSender());

            try {
                Thread.sleep(1000) ;
            }  catch (InterruptedException e) {
                    // gestion de l'erreur
            }

            //c unjoins
            c.tell("unjoin", ActorRef.noSender());

            //a and b hi
            a.tell("start", ActorRef.noSender());
            b.tell("start", ActorRef.noSender());
	
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
