package com.tripman.engine;


import java.io.File;
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


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ParticipantManager implements IBoParticipant{

	private static ParticipantManager iInstance = null;
	private static int selecteditemindex;
	private static int iNewId = 0;
	public static String SFilePath;
	private String iCurrentTripParticipantFile;
	private List<BoParticipant> iParticipantList;
	private String iTripPath;
	
	public void setselectedindex(int aindex)
	{
		selecteditemindex=aindex;
	}

	public int getselectedindex()
	{
		return selecteditemindex;
	}
	
	public static ParticipantManager getInstance()
	{
		if (null == iInstance)
		{
			iInstance = new ParticipantManager();
			try{
				ParticipantManager.getInstance().deserialize(iInstance.getCurrentTripParticipantFile());
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
	
	private int getNewId()
	 {
		  int id = 0;
		  Iterator<BoParticipant> it=iParticipantList.iterator();
		  while(it.hasNext())
		  {
			  BoParticipant par = it.next();
		   if (par.getId().intValue() >= id)
		   {
		    id = par.getId().intValue()+1;
		   }
		  }
		  return id;
		 }

	
	public List<BoParticipant> getAllParticipant()
	{
		return iParticipantList;
	}
	
	public BoParticipant getParticipant(int aParticipantId)
	{
		Iterator<BoParticipant> it=iParticipantList.iterator();
		while(it.hasNext())
		{
			BoParticipant par = it.next();
			if (par.getId().intValue() == aParticipantId)
			{
				return par;
			}
		}
		return null;
	}
	
	public boolean addParticipant(BoParticipant aParticipant)
	{
		aParticipant.setId(getNewId());
		return iParticipantList.add(aParticipant);
	}
	
	public boolean deleteParticipant(int aParticipantId)
	{
		Iterator<BoParticipant> it=iParticipantList.iterator();
		while(it.hasNext())
		{
			BoParticipant par = it.next();
			if (par.getId().intValue()== aParticipantId)
			{
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean modifyParticipant(BoParticipant aParticipant)
	{
		Iterator<BoParticipant> it=iParticipantList.iterator();
		while(it.hasNext())
		{
			BoParticipant par = it.next();
			if (par.getId().intValue()== aParticipant.getId().intValue())
			{
				aParticipant.setId(par.getId().intValue());
				it.remove();
				iParticipantList.add(aParticipant);
				return true;
			}
		}
		return false;
	}
	
	public void deserialize(String path) throws ParserConfigurationException, SAXException, IOException
	{	
		    
		File fXmlFile = new File(path);
		if(fXmlFile.exists())
		{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("Participant");
		System.out.println("----------------------------");
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			BoParticipant p = new BoParticipant();
			Node nNode = (Node)nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				p.deserialize(doc, eElement);
			}
			
			iParticipantList.add(p);
		}
		}
	}
	
	public void serialize(String path) throws TransformerException
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
		Element rootElement = doc.createElement("ParticipantList");
		doc.appendChild(rootElement);
		
		Iterator<BoParticipant> it=iParticipantList.iterator();
		while(it.hasNext())
		{
			BoParticipant par = it.next();
			par.serialize(doc,rootElement);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));

 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
	}
	public String getCurrentTripParticipantFile() 
	{
		return iCurrentTripParticipantFile;
	}
	
	private ParticipantManager()
	{
		iParticipantList = new ArrayList<BoParticipant>();
		iCurrentTripParticipantFile = BoTripManager.getInstance().getTripDirectory()+"//BoTripParticipant.xml";
		iTripPath = BoTripManager.getInstance().getTripDirectory();
	}
}
