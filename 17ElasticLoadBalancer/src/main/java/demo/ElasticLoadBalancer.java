package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;

public class ElasticLoadBalancer {

	public static void main(String[] args) {

        int max = 2;

		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first actor and broadcast
        final ActorRef loadBalancer = system.actorOf(LoadBalancer.createActor(max), "loadBalancer");
        final ActorRef sender = system.actorOf(Sender.createActor(loadBalancer), "sender");

		//publishing
		sender.tell("main", ActorRef.noSender());
        sender.tell("main", ActorRef.noSender());
        sender.tell("main", ActorRef.noSender());
	
        try{
            Thread.sleep(100);
        } catch(Exception e){}

        loadBalancer.tell("main", ActorRef.noSender());
        loadBalancer.tell("main2", ActorRef.noSender());
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
