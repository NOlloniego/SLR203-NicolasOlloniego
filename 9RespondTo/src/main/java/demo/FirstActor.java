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
	private ActorRef c;

	public FirstActor() {}
   
    public FirstActor(ActorRef actorRef1, ActorRef actorRef2) {
		this.b = actorRef1;
		this.c = actorRef2;
		MyMessage m = new MyMessage("req1", this.b);
        c.tell(m, getSelf());
	}


	// Static function creating actor
	public static Props createActor(ActorRef b, ActorRef c) {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor(b,c);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {		
	}

}