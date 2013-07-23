package com.tripman.engine;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.R.integer;

//import ParticipantManager.BoDateToRemember;

//import ParticipantManager.BoAddress;

import java.io.*;
/*
  Class BoAddress
  Container class for Address
*/
 class BoMedia
{
	BoMediaType Type;
	File MediaData;
}
 
public class BoVisit
{
	private String iName;
	//Date iDate;
	private Calendar iVisitDateTime;
	private String iDescription;
	//private List<BoMedia> Media;
	//private BoVisitAddress iAddress;
	private String iAddr;
	private String iPhonenumber;
	private String iEmail;
	private  Integer iId;
    
    
    public BoVisit() {
    	iName="";
    	//iDate = new Date();
    	setVisitDateTime(Calendar.getInstance());
    	iDescription="";
    	iAddr="";
    	//iAddress=new BoVisitAddress();
    	iPhonenumber="";
    	iEmail="";
    	iId=0;
    	
    }
    
     public BoVisit(Integer piId,BoPlaceSorttype pPlaceSorttype,String pName,Calendar pdate,String pDescription,String pAddr,String pPhonenumber,String pEmail)
    {
    	 //BoVisitAddress addr
    	iName=pName;
    	//Media=new LinkedList();
    	//pdate = new Date();
    	iVisitDateTime=pdate;
    	iDescription=pDescription;
    	iAddr=pAddr;
    	//iAddress=addr;
    	iPhonenumber= pPhonenumber;
    	iEmail=pEmail;
    	iId=piId;
    	//iPlaceSorttype=BoPlaceSorttype.Place;
    }
     public Integer getid()
  	{
  		return iId ;
  	}
     public void setiId(Integer pId)
     {
    	 iId=pId;
     }
     
 	public String getName()
 	{
 		return iName ;
 	}
    public void setName(String pName)
    {
    	iName=pName;
    }
    
 	public String getDescription()
 	{
 		return iDescription ;
 	}
 	public void setDescription (String pDescription)
    {
 		iDescription=pDescription;
    }
 	
 	public String getAddr()
 	{
 		return iAddr;
 	}
 	public void setAddr (String pAddr)
    {
 		iAddr=pAddr;
    }
 	
 	public String getPhonenumber()
 	{
 		return iPhonenumber ;
 	}
 	public void setPhonenumber(String pPhonenumber)
    {
 		iPhonenumber=pPhonenumber;
    }
 	
 	public String getEmail()
 	{
 		return iEmail ;
 	}
 	public void setEmail (String pEmail)
    {
 		iEmail=pEmail;
    }
     public void deserialize(Document doc,Element aRootElement)
	{

		NodeList childNodeList = aRootElement.getChildNodes();
		for (int inLoop = 0; inLoop < childNodeList.getLength(); inLoop++)
		{
			Node inNode = (Node)childNodeList.item(inLoop);
			inNode.getNodeName();
			if (inNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element inElement = (Element) inNode;
				if (inElement.getChildNodes().getLength() <= 0)
					continue;
				
				if (0==inNode.getNodeName().compareTo("Name") )
				{
					iName=inElement.getFirstChild().getNodeValue();
				}
				else if (0==inNode.getNodeName().compareTo("Date"))
				{
					DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
					try {
						iVisitDateTime.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
					} catch (DOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//iDate= new Date(inElement.getFirstChild().getNodeValue());
				}
				else if (0==inNode.getNodeName().compareTo("Description") )
				{
					iDescription=inElement.getFirstChild().getNodeValue();
				}
				else if (0==inNode.getNodeName().compareTo("Addr") )
				{
					iAddr=inElement.getFirstChild().getNodeValue();
				}

				else if (0==inNode.getNodeName().compareTo("phonenumber") )
				{
					iPhonenumber=inElement.getFirstChild().getNodeValue();
				}
				else if (0==inNode.getNodeName().compareTo("iEmail"))
				{
					iEmail=inElement.getFirstChild().getNodeValue();
				}
				else if (0==inNode.getNodeName().compareTo("Id"))
				{
					iId=new Integer(inElement.getFirstChild().getNodeValue());
				}
	/*			else if (0==inNode.getNodeName().compareTo("Address") )
				{
					NodeList nameNodes = inNode.getChildNodes();
					for (int nameLoop = 0; nameLoop < nameNodes.getLength(); nameLoop++)
					{
						Node nameNode = (Node)nameNodes.item(nameLoop);
						//Node.getNodeName();
						if (nameNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Element nameElement = (Element) nameNode;
							if (nameElement.getNodeName() == "DoorNumber")
							{
								iAddress.iDoorNum =  nameElement.getFirstChild().getNodeValue();
							}
							if (nameElement.getNodeName() == "Street")
							{
								iAddress.iStreet =  nameElement.getFirstChild().getNodeValue();
							}
							if (nameElement.getNodeName() == "Area")
							{
								iAddress.iArea =  nameElement.getFirstChild().getNodeValue();
							}
							if (nameElement.getNodeName() == "City")
							{
								iAddress.iCity =  nameElement.getFirstChild().getNodeValue();
							}
							if (nameElement.getNodeName() == "State")
							{
								iAddress.iState =  nameElement.getFirstChild().getNodeValue();
							}
							if (nameElement.getNodeName() == "PIN")
							{
								iAddress.iPin =  nameElement.getFirstChild().getNodeValue();
							}
						}
					}
				}*/
			}
		}
	}
     
public void serialize(Document doc, Element rootElement) {
	// TODO Auto-generated method stub
	Element rootElement1 = doc.createElement("Bovisit");
	rootElement.appendChild(rootElement1);
	
	/*
	if (iAddress != null)
	{
		// Name elements
		Element address = doc.createElement("Address");
		rootElement1.appendChild(address);
		
		if ((iAddress.iDoorNum != null) && (iAddress.iDoorNum.length()>0))
		{
			Element dn = doc.createElement("DoorNumber");
			dn.appendChild(doc.createTextNode(iAddress.iDoorNum));
			address.appendChild(dn);
		}
		
		if ((iAddress.iStreet != null) && (iAddress.iStreet.length()>0))
		{
			Element st = doc.createElement("Street");
			st.appendChild(doc.createTextNode(iAddress.iStreet));
			address.appendChild(st);
		}
		
		if ((iAddress.iArea != null) && (iAddress.iArea.length()>0))
		{
			Element area = doc.createElement("Area");
			area.appendChild(doc.createTextNode(iAddress.iArea));
			address.appendChild(area);
		}
		
		if ((iAddress.iCity != null) && (iAddress.iCity.length()>0))
		{
			Element city = doc.createElement("City");
			city.appendChild(doc.createTextNode(iAddress.iCity));
			address.appendChild(city);
		}
		
		if ((iAddress.iState != null) && (iAddress.iState.length()>0))
		{
			Element state = doc.createElement("State");
			state.appendChild(doc.createTextNode(iAddress.iState));
			address.appendChild(state);
		}
		
		if ((iAddress.iPin != null) && (iAddress.iPin.length()>0))
		{
			Element pin = doc.createElement("PIN");
			pin.appendChild(doc.createTextNode(iAddress.iPin));
			address.appendChild(pin);
		}
	}	*/
	if (iId!= null)
	{
		// Name elements
		Element id = doc.createElement("Id");
		id.appendChild(doc.createTextNode(iId.toString()));
		rootElement1.appendChild(id);
	}

		if (iName != null)
		{
			// Name elements
			Element name = doc.createElement("Name");
			name.appendChild(doc.createTextNode(iName));
			rootElement1.appendChild(name);
		}
		if ((iPhonenumber != null) && ((iPhonenumber.length()>0)))
		{
			Element phonenumber = doc.createElement("phonenumber");
			phonenumber.appendChild(doc.createTextNode(iPhonenumber));
			rootElement1.appendChild(phonenumber);
		}
		if (iDescription != null)
		{
			// Name elements
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode(iDescription));
			rootElement1.appendChild(description);
		}
		if (iAddr != null)
		{
			// Name elements
			Element addr = doc.createElement("Addr");
			addr.appendChild(doc.createTextNode(iAddr));
			rootElement1.appendChild(addr);
		}
		if (iEmail != null)
		{
			// Name elements
			Element email = doc.createElement("iEmail");
			email.appendChild(doc.createTextNode(iEmail));
			rootElement1.appendChild(email);
			
     	}
		
		if(iVisitDateTime!=null)
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
			
			Element date=doc.createElement("Date");
			date.appendChild(doc.createTextNode(dateFormat.format(iVisitDateTime.getTime())));
			rootElement1.appendChild(date);
		}
	}

	public Calendar getVisitDateTime() {
		return iVisitDateTime;
	}
	
	public void setVisitDateTime(Calendar aVisitDateTime) {
		iVisitDateTime = aVisitDateTime;
	}
}