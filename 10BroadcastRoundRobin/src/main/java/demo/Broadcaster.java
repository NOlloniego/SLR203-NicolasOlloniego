package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class Broadcaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef receiver;
    private ArrayList<ActorRef> broadcastList;

	public Broadcaster() {
        broadcastList = new ArrayList<ActorRef>();
    }

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Broadcaster.class, () -> {
			return new Broadcaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			String msg = (String) message;
            if(msg.equals("join")){ //Adds to list
                broadcastList.add(getSender());
                log.info("("+getSelf().path().name()+") added ("+ getSender().path().name() +")" + "to broadcast list");
            }
            else {  //Broadcasts
                Iterator<ActorRef> it = broadcastList.iterator();
                while(it.hasNext()){
                    ActorRef element = it.next();
                    element.tell(msg, getSender());
                }
            }
		}
		
	}

}