package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MessageGroup;
import demo.Group;
import demo.MyMessage;

public class Sender extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef multicaster;
    ArrayList<ActorRef> receivers;
    ArrayList<ActorRef> grp1;

    public Sender(ArrayList<ActorRef> receivers, ActorRef multicaster) {
		this.receivers = receivers;
        this.multicaster = multicaster;
		grp1 = new ArrayList<ActorRef>();
        grp1.add(receivers.get(0));
        grp1.add(receivers.get(1));
        MessageGroup msg = new MessageGroup("group1", grp1);
        multicaster.tell(msg, getSelf());
	}


	// Static function creating actor
	public static Props createActor(ArrayList<ActorRef> receivers, ActorRef multicaster) {
		return Props.create(Sender.class, () -> {
			return new Sender(receivers, multicaster);
		});
	}

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message.equals("send")){
            MyMessage msg2 = new MyMessage("group1", "Hello group 1");
            multicaster.tell(msg2, getSelf());
        }
	}

} 