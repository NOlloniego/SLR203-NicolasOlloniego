package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class Publisher extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef topic;

	public Publisher() {}
   
    public Publisher(ActorRef actorRef) {
		this.topic = actorRef;
	}


	// Static function creating actor
	public static Props createActor(ActorRef b) {
		return Props.create(Publisher.class, () -> {
			return new Publisher(b);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
            topic.tell("First theme published", getSelf());
		}
		
	}

} 