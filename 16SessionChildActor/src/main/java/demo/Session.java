package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class Session extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
  private ActorRef client;

	public Session() {}
  
  public Session(ActorRef client) {
		this.client = client;
	}


	// Static function creating actor
	public static Props createActor(ActorRef client) {
		return Props.create(Session.class, () -> {
			return new Session(client);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("helloSession")){
                client.tell("helloClient", getSelf());
                log.info("("+getSelf().path().name()+") received message from ("+ getSender().path().name()+ ")"+ " that says "+ msg);
            }
            else if(msg.equals("howAreYou")){
                client.tell("fine", getSelf());
                log.info("("+getSelf().path().name()+") received message from ("+ getSender().path().name()+ ")"+ " that says "+ msg);
            }
        }
	}

} 