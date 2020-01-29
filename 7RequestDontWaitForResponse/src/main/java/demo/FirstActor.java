package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef b;
    private String m;

	public FirstActor() {}
   
    public FirstActor(ActorRef actorRef) {
		this.b = actorRef;
		b.tell("Request 1", getSelf());
        b.tell("Request 2", getSelf());
	}


	// Static function creating actor
	public static Props createActor(ActorRef b) {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor(b);
		});
	}



        	@Override
	public void onReceive(Object message) throws Throwable {
        m = (String) message;
    	log.info("("+getSelf().path().name()+") received a response from ("+ getSender().path().name() + "that says"+ m +")");
	}

}