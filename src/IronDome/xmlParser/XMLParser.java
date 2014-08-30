package IronDome.xmlParser;

import java.io.IOException;
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
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;
import IronDome.utils.Utils;

public class XMLParser {

	private List<ITzoukEitanViewEventsListener> allListeners;
	private final String XML_FILE_NAME = "warconfiguration.xml"; 
	
	public XMLParser(){

 		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(XML_FILE_NAME);
			getLauncherFromXML(doc);
			getDestructorFromXML(doc);
			getmissileLauncherDestructorsFromXML(doc);
		}catch (ParserConfigurationException e) {
			System.out.println(e);

		} catch (SAXException e1) {
			System.out.println(e1);

		} catch (IOException e2) {
			System.out.println(e2);
		}
	}
	
	public void parseXml(){
		
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
				System.out.println(launcher.toString());
				fireAddLauncherEvent(launcher.getAttribute("id"), Boolean.getBoolean(launcher.getAttribute("isHidden")));
				getMissileFromXML(launcher);
			} 
		}
	}
	
	private void getMissileFromXML(final Element launcher){
		NodeList missileList = launcher.getChildNodes();
		for (int j=0 ; j<missileList.getLength() ; j++){
			Node m = missileList.item(j);
			if (m.getNodeType() == Node.ELEMENT_NODE){
				final Element missile = (Element) m;
				System.out.println(missile.toString());
			
					final int launchTime = Integer.parseInt(missile.getAttribute("launchTime")); 
					Thread t = new Thread() {
					    public void run() {
					    	try {
								sleep(launchTime*Utils.SECONDS); //turned to milliseconds
							} catch (InterruptedException e) {
								e.printStackTrace();
							} 
					    	fireLaunchMissileEvent(launcher.getAttribute("id"), missile.getAttribute("id"), 
									Destination.valueOf(missile.getAttribute("destination")), 
									Integer.parseInt(missile.getAttribute("flyTime")), 
									Integer.parseInt(missile.getAttribute("damage")));
					    }
					};
					t.start();
			}
		}
	}

	private void getDestructorFromXML(Document doc){
		NodeList destructorsList = doc.getElementsByTagName("destructor");

		for (int i=0 ; i<destructorsList.getLength() ; i++){
			Node destructorNode = destructorsList.item(i);
			if (destructorNode.getNodeType() == Node.ELEMENT_NODE){
				Element destructor = (Element) destructorNode;
				System.out.println(destructor.toString());
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
				final Element missile = (Element) m;
				System.out.println(missile.toString());
				final int destructAfterLaunch = Integer.parseInt(missile.getAttribute("destructAfterLaunch"));
				Thread t = new Thread() {
				    public void run() {
				    	try {
							sleep(destructAfterLaunch*Utils.SECONDS); //turned to milliseconds
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
				    	fireLaunchMissileFromDestructorEvent(missile.getAttribute("id"));	
				    }
				};
				t.start();
			}
		}
	}

	private void getmissileLauncherDestructorsFromXML(Document doc){
		NodeList missileLauncherDestructorsList = doc.getElementsByTagName("destructor");

		for (int i=0 ; i<missileLauncherDestructorsList.getLength() ; i++){
			Node destructorNode = missileLauncherDestructorsList.item(i);
			if (destructorNode.getNodeType() == Node.ELEMENT_NODE){
				Element destructor = (Element) destructorNode;
				System.out.println(destructor.toString());
				fireAddLauncherDestructorEvent(DestructorType.valueOf(destructor.getAttribute("type")));
				getDestructedLanuchersFromXML(destructor);
			} 
		}
	}
	
	private void getDestructedLanuchersFromXML(final Element destructor){
		NodeList destructedLanuchersList = destructor.getChildNodes();
		for (int j=0 ; j<destructedLanuchersList.getLength() ; j++){
			Node dl = destructedLanuchersList.item(j);
			if (dl.getNodeType() == Node.ELEMENT_NODE){
				final Element destructedLanucher = (Element) dl;
				System.out.println(destructedLanucher.toString());
				
				final int destructTime= Integer.parseInt(destructedLanucher.getAttribute("destructTime"));
				Thread t = new Thread() {
				    public void run() {
				    	try {
							sleep(destructTime*Utils.SECONDS); //turned to milliseconds
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
				    	fireDestructedLanucherEvent(destructedLanucher.getAttribute("id"));	
				    }
				};
				t.start();
				
			}
		}
	}

	private void fireDestructedLanucherEvent(String Lid){
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyLauncher(Lid);
		}
	}
	
	private void fireLaunchMissileFromDestructorEvent(String Mid){
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyMissile(Mid);
		}
	}
	
	private void fireLaunchMissileEvent(String Lid,String Mid, Destination destination, int flyTime, int damage) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.LaunchMissile(Lid, Mid, destination, flyTime, damage);
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
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addLauncher(Lid, isHidden);
		}
	}
}

