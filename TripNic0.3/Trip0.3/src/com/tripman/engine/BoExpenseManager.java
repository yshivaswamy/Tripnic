package com.tripman.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
//import expense.BoExpense;



enum BoExpSortType
{
	BoSortTypeDate,	BoSortTypeplace,BoSortTypeAmount,BoSortTypeend
}
enum BoSearchExpenseType
{
	BoSearchExpenseShared,BoSearchExpensePersonal,BoSearchExpenseAll,BoSearchExpenseend
}


public class BoExpenseManager implements IBoExpenseManager{
	
	private static int selecteditemindex=-1;
	private static BoExpenseManager iInstance = null;
	//private static int iNewId = 0;
	private  List<BoExpense> iExpenceList;
	private static  BoExpSortType iExpsorttype;
	private static  BoSearchExpenseType  iSearchExpensetype;
	private String iCurrentTripExpenseFile;
	
	private String iTripPath;
	
	//private String sFilePath;
	
	public void setselectedindex(int aindex)
	{
		selecteditemindex=aindex;
	}

	public int getselectedindex()
	{
		return selecteditemindex;
	}
	
	public static void destroyInstance()
	{
		if( null != iInstance)
		{
			iInstance = null;
			System.gc();
		}
	}
	
	public static BoExpenseManager getInstance()
	{
		if (null == iInstance)
		{
			iInstance = new BoExpenseManager();
			
			try {
				iInstance.deserialize(iInstance.iTripPath+"//BoTripExpense.xml");
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
		return iInstance;
	}
	
	public  BoExpenseManager getexpman()
	{
		return BoExpenseManager.getInstance();
	}
	
	private BoExpenseManager()
	{
		iExpenceList = new ArrayList<BoExpense>();
		iTripPath = BoTripManager.getInstance().getTripDirectory();
	}
	
	public List<BoExpense> getAllExpence( )
	{
		return iExpenceList;
	}
		
	public List<BoExpense> getAllExpences(BoSearchExpenseType searchType)
	{
		if( searchType==BoSearchExpenseType.BoSearchExpenseAll)
		{
			return iExpenceList;
		}
		else if( searchType==BoSearchExpenseType.BoSearchExpensePersonal)
		{
			List<BoExpense> personalExpenses = new ArrayList<BoExpense>();
			//Filter only personal expenses
			Iterator<BoExpense> it=iExpenceList.iterator();
			while(it.hasNext())
			{
				BoExpense personal=it.next();
				if(!personal.getiIsshared())
				{
					personalExpenses.add(personal);
				}	
				
			}
			return personalExpenses;
		}
		else
		{
			List<BoExpense> sharedExpenses = new ArrayList<BoExpense>();
			//Filter only shared expenses
			return sharedExpenses;
		}	
	}
	
	
	
	public List<BoExpense> getParticipantExpence(BoSearchExpenseType searchType,int pId )
	{
		 List<BoExpense> partiexp= new ArrayList<BoExpense>();
		 Iterator<BoExpense> it=iExpenceList.iterator();
		 while(it.hasNext())
		 {
			 BoExpense exp = (BoExpense)it.next();
			 if (iSearchExpensetype == searchType &&  exp.getiId() == pId)
				{
				 partiexp.add(exp);
				}
			 
		 }
		 return partiexp;
	 }
	
	
	public BoExpense getExpence(int aExpenceId)
	{
		Iterator<BoExpense> it=iExpenceList.iterator();
		while(it.hasNext())
		{
			BoExpense exp = it.next();
			if (exp.getiId() == aExpenceId)
			{
				return exp;
			}
		}
		return null;
	}
	
	public BoExpense getExpenseAt(int aIndex)
	{
		if(iExpenceList.size() >=aIndex)
		return iExpenceList.get(aIndex);
		return null;
	}
	///////
	
	
	private int getNewExpenceId()
	 {
	  int aExpence = 1;
	  Iterator<BoExpense> it=iExpenceList.iterator();
	  while(it.hasNext())
	  {
		  BoExpense exp = it.next();
	   if (exp.getiId() >= aExpence)
	   {
		   aExpence = exp.getiId()+1;
	   }
	  }
	  return aExpence;
	 }
	//////
	public boolean addExpence(BoExpense aExpence)
	{
		
		
		aExpence.setiId(getNewExpenceId());
		
		return iExpenceList.add(aExpence);
	}
	
	public int getexpenseIdAt(int aIndex)
	{
		int count=0;
		Iterator<BoExpense> it=iExpenceList.iterator();
		while(it.hasNext())
		{
			BoExpense exp = it.next();
			if (count==aIndex)
				return exp.getiId();
			count++;
			
		}
		return -1;
	}
	public boolean deleteExpence(int aExpenceId)
	{
		Iterator<BoExpense> it=iExpenceList.iterator();
		while(it.hasNext())
		{
			BoExpense exp = it.next();
			if (exp.getiId() == aExpenceId)
			{
				it.remove();
				return true;
			}
		}
		return false;
	}
	public boolean modifyExpence(BoExpense aExpence)
	{
		Iterator<BoExpense> it=iExpenceList.iterator();
		while(it.hasNext())
		{
			BoExpense exp = it.next();
			if (exp.getiId() == aExpence.getiId())
			{
				aExpence.setiId(exp.getiId());
				it.remove();
				iExpenceList.add(aExpence);
				return true;
			}
		}
		return false;
	}
	
	
	public void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile = new File(aPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		doc.getDocumentElement().normalize();
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("Expence");
		//System.out.println("----------------------------");
		iExpenceList.clear();
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			BoExpense e = new BoExpense();
			Node nNode = (Node)nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				e.deserialize(doc, eElement);
			}
			
			iExpenceList.add(e);
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
		Element rootElement = doc.createElement("ExpenceList");
		doc.appendChild(rootElement);
		
		Iterator<BoExpense> it=iExpenceList.iterator();
		while(it.hasNext())
		{
			BoExpense exp = it.next();
			exp.serialize(doc,rootElement);
		}
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		
		File tripDirectory = new File(iTripPath);
		File tripFile = null;
		if(tripDirectory.exists() && tripDirectory.isDirectory())
		{
			tripFile = new File(tripDirectory.getPath()+"//"+"BoTripExpense.xml");
		}
		else
		{
			tripDirectory.mkdir();
			tripFile = new File(tripDirectory.getPath()+"//"+"BoTripExpense.xml");
		}
		
		StreamResult result = new StreamResult(tripFile);
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source , result);
 
		System.out.println("File saved!");
	}
	public String getCurrentTripExpenseFile() 
	{
		return iCurrentTripExpenseFile;
	}

	public List<BoExpense> getDayExpence(BoSearchExpenseType searchType,
			Calendar dayExpense) {
		 List<BoExpense> dayexp= new ArrayList<BoExpense>();
		 Iterator<BoExpense> it=iExpenceList.iterator();
		 while(it.hasNext())
		 {
			 BoExpense exp = it.next();
			 if (iSearchExpensetype == searchType &&  exp.getExpenseDateTime() == dayExpense)
				{
					dayexp.add(exp);
				}
		 }
		 return dayexp;

	}

}