package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class Topic extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ArrayList<ActorRef> receiverList;

	public Topic() {
        receiverList = new ArrayList<ActorRef>();
    }

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Topic.class, () -> {
			return new Topic();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			String msg = (String) message;
            if(msg.equals("subscribe")){
                receiverList.add(getSender());
                log.info("("+getSelf().path().name()+") added ("+ getSender().path().name() +")" + "to subscribe list");
            }
            else if(msg.equals("unsubscribe")){
                 receiverList.remove(getSender());
                 log.info("("+getSelf().path().name()+") removed ("+ getSender().path().name() +")" + "from subscribe list");
            }
            else {
                Iterator<ActorRef> it = receiverList.iterator();
                while(it.hasNext()){
                    ActorRef element = it.next();
                    element.tell(msg, getSelf());
                }
            }
		}
		
	}

}