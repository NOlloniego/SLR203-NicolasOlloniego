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
    private ArrayList<ActorRef> topicList;

	public Subscriber() {}
   
    public Subscriber(ArrayList<ActorRef> topicList) {
		this.topicList = topicList;
	}


	// Static function creating actor
	public static Props createActor(ArrayList<ActorRef> topicList) {
		return Props.create(Subscriber.class, () -> {
			return new Subscriber(topicList);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("join0")){
            topicList.get(0).tell("subscribe", getSelf());
        }
		else if(message.equals("join1")){
            topicList.get(1).tell("subscribe", getSelf());
        }
		else if(message.equals("unjoin0")){
            topicList.get(0).tell("unsubscribe", getSelf());
        }
        else {
			String data = (String) message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+ "that says" + data );
        }
	}

} 