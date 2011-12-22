package wf4ever.client;

import com.googlecode.gchart.client.GChart;

public class ConsistencyChart extends GChart{
	
	public ConsistencyChart() {
		reset();
	}
 
	public void addPoint(double x, double y) {
		getCurve().addPoint(x, y);
	}

	public void reset() {
		setChartSize(240, 200);
		setPadding("4px");
		getXAxis().setAxisLabel("Steps");
		getYAxis().setAxisLabel("Consistency");
		this.addCurve();
		// 2 pixel square connecting dots, spaced 5 pixels apart:
		getCurve().getSymbol().setSymbolType(SymbolType.LINE);
	    getCurve().getSymbol().setFillThickness(2);
	    getCurve().getSymbol().setFillSpacing(1);
		/*// solid, 2px thick, 1px resolution, connecting lines:
	    getCurve().getSymbol().setSymbolType(SymbolType.LINE);
	    getCurve().getSymbol().setFillThickness(2);
	    getCurve().getSymbol().setFillSpacing(1);*/
	    // Make center-fill of square point markers same color as line:
	     getCurve().getSymbol().setBackgroundColor(getCurve().getSymbol().getBorderColor());
	}

}
