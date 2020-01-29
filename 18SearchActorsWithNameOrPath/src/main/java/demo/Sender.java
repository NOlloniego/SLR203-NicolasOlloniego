package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.ActorIdentity;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import java.util.*;

public class Sender extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    int i;
    ActorIdentity actorIdentity;

	public Sender() {
        i = 0;
    }

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("create"))
                getContext().actorOf(Sender.createActor(), "actor"+i);
                log.info("["+getSelf().path().name()+"] created actor" + i);
                i++;
        }
        if(message instanceof ActorIdentity) {
            this.actorIdentity = (ActorIdentity) message;
            try{
                ActorRef actor = this.actorIdentity.getActorRef().get();
                log.info("Path: [" + actor.path() + "]");
            }catch(Exception e){}
            
        }
    }
} 