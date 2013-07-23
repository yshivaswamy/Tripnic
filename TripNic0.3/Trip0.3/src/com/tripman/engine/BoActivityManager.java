package com.tripman.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import activity.BoActivity;

enum BoActivitySortType
{
	BoActivityname,
	BoActivitydate,
	BoActivityplace,
	end
}

public class BoActivityManager {
    private static int selecteditemindex = -1;
	private static BoActivityManager iInstance = null;
	public static String SFilePath;
	//private static int iNewId = 0;
	private List<BoActivity> iActivityList;
	private BoActivitySortType itype;
	private String iCurrentTripActivityFile;
	
	public void setselectedindex(int aindex)
	{
		selecteditemindex=aindex;
	}
	
	public int getselectedindex()
	{
		return selecteditemindex;
	}
	
	
	
	public static BoActivityManager getInstance()
	{
		if (null == iInstance)
		{
			iInstance = new BoActivityManager();
			try{
				BoActivityManager.getInstance().deserialize(iInstance.getCurrentTripActivityFile());
			}catch(ParserConfigurationException e)
			{
				e.printStackTrace();
			}
			catch(SAXException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			}
		
		return iInstance;
	}
	
	private BoActivityManager()
	{
		iActivityList = new ArrayList<BoActivity>();
		iCurrentTripActivityFile = BoTripManager.getInstance().getTripDirectory()+"//BoTripActivity.xml";
	}
	
	private boolean createActivityMediaDirectory(String aActivityName)
	{
		String mediapath = BoTripManager.getInstance().getTripDirectory()+"//activity//"+aActivityName;
		File mediaDir = new File(mediapath);
		if(mediaDir.exists() && mediaDir.isDirectory())
		{
			return true;
		}
		else
		{
			return mediaDir.mkdirs();
		}
	}
	
	public String getActivityMediaDirectory(String aActivityName)
	{
		String mediapath = BoTripManager.getInstance().getTripDirectory()+"//activity//"+aActivityName;
		File mediaDir = new File(mediapath);
		if(mediaDir.exists() && mediaDir.isDirectory())
		{
			return mediapath;
		}
		else
		{
			mediaDir.mkdirs();
			return mediapath;
		}
	}
	
	public List<BoActivity> getAllActivity()
	{
		return iActivityList;
	}
	
	public BoActivity getActivity(int aActivityId)
	{
		Iterator<BoActivity> it=iActivityList.iterator();
		while(it.hasNext())
		{
			BoActivity act = it.next();
			if (act.getId() == aActivityId)
			{
				return act;
			}
		}
		return null;
	}
	
	public BoActivity getActivityAt(int aIndex)
	{
		if (iActivityList.size() >= aIndex)
			return iActivityList.get(aIndex);
		
		return null;
	}
	
	private int getNewActivityId()
	{
		int id=1;
		Iterator<BoActivity> it=iActivityList.iterator();
		while(it.hasNext())
		{
			BoActivity act = it.next();
			if(act.getId()>=id)
			{
				id=act.getId()+1;
		}
		}
		return id;
		
	}
	
	public boolean addActivity(BoActivity aActivity)
	{
		aActivity.setId(getNewActivityId());// = getNewActivityId();
		return iActivityList.add(aActivity);
	}
	
	
	public int getActivityIdAt(int aIndex)
	{
		int count=0;
		Iterator<BoActivity> it=iActivityList.iterator();
		while(it.hasNext())
		{
			BoActivity act = it.next();
			if (count==aIndex)
				return act.getId();
			count++;
			
		}
		return -1;
	}
	public boolean removeActivity(int aActivityId)
	{
		Iterator<BoActivity> it=iActivityList.iterator();
		while(it.hasNext())
		{
			BoActivity act = it.next();
			if (act.getId() == aActivityId)
			{
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean modifyActivity(BoActivity aActivity)
	{
		Iterator<BoActivity> it=iActivityList.iterator();
		while(it.hasNext())
		{
			BoActivity act = it.next();
			if (act.getId() == aActivity.getId())
			{
				aActivity.setId(act.getId());// = act.id;
				it.remove();
				iActivityList.add(aActivity);
				return true;
			}
		}
		return false;
	}
	void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile = new File(aPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("BoActivity");
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			BoActivity a = new BoActivity();
			Node nNode = (Node)nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				a.deserialize(doc, eElement);
			}
			
			iActivityList.add(a);
		}
	}

	public void serialize(String aPath) throws TransformerException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("ActivityList");
		doc.appendChild(rootElement);
		
		Iterator<BoActivity> it=iActivityList.iterator();
		while(it.hasNext())
		{
			BoActivity act = it.next();
			act.serialize(doc,rootElement);
			createActivityMediaDirectory(act.getName());
		}
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(aPath));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
		System.out.println("File saved!");
	}

	public String getCurrentTripActivityFile() 
	{
		return iCurrentTripActivityFile;
	}
}
