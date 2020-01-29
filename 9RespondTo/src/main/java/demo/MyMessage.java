package demo;

import akka.actor.ActorRef;

public class MyMessage {
	public final String data;
    public ActorRef receiver;

	public MyMessage(String data, ActorRef receiver) {
		this.data = data;
        this.receiver = receiver;
	}
}