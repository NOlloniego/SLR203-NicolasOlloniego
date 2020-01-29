package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class Sender extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    ArrayList<ActorRef> actorList;
    ArrayList<Integer> sequenceNumberList;

	public Sender() {
        this.actorList = new ArrayList<ActorRef>();
        this.sequenceNumberList = new ArrayList<Integer>();;
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
            if(msg.equals("create")){
                actorList.add(getSender());
                log.info("("+getSelf().path().name()+") added actor ("+ getSender().path().name() +")");
            }
        }
        if(message instanceof MyMessage){
            MyMessage msg = (MyMessage) message;
            if(!sequenceNumberList.contains(msg.sequenceNumber)){
                sequenceNumberList.add(msg.sequenceNumber);
                Iterator<ActorRef> i = actorList.iterator();
                while(i.hasNext()){
                    ActorRef element = i.next();
                    element.tell(msg, getSelf());
                }
                log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +") that says"+ msg.message);
            }
        }
    }
} 