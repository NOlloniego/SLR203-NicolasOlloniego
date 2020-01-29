package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;

public class SecondActor extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String data;
    private ActorRef broadcaster;

	// Empty Constructor
	public SecondActor() {}
   
    public SecondActor(ActorRef actorRef) {
		this.broadcaster = actorRef;
		broadcaster.tell("join", getSelf());
	}
	// Static function that creates actor
	public static Props createActor(ActorRef b) {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor(b);
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		this.data = (String) message;
		log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+ "that says" + data );
	}
}
	
	