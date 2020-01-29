package demo;

import akka.actor.ActorRef;
import java.util.*;

public class MyMessage {
	public final String data;
    public final ArrayList<ActorRef> mergeList;

	public MyMessage(String data, ArrayList<ActorRef> mergeList) {
		this.data = data;
        this.mergeList = mergeList;
	}
}