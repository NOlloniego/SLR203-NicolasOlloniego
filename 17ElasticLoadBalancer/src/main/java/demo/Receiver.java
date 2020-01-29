package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class Receiver extends UntypedAbstractActor{

	// Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef loadBalancer;
    private int tasks;

	public Receiver() {}
  
    public Receiver(ActorRef loadBalancer) {
		this.loadBalancer = loadBalancer;
        tasks = 0;
	}


	// Static function creating actor
	public static Props createActor(ActorRef loadBalancer) {
		return Props.create(Receiver.class, () -> {
			return new Receiver(loadBalancer);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("newTask")){
                tasks++;
                log.info("("+getSelf().path().name()+") received task from ("+ getSender().path().name()+ ")");
            }
            else if(msg.equals("finishAll")){
                tasks=0;
                loadBalancer.tell("finishedAllTasks",getSelf());
            }
            else {
                tasks--;
                if(tasks<=0){
                    loadBalancer.tell("finishedALLTasks",getSelf());
                } else {
                    log.info("("+getSelf().path().name()+") finished task, they remain"+ Integer.toString(tasks));
                    loadBalancer.tell("finishedTask",getSelf());
                }
            }
        }
	}

} 