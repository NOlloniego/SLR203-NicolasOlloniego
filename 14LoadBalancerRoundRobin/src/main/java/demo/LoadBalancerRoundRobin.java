package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;

public class LoadBalancerRoundRobin {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first actor and broadcast
        final ActorRef loadBalancer = system.actorOf(LoadBalancer.createActor(), "loadBalancer");
        final ActorRef sender = system.actorOf(Sender.createActor(loadBalancer), "sender");
        final ActorRef b = system.actorOf(Subscriber.createActor(loadBalancer), "b");
        final ActorRef c = system.actorOf(Subscriber.createActor(loadBalancer), "c");
	    
			//b and c subscribe to load balancer
			b.tell("join", ActorRef.noSender());
			c.tell("join", ActorRef.noSender());

			//publishing
			sender.tell("start", ActorRef.noSender());
                        
			//b unsubscribes from load balancer
			b.tell("unjoin", ActorRef.noSender());
            
            sender.tell("start", ActorRef.noSender());
	
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
