package wf4ever.server.algorithm.consistency;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import wf4ever.server.algorithm.knowledge.*;
import wf4ever.server.algorithm.Enums.*;
import wf4ever.server.algorithm.Events.*;

public class ConsistencyObjects {
	
	private Hashtable<String,ArrayList<Double>> co;
	private ArrayList<Double> consistencyVector;
	
	
	public ConsistencyObjects(){
		co=new Hashtable<String, ArrayList<Double>>();
		consistencyVector= new ArrayList<Double>();
	}
	
	public void addEvent(Event e){
		double value= getValue(e.getUser(), e.getAction(), e.getImpact());

		switch (e.getAction()){
		case CREATE: addCreate(e.getId(), value); break;
		case ADD: addAdd(e.getId(), value); break;
		case REMOVE: addAdd(e.getId(), value); break; //it does exactly the same
		case DELETE: addDelete(e.getId());break;
		case GENERATE: addGenerate(e.getId(), e.getSources());break;
		}
		
		consistencyVector.add(getFullConsistency());
	}


//////////////////// Consistency	
	public double getFullConsistency(){
		double acum=0;
		String e;
		int numOfElements=co.size();
		
		Enumeration<String> elements=co.keys();
		while (elements.hasMoreElements()){
			e=elements.nextElement();
			acum=acum+getObjectConsistency(e);
		}
		return acum/numOfElements;
	}
	
	//need to refactor
	/*public ArrayList<Double> getFullConsistencyVector(){
		ArrayList<Double> vector=new ArrayList<Double>();
		double acum=0;
		Enumeration<String> elements=co.keys();
		int numOfElements=co.size();
		String e;
		while (elements.hasMoreElements()){
			e=elements.nextElement();
			vector.add(getObjectConsistency(e));
			acum=acum+getObjectConsistency(e);	
		}
		vector.add(acum/numOfElements);
		return vector;
	}*/
	
	public ArrayList<Double> getConsistencyVector(){
		return consistencyVector;
	}
	
	private double getObjectConsistency(String id){
		if (co.containsKey(id))
			return co.get(id).get(co.get(id).size()-1);
		
		return 0;
	}
	

////////////////////////////// Calculations

	private double getValue(Users user, Actions action, Impact impact) {
		double value=ActionUserTable.getInstance().getValueActionUser(action, user); 
		if (impact!=null)
			value=value*ImpactTable.getInstance().getImpactValue(impact);
		
		return value;
	}

	
/////////////////////////// Actions
	private void addCreate(String id, double value){
		if (!co.containsKey(id)){
			ArrayList<Double> list=new ArrayList<Double>();
			list.add(value);
			co.put(id,list);
		}
		// else Exception because it's replicated
	}
	
	private void addAdd(String id, double value) {
		if (co.containsKey(id)){
			//ArrayList of events
			ArrayList<Double> list=co.get(id);
			//position of the last value
			int pos=list.size()-1;
			//last value
			double last=list.get(pos);
			
			//adding
			list.add(last+last*value/100);
		}
		//else Exception, id not found
	}
	
	private void addDelete(String id) {
		if (co.containsKey(id))
			co.remove(id);
		//else Exception, id not found
	}
	
	private void addGenerate(String id, ArrayList<String> sources) {
		double acum=0;
		for (String s: sources){
			if(co.containsKey(s))
				acum=acum+getObjectConsistency(s);
		}
		
		//check the RO element existency
		ArrayList<Double> list;
		if (!co.containsKey(id)){
			list=new ArrayList<Double>();
			co.put(id, list);
		}
			else
				list=co.get(id);
		
		list.add(acum/sources.size());
				
	}


	

}
