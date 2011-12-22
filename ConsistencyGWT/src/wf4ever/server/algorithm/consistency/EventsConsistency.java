package wf4ever.server.algorithm.consistency;

import java.util.ArrayList;

import wf4ever.server.algorithm.Events.*;

public class EventsConsistency {
	
	private ArrayList<Event> events;
	private ConsistencyObjects co;
	
	public EventsConsistency(ArrayList<Event> e){
		events=e;
		co=new ConsistencyObjects();
		setEvents();
	}

	private void setEvents() {
		for (int i=0; i<events.size(); i++)
			co.addEvent(events.get(i));	
	}

	public ArrayList<Double> getConsistencyVector(){
		return co.getConsistencyVector();
	}

}
