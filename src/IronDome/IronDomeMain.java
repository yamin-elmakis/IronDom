package IronDome;

import IronDome.Controller.TzoukEitanController;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.TzoukEitan;
import IronDome.Utils.Utils;

public class IronDomeMain {

	public static void main(String[] args) {
		// the model
		TzoukEitan tzoukEitan = new TzoukEitan();
		
		//the view
		ITzoukEitanViewEventsListener iromDomeView;
		
		// the controller
		TzoukEitanController tzoukEitanController = new TzoukEitanController();
	}

}
