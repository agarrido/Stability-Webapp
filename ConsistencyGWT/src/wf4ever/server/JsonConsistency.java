package wf4ever.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wf4ever.server.algorithm.Enums.Actions;
import wf4ever.server.algorithm.Enums.Impact;
import wf4ever.server.algorithm.Enums.Users;
import wf4ever.server.algorithm.Events.Event;
import wf4ever.server.algorithm.consistency.EventsConsistency;

public class JsonConsistency extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		EventsConsistency events=new EventsConsistency(params(req));
		ArrayList<Double> cons=events.getConsistencyVector();
		response(resp,cons);		
	}

	private void response(HttpServletResponse resp, ArrayList<Double> cons) throws IOException {
		PrintWriter out = resp.getWriter();
	    out.println('[');
	    
	    for (int i=0; i<cons.size(); i++) {
	      out.println("  {");
	      out.print("    \"x\": ");
	      out.print(i);
	      out.println(",");
	      out.print("    \"y\": ");
	      out.println(cons.get(i));
	      out.println("  },");
	    }
	    
	    out.println(']');
	    out.flush();	
	}

	private ArrayList<Event> params(HttpServletRequest req) {
		ArrayList<Event> events= new ArrayList<Event>();
		
		String[] stockSymbols = req.getParameter("q").split("\\$");
		 for (String stockSymbol : stockSymbols){
		    	String[] stock2 = stockSymbol.split(" ");
		    	String id=stock2[0];
		    	Users u= getUser(stock2[1]);
		    	Actions a=getAction(stock2[2]);
		    	Impact i=null;
		    	if (a==Actions.REMOVE || a==Actions.ADD) i=getImpact(stock2[3]);
		    	
		    	Event e=new Event(id, u, a, i);
		    	if (a==Actions.GENERATE){
		    		e.setSource(stock2[3]);
		    		e.setSource(stock2[4]);
		    	}
		    	events.add(e);
		 }
		
		return events;
	}

	private Users getUser(String s) {
		if (s.equals("O")) return Users.OWNER;
		if (s.equals("T")) return Users.TRUSTED;
		if (s.equals("C")) return Users.COLLABORATOR;
		if (s.equals("U")) return Users.UNTRUSTED;
		return null;
	}
	
	private Actions getAction(String s) {
		if (s.equals("C")) return Actions.CREATE;
		if (s.equals("R")) return Actions.REMOVE;
		if (s.equals("G")) return Actions.GENERATE;
		if (s.equals("A")) return Actions.ADD;
		if (s.equals("D")) return Actions.DELETE;
		return null;
	}
	
	private Impact getImpact(String s) {
		if (s.equals("N")) return Impact.MIN;
		if (s.equals("D")) return Impact.MED;
		if (s.equals("X")) return Impact.MAX;
		return null;
	}
	

}
