package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef merger;

	public FirstActor() {}
   
    public FirstActor(ActorRef actorRef) {
		this.merger = actorRef;
		merger.tell("join", getSelf());
	}


	// Static function creating actor
	public static Props createActor(ActorRef b) {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor(b);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			String msg = (String) message;
			if(msg.equals("start")){
				merger.tell("hi", getSelf());
			}
			else if(msg.equals("unjoin")){
				merger.tell("unjoin", getSelf());
			}
		} 
	}

} 