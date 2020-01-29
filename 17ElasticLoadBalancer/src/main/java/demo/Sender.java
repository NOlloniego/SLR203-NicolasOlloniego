package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class Sender extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef loadBalancer;

	public Sender() {}
   
    public Sender(ActorRef loadBalancer) {
		this.loadBalancer = loadBalancer;
	}


	// Static function creating actor
	public static Props createActor(ActorRef loadBalancer) {
		return Props.create(Sender.class, () -> {
			return new Sender(loadBalancer);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("main"))
                loadBalancer.tell("newTask", getSelf());
        }
    }
} 