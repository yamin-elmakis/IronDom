package IronDome.xmlParser;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public class XMLParser {

	private List<ITzoukEitanViewEventsListener> allListeners;
	private final String XML_FILE_NAME = "warconfiguration.xml"; 
	
	public XMLParser(){
		allListeners = new Vector<ITzoukEitanViewEventsListener>();
	}
	
	public void parseXml(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// TODO move to after register
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(XML_FILE_NAME);
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

	public void registerController(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}
	
	private void getLauncherFromXML(Document doc){
		NodeList LaunchersList = doc.getElementsByTagName("launcher");

		for (int i=0 ; i<LaunchersList.getLength() ; i++){
			Node LauncherNode = LaunchersList.item(i);
			if (LauncherNode.getNodeType() == Node.ELEMENT_NODE){
				Element launcher = (Element) LauncherNode;
				
				fireAddLauncherEvent(launcher.getAttribute("id"), Boolean.getBoolean(launcher.getAttribute("isHidden")));
				getMissileFromXML(launcher);
			} 
		}
	}
	
	private void getMissileFromXML(Element launcher){
		NodeList missileList = launcher.getChildNodes();
		for (int j=0 ; j<missileList.getLength() ; j++){
			Node m = missileList.item(j);
			if (m.getNodeType() == Node.ELEMENT_NODE){
				Element missile = (Element) m;
				
				try {
					fireLaunchMissileEvent(launcher.getAttribute("id"), missile.getAttribute("id"), 
							Destination.valueOf(missile.getAttribute("destination")), 
							Integer.parseInt(missile.getAttribute("launchTime")), 
							Integer.parseInt(missile.getAttribute("flyTime")), 
							Integer.parseInt(missile.getAttribute("damage")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void getDestructorFromXML(Document doc){
		NodeList destructorsList = doc.getElementsByTagName("destructor");

		for (int i=0 ; i<destructorsList.getLength() ; i++){
			Node destructorNode = destructorsList.item(i);
			if (destructorNode.getNodeType() == Node.ELEMENT_NODE){
				Element destructor = (Element) destructorNode;
				fireAddMissileDestructorEvent(destructor.getAttribute("id"));
				getMissileForDestructorFromXML(destructor);
			} 
		}
	}
	
	private void getMissileForDestructorFromXML(Element destructor) {
		NodeList missileList = destructor.getChildNodes();
		for (int j=0 ; j<missileList.getLength() ; j++){
			Node m = missileList.item(j);
			if (m.getNodeType() == Node.ELEMENT_NODE){
				Element missile = (Element) m;
				
				try {
					fireLaunchMissileFromDestructorEvent(missile.getAttribute("id"), Integer.parseInt(missile.getAttribute("destructAfterLaunch")));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getmissileLauncherDestructorsFromXML(Document doc){
		NodeList missileLauncherDestructorsList = doc.getElementsByTagName("destructor");

		for (int i=0 ; i<missileLauncherDestructorsList.getLength() ; i++){
			Node destructorNode = missileLauncherDestructorsList.item(i);
			if (destructorNode.getNodeType() == Node.ELEMENT_NODE){
				Element destructor = (Element) destructorNode;
				
				fireAddLauncherDestructorEvent(DestructorType.valueOf(destructor.getAttribute("type")));
				getDestructedLanuchersFromXML(destructor);
			} 
		}
	}
	
	private void getDestructedLanuchersFromXML(Element destructor){
		NodeList destructedLanuchersList = destructor.getChildNodes();
		for (int j=0 ; j<destructedLanuchersList.getLength() ; j++){
			Node dl = destructedLanuchersList.item(j);
			if (dl.getNodeType() == Node.ELEMENT_NODE){
				Element destructedLanucher = (Element) dl;
				
				try {
					fireDestructedLanucherEvent(destructedLanucher.getAttribute("id"),DestructorType.valueOf(destructor.getAttribute("type")), Integer.parseInt(destructedLanucher.getAttribute("destructTime")));
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void fireDestructedLanucherEvent(String Lid,DestructorType type, int destructTime){
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyLauncher(Lid, type, destructTime);;
		}
	}
	
	private void fireLaunchMissileFromDestructorEvent(String Mid, int destructAfterLaunch){
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyMissile(Mid,destructAfterLaunch);
		}
	}
	
	private void fireLaunchMissileEvent(String Lid,String Mid, Destination destination, int launchTime, int flyTime, int damage) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.LaunchMissile(Lid, Mid, destination, launchTime, flyTime, damage);
		}	
	}

	private void fireAddMissileDestructorEvent(String MDid) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileDestructor(MDid);
		}
	}

	private void fireAddLauncherDestructorEvent(DestructorType type) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileLauncherDestructor(type);
		}
	}

	private void fireAddLauncherEvent(String Lid, boolean isHidden){
		//TODO add parameters for adding launcher
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addLauncher(Lid, isHidden);
		}
	}
}

