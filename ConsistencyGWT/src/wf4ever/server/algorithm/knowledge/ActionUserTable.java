package wf4ever.server.algorithm.knowledge;

import wf4ever.server.algorithm.Enums.*;


public class ActionUserTable {
	
	private static ActionUserTable instance = null;
	private double[][] table;
	
	
	
	private ActionUserTable(){
		table = new double[Users.values().length][Actions.values().length];
		
		//actions
		int create=Actions.CREATE.ordinal();
		int add=Actions.ADD.ordinal();
		int remove=Actions.REMOVE.ordinal();
		//int delete=Actions.DELETE.ordinal();
		
		//users
		int owner=Users.OWNER.ordinal();
		int trusted=Users.TRUSTED.ordinal();
		int collab=Users.COLLABORATOR.ordinal();
		int untrusted=Users.UNTRUSTED.ordinal();
		
		//OWNER
		table[owner][create]=55;
		table[owner][add]=2;
		table[owner][remove]=1;
		//table[owner][delete]=
		
		//TRUSTED
		table[trusted][create]=50;
		table[trusted][add]=1;
		table[trusted][remove]=0;
		//table[trusted][delete]=
		
		//COLLABORATOR
		table[collab][create]=45;
		table[collab][add]=0;
		table[collab][remove]=-1;
		//table[collab][delete]=
		
		//UNTRUSTED
		table[untrusted][create]=35;
		table[untrusted][add]=-1;
		table[untrusted][remove]=-2;
		//table[untrusted][delete]=
	}
	
	public static ActionUserTable getInstance(){
		if (instance==null)
			instance=new ActionUserTable();
		return instance;
	}
	
	public double getValueActionUser(Actions action, Users user){
		if (action==Actions.DELETE)return 0;
		return table[user.ordinal()][action.ordinal()];
	}

}
