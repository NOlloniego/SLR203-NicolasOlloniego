package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class Merger extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef receiver;
    private ArrayList<ActorRef> mergeList;
    private ArrayList<ActorRef> receivedList;

	public Merger(ActorRef actorRef) {
        mergeList = new ArrayList<ActorRef>();
        receivedList = new ArrayList<ActorRef>();
        this.receiver = actorRef;
    }

	// Static function creating actor
	public static Props createActor(ActorRef actorRef) {
		return Props.create(Merger.class, () -> {
			return new Merger(actorRef);
		});
	}


    //Asuming they all send the same message
	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			String msg = (String) message;
            if(msg.equals("join")){
                mergeList.add(getSender());
                log.info("("+getSelf().path().name()+") added ("+ getSender().path().name() +")" + "to merge list");
            }
            else if(msg.equals("unjoin")){
                mergeList.remove(mergeList.indexOf(getSender()));
                log.info("("+getSelf().path().name()+") removed ("+ getSender().path().name() +")" + "from merge list");
            }
            else {
                if(!receivedList.contains(getSender()))
                    receivedList.add(getSender());
                if(mergeList.size()==receivedList.size()){
                    MyMessage m = new MyMessage(msg,mergeList);
                    receiver.tell(m,getSelf());  //How indicate multiple senders
                    receivedList.clear();                   
                 }
            }
		}
		
	}

}