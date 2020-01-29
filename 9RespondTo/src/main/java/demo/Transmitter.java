package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;

public class Transmitter extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef receiver;

	public Transmitter() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Transmitter.class, () -> {
			return new Transmitter();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MyMessage){
			MyMessage msg = (MyMessage) message;
            if(msg.data.equals("req1")){
				log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")" + "that says" + msg.data );
                msg.receiver.tell("Response 1", getSelf());
            }
		}
		
	}

}