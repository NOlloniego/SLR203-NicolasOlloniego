package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;

public class PublishSubscribe {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");

        ArrayList<ActorRef> topicList = new ArrayList<ActorRef>();
		
		// Instantiate first actor and broadcast
        final ActorRef topic1 = system.actorOf(Topic.createActor(), "topic1");
        final ActorRef topic2 = system.actorOf(Topic.createActor(), "topic2");
        topicList.add(topic1);
        topicList.add(topic2);
        final ActorRef publisher1 = system.actorOf(Publisher.createActor(topic1), "publisher1");
        final ActorRef publisher2 = system.actorOf(Publisher.createActor(topic2), "publisher2");
        final ActorRef b = system.actorOf(Subscriber.createActor(topicList), "b");
        final ActorRef c = system.actorOf(Subscriber.createActor(topicList), "c");
	    final ActorRef a = system.actorOf(Subscriber.createActor(topicList), "a");
	    
			//a and b subscribe to topic 1
			a.tell("join0", ActorRef.noSender());
			b.tell("join0", ActorRef.noSender());

            //b and c subscribe to topic 2
            c.tell("join1", ActorRef.noSender());
			b.tell("join1", ActorRef.noSender());

			//publishing
			publisher1.tell("main", ActorRef.noSender());
			publisher2.tell("main", ActorRef.noSender());

			//b unsubscribes from topic 1
			b.tell("unjoin0", ActorRef.noSender());

			//publish again
			publisher1.tell("pub", ActorRef.noSender());
	
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
