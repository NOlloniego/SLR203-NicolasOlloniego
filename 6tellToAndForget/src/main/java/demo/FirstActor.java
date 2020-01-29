package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import demo.MyMessage;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef b;
	private ActorRef transmitter;

	public FirstActor() {}
   
    public FirstActor(ActorRef actorRef1, ActorRef actorRef2) {
		this.b = actorRef1;
		this.transmitter = actorRef2;
		MyMessage m = new MyMessage("hello", this.b);
        transmitter.tell(m, getSelf());
	}


	// Static function creating actor
	public static Props createActor(ActorRef b, ActorRef actorRef) {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor(b, actorRef);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
		String data = (String) message;
		log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")");
	}

}
