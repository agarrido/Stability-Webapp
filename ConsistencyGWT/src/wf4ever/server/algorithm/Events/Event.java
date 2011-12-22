package wf4ever.server.algorithm.Events;

import java.util.ArrayList;

import wf4ever.server.algorithm.Enums.*;

public class Event {

	private String id;
	private Users user;
	private Actions action;
	private Impact impact;
	private ArrayList<String> sources;
	//private time
	//private type...
	
	public Event(String id, Users user, Actions action, Impact impact){
		this.id=id;
		this.user=user;
		this.action=action;
		this.impact=impact;
	}
	
	public void setSource(String id){
		if (sources==null)
			sources=new ArrayList<String>();
		//avoid duplication
		if (!sources.contains(id))
			sources.add(id);
	}
	
	public String getId() {
		return id;
	}

	public Users getUser() {
		return user;
	}

	public Actions getAction() {
		return action;
	}

	public Impact getImpact() {
		return impact;
	}
	
	public ArrayList<String> getSources(){
		return sources;
	}
}
