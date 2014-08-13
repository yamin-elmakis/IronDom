package IronDome;

import IronDome.Controller.TzoukEitanController;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.TzoukEitan;
import IronDome.Utils.Utils;
import IronDome.View.ConsoleView;
import IronDome.View.ITzoukEitanView;

public class IronDomeMain {

	public static void main(String[] args) {
		// the model
		TzoukEitan tzoukEitan = new TzoukEitan();
		
		//the view
		ITzoukEitanView tzoukEitanView = new ConsoleView();
		
		// the controller
		TzoukEitanController tzoukEitanController = new TzoukEitanController(tzoukEitan, tzoukEitanView);
	}

}
