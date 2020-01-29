package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class SecondActor extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String data;
    private ArrayList<ActorRef> sendersList;

	// Empty Constructor
	public SecondActor() {
        sendersList = new ArrayList<ActorRef>();
    }
   
	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof MyMessage){
            MyMessage m = (MyMessage) message;
			this.data = m.data;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+ "that says " + data );
            this.sendersList = m.mergeList;
            Iterator<ActorRef> it = sendersList.iterator();
                while(it.hasNext()){
                    ActorRef element = it.next();
                    log.info("The message was sent by " + element.path().name());
                }
	    }
    }
}
	