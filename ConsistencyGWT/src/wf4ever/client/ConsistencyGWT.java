package wf4ever.client;

import java.util.ArrayList;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConsistencyGWT implements EntryPoint {

	//private ArrayList<String> elements = new ArrayList<String>();
	private ArrayList<String> trace = new ArrayList<String>();

	private FlexTable table = new FlexTable();
	private TextBox id = new TextBox();
	private TextBox s1 = new TextBox();
	private TextBox s2 = new TextBox();
	private ListBox lbUsers = new ListBox();
	private ListBox lbActions = new ListBox();
	private ListBox lbImpact = new ListBox();
	private Button bAdd = new Button("Add to the trace");
	private Button bGenerate = new Button("Generate graph");
	private TextArea tTrace = new TextArea();
	
	private HorizontalPanel hPanel=new HorizontalPanel();
	private VerticalPanel vPanel=new VerticalPanel();
	
	

	private ConsistencyChart chart = new ConsistencyChart();

	private enum Users {
		OWNER, TRUSTED, COLLABORATOR, UNTRUSTED
	}

	private enum Actions {
		CREATE, ADD, REMOVE, DELETE, GENERATE
	}

	private enum Impact {
		MIN, MED, MAX

	}

	public void onModuleLoad() {
		// Lista de usuarios
		for (Users u : Users.values()) {
			lbUsers.addItem(u.toString());
		}
		lbUsers.setVisibleItemCount(1);

		// Lista de acciones
		for (Actions a : Actions.values()) {
			lbActions.addItem(a.toString());
		}
		lbActions.setVisibleItemCount(1);

		// Lista de impactos
		for (Impact i : Impact.values()) {
			lbImpact.addItem(i.toString());
		}
		lbImpact.setVisibleItemCount(1);
		lbImpact.setVisible(false);
		lbImpact.setVisible(false);

		s1.setVisible(false);
		s2.setVisible(false);
		s1.setEnabled(false);
		s2.setEnabled(false);

		//panel3.add(bAdd);
		
		
		
		//panelV1.add(tTrace);

		lbActions.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				s1.setEnabled(false);
				s2.setEnabled(false);
				s1.setVisible(false);
				s2.setVisible(false);
				lbImpact.setEnabled(false);
				lbImpact.setVisible(false);
				table.setText(2, 0, "");
				table.setText(2, 1, "");
				table.setText(2, 2, "");
				switch (Actions.valueOf(lbActions.getItemText(lbActions
						.getSelectedIndex()))) {
				case ADD: {
					table.setText(2, 2, "Impact");
					lbImpact.setEnabled(true);
					lbImpact.setVisible(true);
				}
					break;
				case REMOVE: {
					table.setText(2, 2, "Impact");
					lbImpact.setEnabled(true);
					lbImpact.setVisible(true);
				}
					break;
				case GENERATE: {
					table.setText(2, 0, "Source 1");
					table.setText(2, 1, "Source 2");
					s1.setEnabled(true);
					s2.setEnabled(true);
					s1.setVisible(true);
					s2.setVisible(true);
				}
					;
					break;
				}

			}

		});
		
		ordena();

		// Listen mouse events on Add button
		bAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addToTrace();
				addToEventList();
			}
		});
		
		bGenerate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				plotConsistency();
			}
		});
		
		tTrace.setCharacterWidth(60);
	    tTrace.setVisibleLines(25);
	    tTrace.setReadOnly(true);
		
		vPanel.add(table);
		vPanel.add(chart);
		hPanel.add(vPanel);
		hPanel.add(tTrace);
		RootPanel.get("blabla").add(hPanel);

	}

	private void addToEventList(){
		String aux=id.getText()+ "+";
		aux= aux + lbUsers.getItemText(lbUsers.getSelectedIndex()).charAt(0)+"+";
		aux= aux + lbActions.getItemText(lbActions.getSelectedIndex()).charAt(0)+"+";
		
		if (lbImpact.isVisible())
		aux= aux + lbImpact.getItemText(lbImpact.getSelectedIndex()).charAt(2);
		
		if (s1.isVisible()){
			aux=aux+ s1.getText()+ "+";
			aux= aux+ s2.getText();
		}
		trace.add(aux);
	}

	private void ordena() {
		table.getCellFormatter().addStyleName(0,0,"Table-Cell");
		table.setText(0, 0, "ID:");
		table.setText(0, 1, "Author");
		table.setText(0, 2, "Action");
		table.setWidget(1, 0, id);
		table.setWidget(1, 1, lbUsers);
		table.setWidget(1, 2, lbActions);
		table.setWidget(3, 2, lbImpact);
		table.setWidget(3, 0, s1);
		table.setWidget(3, 1, s2);
		table.setWidget(4,2, bAdd);
		table.setWidget(5,2, bGenerate);
		table.getCellFormatter().addStyleName(0,0,"Table-Cell");
		table.getCellFormatter().addStyleName(0,1,"Table-Cell");
		
		
	}

	private void addToTrace() {
		String ident = id.getText();
		String u = lbUsers.getItemText(lbUsers.getSelectedIndex());
		String a = lbActions.getItemText(lbActions.getSelectedIndex());
		String i = null;
		String source1= null;
		String source2= null;
		
		// si está enabled..........
		if (lbImpact.isVisible())
		i = lbImpact.getItemText(lbImpact.getSelectedIndex());
		
		
		if (s1.isVisible()){
			source1= s1.getText();
			source2= s2.getText();
		}
		
		String aux= ident + " " + u +" "+ a;
		if (i!=null)aux = aux + " " + i;
		if (source1!=null)aux= aux + " from "+ source1 + " and " + source2;
		tTrace.setText(tTrace.getText() + "\n" + aux);

	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	 /**
	   * Convert the string of JSON into JavaScript object.
	   */
	  private final native JsArray<XY> asArrayXY(String json) /*-{
	    return eval(json);
	  }-*/;
	  

	  private static final String JSON_URL = GWT.getModuleBaseURL() + "consistency?q=";
	  
	  private void plotConsistency(){
		  String url = JSON_URL;
		  
		  for (int i=0; i<trace.size(); i++){
			  url=url+trace.get(i)+"$";
		  }
		  
		  url = URL.encode(url);
		  
		// Send request to server and catch any errors.
		    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		    try {
			      Request request = builder.sendRequest(null, new RequestCallback() {
			        public void onError(Request request, Throwable exception) {
			          id.setText("Couldn't retrieve JSON");
			        }

			        public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode()) {
			            plotter(asArrayXY(response.getText()));
			          } else {
			        	  //id.setText("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			          }
			        }
			      });
			    } catch (RequestException e) {
			    	 //id.setText("Couldn't retrieve JSON");
			    }	    
	  }
	  
	  private void plotter(JsArray<XY> values){
		  //String dame="";
		  chart.reset();
		  
		  for (int i = 0; i < values.length(); i++){
			  double x=values.get(i).getX();
			  double y=values.get(i).getY();
			  chart.addPoint(x, y);
			  //dame=dame+values.get(i).getX()+values.get(i).getY();
		  }
			
		  chart.update();
		  //id.setText(dame);	  
	  }
}
