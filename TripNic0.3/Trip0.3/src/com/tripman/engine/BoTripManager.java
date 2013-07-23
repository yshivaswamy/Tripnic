package com.tripman.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

enum CurrentTripUIPage
{
	BoEmptyTripPage,
	BoTripHistroyListPage,
	BoPlannedTripListPage,
	BoOngoingTripSummaryPage,
	BoModifyPlannedTripPage,
	BoModifyOngoingtripPage,
	BoViewHistoryTripPage,
	BoUIPageEnd
}

public class BoTripManager {

	private BoTrip iEditingTrip;
	ArrayList<BoTrip> iTripList;
	String iRootDirectory;
	static BoTripManager self = null;
	private static int selecteditemindex;
	private CurrentTripUIPage iCurrentPage;
	
	static public BoTripManager getInstance(String aRootDirectory)
	{
		if (self == null)
		{
			self = new BoTripManager(aRootDirectory);
		}
		return self;
	}
	
	public void setslectedindex(int  aIndex)
	{
		selecteditemindex = aIndex;
	}
	
	public int getslectedindex()
	{
		return selecteditemindex;
	}
	
	static public BoTripManager getInstance()
	{
		return self;
	}
	
	private BoTripManager(String aRootDirectory)
	{
		iTripList = new ArrayList<BoTrip>();
		iRootDirectory = aRootDirectory;
		iCurrentPage = CurrentTripUIPage.BoEmptyTripPage;
		initTripList();
	}
	
	public ArrayList<BoTrip> getAllTrip()
	{
		return iTripList;
	}
	
	public int getTripCount()
	{
		return iTripList.size();
	}
	
	public String getTripDirectory()
	{
	    return iRootDirectory+"//"+iEditingTrip.getTripName();
	}
	
	public BoTrip getTrip(int aId)
	{
		Iterator<BoTrip> it=iTripList.iterator();
		while(it.hasNext())
		{
			BoTrip trip = it.next();
			if (trip.getId() == aId)
			{
				return trip;
			}
		}
		return null;
	}
	
	public BoTrip getTripAt(int aIndex)
	{
		if(iTripList.size()>=aIndex)
	     	return iTripList.get(aIndex);
		
		return null;

	}
	
	public void addTrip(BoTrip aTrip)
	{
		iTripList.add(aTrip);
	}
	
	public void deleteTrip(int aTripId)
	{
		Iterator<BoTrip> it=iTripList.iterator();
		while(it.hasNext())
		{
			BoTrip trip = it.next();
			if (trip.getId() == aTripId)
			{
				it.remove();
				break;
			}
		}
	}
	
	public void modifyTrip(BoTrip aTrip)
	{
		Iterator<BoTrip> it=iTripList.iterator();
		while(it.hasNext())
		{
			BoTrip trip = it.next();
			if (trip.getId() == aTrip.getId())
			{
				it.remove();
				iTripList.add(aTrip);
				break;
			}
		}
	}
	
	public BoTrip CreateNewTrip()
	{
		return new BoTrip(getDefaultName(),getNewTripId());
	}
	
	public void renameTrip(String aOldName, String aNewName)
	{
		File tripDirectory = new File(iRootDirectory+"//"+aOldName);
		if(tripDirectory.exists() && tripDirectory.isDirectory())
		{
			File newTripDirectory = new File(iRootDirectory+"//"+aNewName);
			tripDirectory.renameTo(newTripDirectory);
		}
	}
	
	public void serializeTrip(BoTrip aTrip) throws TransformerConfigurationException
	{
		File tripDirectory = new File(iRootDirectory+"//"+aTrip.getTripName());
		File tripFile = null;
		if(tripDirectory.exists() && tripDirectory.isDirectory())
		{
			tripFile = new File(tripDirectory.getPath()+"//"+"BoTrip.xml");
		}
		else
		{
			tripDirectory.mkdir();
			tripFile = new File(tripDirectory.getPath()+"//"+"BoTrip.xml");
		}
		
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
		aTrip.serialize(doc);
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(tripFile);
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile = new File(aPath);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		BoTrip trip = new BoTrip("existing", -1);
		trip.deserialize(doc);
		
		iTripList.add(trip);
	}
	
	private int getNewTripId()
	{
		int id = 1;
		Iterator<BoTrip> it=iTripList.iterator();
		while(it.hasNext())
		{
			BoTrip trip = it.next();
			if (trip.getId() >= id)
			{
				id = trip.getId()+1;
			}
		}
		return id;
	}
	
	private void initTripList()
	{
		File trip = new File(iRootDirectory);
		
		if (trip.exists() && trip.isDirectory())
		{
			String fileList[] = trip.list();
			for(int loop = 0; loop < fileList.length; loop++)
			{
				File inFile = new File(trip.getPath()+"//"+fileList[loop]);
				if (inFile.exists() && inFile.isDirectory())
				{
					ReadTripInfo(inFile.getPath()+"//"+"BoTrip.xml");
				}
			}
		}
	}
	
	private void ReadTripInfo(String aTripInfoFile)
	{
		// XML parsing and filling...
		try {
			deserialize(aTripInfoFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getDefaultName()
	{
		String tripname = "unnamed";
		String searchName = tripname;
		File trip = new File(iRootDirectory);
		if (trip.exists() && trip.isDirectory())
		{
			String fileList[] = trip.list();
			for(int loop = 0; loop < fileList.length; loop++)
			{
				File inFile = new File(trip.getPath()+"//"+searchName);
				if (inFile.exists() && inFile.isDirectory())
				{
					searchName = tripname+String.valueOf(loop+1);
				}
				else
				{
					break;
				}
			}
		}
		else
		{
			trip.mkdir();
		}
		
		return searchName;
	}

	public BoTrip getEditingTrip() {
		return iEditingTrip;
	}

	public void setEditingTrip(BoTrip aEditingTrip) {
		this.iEditingTrip = aEditingTrip;
	}

	public CurrentTripUIPage getCurrentPage() 
	{
		return iCurrentPage;
	}

	public void setCurrentPage(CurrentTripUIPage aCurrentPage) 
	{
		iCurrentPage = aCurrentPage;
	}
}
