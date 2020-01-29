package demo;

import akka.actor.ActorRef;
import java.util.*;

public class MyMessage {
	public final String groupName;
    public final String message;

	public MyMessage(String groupName, String message) {
		this.groupName = groupName;
        this.message = message;
	}
}