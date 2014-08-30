package IronDome;

import java.io.IOException;

import IronDome.controller.TzoukEitanController;
import IronDome.model.TzoukEitan;
import IronDome.view.ConsoleView;
import IronDome.xmlParser.XMLParser;

public class IronDomeMain {

	public static void main(String[] args) throws SecurityException, IOException {
		// the model
		TzoukEitan tzoukEitan = new TzoukEitan();
		
		//the view
		ConsoleView tzoukEitanView = new ConsoleView();
		
		// the xml parser
		XMLParser xmlParser = new XMLParser();
		
		// the controller
		TzoukEitanController tzoukEitanController = new TzoukEitanController(tzoukEitan, tzoukEitanView, xmlParser);
		
//		System.exit(0);
	}
}
