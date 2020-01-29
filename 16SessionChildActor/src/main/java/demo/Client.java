package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class Client extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef sessionManager;
    private ActorRef session;
    int z;

	public Client() {}
   
    public Client(ActorRef sessionManager) {
		this.sessionManager = sessionManager;
        this.z = 0;
        sessionManager.tell("createSession", getSelf());
	}


	// Static function creating actor
	public static Props createActor(ActorRef sessionManager) {
		return Props.create(Client.class, () -> {
			return new Client(sessionManager);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("fine"))
                sessionManager.tell("endSession", getSelf());
            else if (z==1){
                log.info("("+getSelf().path().name()+") received message from ("+ getSender().path().name()+ ")"+ " that says "+ msg);
                session.tell("howAreYou", getSelf());
            }
		}
        else if(message instanceof MyMessage){
            MyMessage msg2 = (MyMessage) message;
            session = msg2.session;
            z = 1;
            log.info("("+getSelf().path().name()+") conected to session ("+ session.path().name()+")");
            session.tell("helloSession", getSelf());
        }
	}

} 