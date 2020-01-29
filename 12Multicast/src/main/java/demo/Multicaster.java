package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MessageGroup;
import demo.Group;
import demo.MyMessage;

public class Multicaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ArrayList<Group> groupList;

	public Multicaster() {
       groupList = new ArrayList<Group>();
    }

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Multicaster.class, () -> {
			return new Multicaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageGroup){
            MessageGroup msg = (MessageGroup) message;
            Group grp = new Group(msg.groupName, msg.groupList);    //Creates group
            groupList.add(grp);
            log.info("("+getSelf().path().name()+") added group ("+ msg.groupName +")");
        }
        if (message instanceof MyMessage){
            int z=0;
            Group grp2;
            MyMessage msg2 = (MyMessage) message;
            Iterator<Group> it = groupList.iterator();
            while(it.hasNext() && z == 0){  
                grp2 = (Group) it.next();
                if(grp2.name.equals(msg2.groupName)){
                    z=1;
                    Iterator<ActorRef> i = grp2.members.iterator();
                    while(i.hasNext()){
                        ActorRef element = i.next();
                        element.tell(msg2.message, getSender());    //Sends message to group members
                    }
                }
            }
        }
	}

}