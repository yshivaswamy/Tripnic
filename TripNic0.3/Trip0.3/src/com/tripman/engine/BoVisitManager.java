package com.tripman.engine;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
enum BoMediaType
{
 Video,Photo
}
enum BoPlaceSorttype
{
	Place,Date
}

public class BoVisitManager implements IBoVisitManager
{
	private static int Selecteditemindex =-1;
	private static BoVisitManager iInstance = null;
	private static int iNewId = 0;
	private List<BoVisit> iBoVisitList;
	//public static String sFilePath; 
	BoPlaceSorttype iPlaceSorttype;
	private String iCurrentTripVisitFile;
	
	public void setSelecetedindex(int aindex)
	{
		Selecteditemindex=aindex;
	}
	public int getSelecteditemindex()
	{
		return Selecteditemindex;
	}
	private BoVisitManager()
	{
		iBoVisitList =new  ArrayList<BoVisit>();
		iCurrentTripVisitFile = BoTripManager.getInstance().getTripDirectory()+"//plcevisit.xml";
	}
	
	private boolean createPlaceVisitMediaDirectory(String aPlaceVisitName)
	{
		String mediapath = BoTripManager.getInstance().getTripDirectory()+"//place_visit//"+aPlaceVisitName;
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
	
	public String getPlaceVisitMediaDirectory(String aPlaceVisitName)
	{
		String mediapath = BoTripManager.getInstance().getTripDirectory()+"//place_visit//"+aPlaceVisitName;
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
	
	public boolean addVisit( BoVisit aVisit)
	{
		aVisit.setiId( getNewVisitId());
		return iBoVisitList.add(aVisit);
	}
	private int getNewVisitId()
	{
		int id=1;
		Iterator<BoVisit> it=iBoVisitList.iterator();
		while(it.hasNext())
		{
			BoVisit visit=it.next();
			if(visit.getid()>=id)
			{
				id=visit.getid()+1;
			}
		}
		return id;
	}
	
	public int getVisitIdAt(int aIndex)
	{
		int count=0;
		Iterator<BoVisit> it=iBoVisitList.iterator();
		while(it.hasNext())
		{
			BoVisit visit = it.next();
			if (count==aIndex)
				return visit.getid();
			count++;
			
		}
		return -1;
	}
	
	public boolean removeVisit(int pId)
	{
        Iterator<BoVisit> it=iBoVisitList.iterator();
		while(it.hasNext())
		{
			BoVisit visit = it.next();
			if (visit.getid() == pId)
			{
				it.remove();
				return true;
			}
		}
		return false;
		}
	public boolean modifyVisit(BoVisit mVisit)
	{
		Iterator<BoVisit> it=iBoVisitList.iterator();
		while(it.hasNext())
		{
			BoVisit visit1 = it.next();
			if (visit1.getid() == mVisit.getid())
			{
				mVisit.setiId(visit1.getid())  ;
				it.remove();
				iBoVisitList.add(mVisit);
				return true;
			}
		}
		return false;
	}
	public BoVisit getVisit(int PId)
	{
		Iterator<BoVisit> it=iBoVisitList.iterator();
		while(it.hasNext())
		{
			BoVisit visit = it.next();
			if (visit.getid()== PId)
			{
				return visit;
			}
		}
		return null;
	}
	public BoVisit getVisitAt(int aIndex)
	{
		if(iBoVisitList.size()>=aIndex)
		return iBoVisitList.get(aIndex);
		return null;
	}
	
	 public List<BoVisit> getAllVisit()

	{
		return iBoVisitList;
	}
	 
		public static BoVisitManager getInstance()
		{
			if (null == iInstance)
			{
				iInstance = new BoVisitManager();
				try {
					
					BoVisitManager.getInstance().deserialize(iInstance.getCurrentTripVisitFile());
				} catch (ParserConfigurationException e) {
					e.printStackTrace();

				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return iInstance;
		}
		public BoVisitManager getVisitmgr()
		{
			return BoVisitManager.getInstance();
		}
		public void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException
		{
			File fXmlFile = new File(aPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("Bovisit");
			System.out.println("----------------------------");
			
			
			int test = nList.getLength();
			iBoVisitList.clear();
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				BoVisit v= new BoVisit();
				Node nNode = (Node)nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					v.deserialize(doc, eElement);
				}
				
				iBoVisitList.add(v);
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
			Element rootElement = doc.createElement("VisitedList");
			doc.appendChild(rootElement);
			
			Iterator<BoVisit> it=iBoVisitList.iterator();
			while(it.hasNext())
			{
				BoVisit visit = it.next();
				visit.serialize(doc,rootElement);
				createPlaceVisitMediaDirectory(visit.getName());
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
		 
		public String getCurrentTripVisitFile() 
		{
			return iCurrentTripVisitFile;
		}

	}