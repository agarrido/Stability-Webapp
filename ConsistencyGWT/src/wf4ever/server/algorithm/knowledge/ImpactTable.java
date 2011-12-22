package wf4ever.server.algorithm.knowledge;

import wf4ever.server.algorithm.Enums.*;

public class ImpactTable {
	
	private static ImpactTable instance = null;
	private double[] table;
	
	private ImpactTable(){
		table = new double[Impact.values().length];
		
		//Impact values
		int min=Impact.MIN.ordinal();
		int med=Impact.MED.ordinal();
		int max=Impact.MAX.ordinal();
		
		//FILL THE TABLE
		table[min]=1;
		table[med]=3;
		table[max]=5;	
	}
	
	public static ImpactTable getInstance(){
		if (instance==null)
			instance=new ImpactTable();
		return instance;
	}

	public double getImpactValue(Impact impact){
		return table[impact.ordinal()];
	}
}
