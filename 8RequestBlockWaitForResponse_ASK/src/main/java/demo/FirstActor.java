package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.time.Duration;
import akka.dispatch.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.Promise;
import akka.util.Timeout;
import akka.pattern.Patterns;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

import java.util.concurrent.CompletableFuture;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef b;
    private String m;

	public FirstActor() {}
   
   //Sends a message to b and blocks until response or timeout
    public FirstActor(ActorRef actorRef) {
		Timeout t = Timeout.create(Duration.ofSeconds(5));
     	Future<Object> future = Patterns.ask(b, "req1", t);
		this.b = actorRef;
		b.tell("Request 1", getSelf());
		try {
         String res1 = (String) Await.result(future, t.duration());
         log.info("("+getSelf().path().name()+") received message from ("+ getSender().path().name() +") that syays " +res1);
      } catch (Exception e){}
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
			log.info("("+getSelf().path().name()+") received message from ("+ getSender().path().name() +") that syays : ("+msg+")");
			if(msg.equals("Response 1"))
			b.tell("Request 2", getSelf());
        }
	}

}