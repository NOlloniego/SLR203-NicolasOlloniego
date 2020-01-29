package demo;

import akka.actor.ActorRef;
import java.util.*;

public class Group {
    public final String name;
    public final ArrayList<ActorRef> members;

    public Group(String name, ArrayList<ActorRef> members){
        this.name = name;
        this.members = members;
    }
}