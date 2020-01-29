package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class Subscriber extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef loadBalancer;

	public Subscriber() {}
   
    public Subscriber(ActorRef loadBalancer) {
		this.loadBalancer = loadBalancer;
	}


	// Static function creating actor
	public static Props createActor(ActorRef loadBalancer) {
		return Props.create(Subscriber.class, () -> {
			return new Subscriber(loadBalancer);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("join")){
            loadBalancer.tell("join", getSelf());
        }
		else if(message.equals("unjoin")){
            loadBalancer.tell("unjoin", getSelf());
        }
        else {
			String data = (String) message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+ "that says" + data );
        }
	}

} 