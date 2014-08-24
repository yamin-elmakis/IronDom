package IronDome.xmlParser;

import java.awt.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import sun.launcher.resources.launcher;
import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Launcher;
import IronDome.model.Missile;

public class ReadXMLFile {
	 
	private static Vector<Launcher> launchers;
	private HashMap<String, Missile> missiles; //string for launcher's id and missile for the missile's details
	
	public static void createObject(Element e) {
		String elementName = e.getNodeName();
		// TODO change to switch-case
		if (elementName == "launcher")
			createLauncher(e);
//		else if (elementName == "missile")
//			createMissile(e);
//		else if (elementName == "destructor")
//			if (e.hasAttribute("id"))
//				createMDestructor(e);
//			else if (e.hasAttribute("type")) {
//				createLDestructor(e);
//			}
	}

	public static void createLauncher(Element e) {
		launchers.add(new Launcher(e.getAttribute("id"),Boolean.getBoolean(e.getAttribute("isHidden"))));
	}

	public static void createMissileList(Element elem) {
		int flyTime = Integer.parseInt(elem.getAttribute("flyTime"));
		int damage = Integer.parseInt(elem.getAttribute("damage"));
		int launchTime = Integer.parseInt(elem.getAttribute("launchTime"));
//		Missile m = new Missile()
//				(flyTime, elem.getAttribute("id"),
//				elem.getAttribute("destination"), damage, launchTime);
	}

	public static void createLauncherDestructor(Element elem) {
		
	}

	public static void createMissileDestructor(Element elem) {

	}

	// private MissileLauncher ml;

	//@SuppressWarnings("static-access")
	public static void main(String[] args) {

		List<Launcher> launchersList = new ArrayList<>();
		List<Missile> missileList = new ArrayList<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse("war.xml");
			NodeList mList = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < mList.getLength(); i++) {
				Node node = mList.item(i);
				if (node instanceof Element)
					System.out.println(node.getNodeName());
				NodeList internalList = node.getChildNodes();
				for (int j = 0; j < internalList.getLength(); j++) {
					Node inNode = internalList.item(j);
					if (inNode instanceof Element) {
						System.out.println("\t" + inNode.getNodeName());
						Element elem = (Element) inNode;
						createObject(elem);
						NodeList inList = inNode.getChildNodes();
						for (int k = 0; k < inList.getLength(); k++) {
							Node child = inList.item(k);
							if (child instanceof Element) {
								Element elem1 = (Element) child;
								createObject(elem1);
								System.out
										.println("\t\t" + child.getNodeName());
							}
						}
					}
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
