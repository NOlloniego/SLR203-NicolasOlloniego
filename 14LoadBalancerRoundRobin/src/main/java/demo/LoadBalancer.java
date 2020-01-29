package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class LoadBalancer extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ArrayList<ActorRef> receiverList;
    public int i;

	public LoadBalancer() {
        receiverList = new ArrayList<ActorRef>();
        i = 0;
    }

	// Static function creating actor
	public static Props createActor() {
		return Props.create(LoadBalancer.class, () -> {
			return new LoadBalancer();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			String msg = (String) message;
            if(msg.equals("join")){
                receiverList.add(getSender());
                log.info("("+getSelf().path().name()+") added ("+ getSender().path().name() +")" + "to subscribe list");
            }
            else if(msg.equals("unjoin")){
                 receiverList.remove(getSender());
                 log.info("("+getSelf().path().name()+") removed ("+ getSender().path().name() +")" + "from subscribe list");
            }
            else {
                if(i < receiverList.size()){
                    ActorRef element = receiverList.get(i);
                    element.tell(msg, getSender());
                    i++;
                }
                else i=0;
            }
		}
		
	}

}