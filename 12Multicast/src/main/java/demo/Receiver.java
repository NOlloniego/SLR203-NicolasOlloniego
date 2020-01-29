package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;
import demo.MessageGroup;

public class Receiver extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String data;

	// Empty Constructor
	public Receiver() {
    }
   
	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Receiver.class, () -> {
			return new Receiver();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+ "that says " + msg );
	    }
    }
}
	