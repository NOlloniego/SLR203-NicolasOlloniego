package demo;

import akka.actor.ActorRef;
import java.util.*;

public class MessageGroup {
	public final String groupName;
    public final ArrayList<ActorRef> groupList;

	public MessageGroup(String data, ArrayList<ActorRef> groupList) {
		this.groupName = data;
        this.groupList = groupList;
	}
}