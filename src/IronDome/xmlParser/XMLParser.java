package IronDome.xmlParser;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.model.MissileDestructor;
import IronDome.model.MissileLauncherDestructor;
import IronDome.model.TzoukEitan;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public class XMLParser {

	
	private List<ITzoukEitanViewEventsListener> allListeners;
	
	public static void XMLParser(){

		// TODO instead of singleton you can get TzoukEitan in the constructor
		// and instantiate parseXML after TzoukEitan
 		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("warconfiguration.xml");
			getLauncherFromXML(doc);
			getDestructorFromXML(doc);
		}catch (ParserConfigurationException e) {
			System.out.println(e);

		} catch (SAXException e1) {
			System.out.println(e1);

		} catch (IOException e2) {
			System.out.println(e2);
		}

	}

	private static void getLauncherFromXML(Document doc){
		NodeList LaunchersList = doc.getElementsByTagName("launcher");

		for (int i=0 ; i<LaunchersList.getLength() ; i++){
			Node n = LaunchersList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE){
				Element launcher = (Element) n;
				
				
				System.out.println(launcher.toString());
				//addLauncher(launcher.getAttribute("id"), Boolean.getBoolean(launcher.getAttribute("isHidden")));
			} 
		}
	}

	private static void getDestructorFromXML(Document doc){
		NodeList dstructorsList = doc.getElementsByTagName("destructor");

		for (int i=0 ; i<dstructorsList.getLength() ; i++){

			Node n = dstructorsList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE){

				Element destructor = (Element) n;

//				if (destructor.getAttribute("type").equals("plane")){
//					MissileLauncherDestructor d = new MissileLauncherDestructor(destructor.getAttribute("id"),MissileLauncherDestructor.Type.plane);
//					getLaunchersToDestroyXML( doc,  destructor,  d);
//					tzoukEitan.addMissileLauncheDestructor();//addDestructor(d);
//				} else if (destructor.getAttribute("type").equals("ship")){
//					MissileLauncherDestructor d = new MissileLauncherDestructor(destructor.getAttribute("id"),MissileLauncherDestructor.Type.ship);
//					getLaunchersToDestroyXML( doc,  destructor,  d);
//					tzoukEitan.addMissileLauncheDestructor();//(d);
//				} else { // missile destructor
//					MissileDestructor md = new MissileDestructor(destructor.getAttribute("id"), getMissilesToDestroyXML( doc, destructor,  md););
					//getMissilesToDestroyXML( doc,  destructor,  md);
//					TzoukEitan.addDestructor(md);
//				}
			} 
		}
	}

	private static void getMissileFromXML(Document doc, Element launcher, Launcher theLauncher){
		NodeList missileList = launcher.getChildNodes();
		for (int j=0 ; j<missileList.getLength() ; j++){
			Node m = missileList.item(j);
			if (m.getNodeType() == Node.ELEMENT_NODE){
				Element missile = (Element) m;
				String mid = missile.getAttribute("id");
				String mdestination = missile.getAttribute("destination");
				int mlaunchTime = Integer.parseInt(missile.getAttribute("launchTime"));
				int mflyTime = Integer.parseInt(missile.getAttribute("flyTime"));
				int mdamage = Integer.parseInt(missile.getAttribute("damage"));
				Missile mis = new Missile(mid, mflyTime, mdamage, Destination.Azor,theLauncher); 
				//TODO change destination to string or find a solution for this
				theLauncher.loadMissile(mid, mflyTime, mdamage, Destination.Azor); 
			}
		}
	}

//	private static ArrayDeque<Missile> getMissilesToDestroyXML(Document doc,Element destructor, MissileDestructor md) {
//
//		NodeList destructdMissileList = destructor.getChildNodes();
//		for (int j=0 ; j<destructdMissileList.getLength() ; j++){
//			Node dm = destructdMissileList.item(j);
//			if (dm.getNodeType() == Node.ELEMENT_NODE){
//				Element destructdMissile = (Element) dm;
//				String dmid = destructdMissile.getAttribute("id");
//				//TODO do something with that value
//				int destructAfterLaunch = Integer.parseInt(destructdMissile.getAttribute("destructAfterLaunch"));
//				Missile m = tzoukEitan.getMissileByid(dmid);
//				md.addMissileToDestruct(m, destructAfterLaunch);
//			}
//		}
//	}

//	private static void getLaunchersToDestroyXML(Document doc,Element destructor, LauncherDestructor d) {
//		NodeList destructdLaunchersList = destructor.getChildNodes();
//		for (int j=0 ; j<destructdLaunchersList.getLength() ; j++){
//			Node dm = destructdLaunchersList.item(j);
//			if (dm.getNodeType() == Node.ELEMENT_NODE){
//				Element destructdLauncher = (Element) dm;
//				String dlid = destructdLauncher.getAttribute("id");
//				// TODO do something with that value
//				int destructTime = Integer.parseInt(destructdLauncher.getAttribute("destructTime"));
//				Launcher l = TzoukEitan.getLauncherByid(dlid);
//				d.addWaitingLauncherss(l,destructTime);
//			}
//		}
//
//	}
	
	private void fireLaunchMissileEvent(String Lid,String Mid, Destination destination, int launchTime, int flyTime, int damage) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.LaunchMissile(Lid, Mid, destination, launchTime, flyTime, damage);
		}	
	}

	private void fireAddMissileDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileDestructor();
		}
	}

	private void fireAddLauncherDestructorEvent(String MDid) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileLauncherDestructor(MDid);
		}
	}

	private void fireAddLauncherEvent(String Lid, boolean isHidden){
		//TODO add parameters for adding launcher
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addLauncher(Lid, isHidden);
		}
	}
	
	

}

