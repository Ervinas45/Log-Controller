package manager;

import java.util.ArrayList;

public class ActionListeners {

	public ActionListeners(){
		
	}
	
	public static void filterButtonPressed(ArrayList titles, ArrayList projects) {
		new FilterWindow(titles, projects);
		System.out.println("Pressed Filter");
		
	}
}
