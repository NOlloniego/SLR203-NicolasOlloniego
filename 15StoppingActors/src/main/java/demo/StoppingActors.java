package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import static akka.pattern.Patterns.gracefulStop;
import akka.pattern.AskTimeoutException;
import java.util.concurrent.CompletionStage;
import java.time.*;
import akka.dispatch.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.Promise;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;

public class StoppingActors {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first actor and broadcast
        final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");
        final ActorRef b = system.actorOf(SecondActor.createActor(broadcaster), "b");
        final ActorRef c = system.actorOf(SecondActor.createActor(broadcaster), "c");
        final ActorRef d = system.actorOf(SecondActor.createActor(broadcaster), "d");
        final ActorRef f = system.actorOf(SecondActor.createActor(broadcaster), "f");
	    final ActorRef a = system.actorOf(FirstActor.createActor(broadcaster), "a");

        system.stop(b);
        c.tell(akka.actor.PoisonPill.getInstance(),ActorRef.noSender());
        d.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
        gracefulStop(f, Duration.ofSeconds(5), akka.actor.PoisonPill.getInstance());  

		a.tell("Anyone alive?", ActorRef.noSender());

	    // We wait 5 seconds before ending system (by default)
	    // But this is not the best solution.
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}

}
