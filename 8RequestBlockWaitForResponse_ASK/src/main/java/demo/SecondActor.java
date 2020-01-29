package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;

public class SecondActor extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String data;
	// Empty Constructor
	public SecondActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
			this.data = (String) message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")");
            if(message.equals("Request 1")){
				Thread.sleep(100);
                getSender().tell("Response 1", getSelf());
			}
            else getSender().tell("Response 2", getSelf());
		}
	}
	
	