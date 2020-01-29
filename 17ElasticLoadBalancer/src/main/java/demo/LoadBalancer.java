package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;

public class LoadBalancer extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef[] receiverList;
    public int i,j;
    public int max;
    public int round;

	public LoadBalancer(int max) {
        i = 0;
        j = 0;
        round = 0;
        this.max = max;
        receiverList = new ActorRef[max];
    }

	// Static function creating actor
	public static Props createActor(int max) {
		return Props.create(LoadBalancer.class, () -> {
			return new LoadBalancer(max);
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		
		if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("finishedAllTasks")){
                getContext().stop(getSender());
                i--;
                log.info("("+getSender().path().name()+") closed ");
            }
            else if(msg.equals("newTask")){
                if(i<max){
                receiverList[i] = getContext().actorOf(Receiver.createActor(getSelf()), "receiver" + Integer.toString(i));;
                log.info("("+getSelf().path().name()+") created receiver ("+ "receiver" + Integer.toString(i)+ ")");
                receiverList[i].tell("newTask", getSelf());           
                i++;     
                } 
                else {
                    receiverList[round].tell("newTask", getSelf());
                    if(round<max)
                        round++;
                    else round = 0;
                }
            }
            else if(msg.equals("main")){
                    receiverList[j].tell("finishTasks", getSelf()); 
                    if(j<max)
                        j++;
                    else j = 0;
                }
            else if(msg.equals("main2")){
                    receiverList[j].tell("finishAll", getSelf()); 
                }
            }
        }
}

