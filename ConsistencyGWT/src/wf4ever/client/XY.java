package wf4ever.client;

import com.google.gwt.core.client.JavaScriptObject;

public class XY extends JavaScriptObject{

	protected XY(){};
	
	// JSNI methods to get stock data.
	public final native double getX() /*-{ return this.x; }-*/;
	public final native double getY() /*-{ return this.y; }-*/;
	
	
	
/*	private double x;
	private double y;
	
	public XY (double x, double y){
		this.x=x;
		this.y=y;
	}
	
	public void setX(double x){
		this.x=x;
	}
	
	public void setY(double y){
		this.y=y;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	*/
	
	

}
