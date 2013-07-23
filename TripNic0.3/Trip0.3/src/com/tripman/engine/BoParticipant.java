package com.tripman.engine;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
Class BoAddress
Container class for Address
*/
 
/*
Class BoPersonIdentifier
Container class for Person name
*/

/*
Class BoParticipant
Container class for Participant Info
*/
public class BoParticipant
{
	private BoPersonIdentifier     iName;
	private BoAddress              iAddress;
	private String                 iPhoneNumber;
	private String                 iEMail;
	private Calendar			   iDoB;
	private Integer iId;
	
	public BoParticipant()
	{
	    iName = new BoPersonIdentifier();
		iAddress = new BoAddress();
		iPhoneNumber = "";
		iEMail = "";
		iDoB = Calendar.getInstance();
	}
	
	BoParticipant(BoPersonIdentifier aIden, BoAddress aAddr, String aPhoneNum, String aEMail,Calendar aDoB)
	{
	    iName = aIden;
		iAddress = aAddr;
		iPhoneNumber = aPhoneNum;
		iEMail = aEMail;
		iDoB = aDoB;
	}
	
	public void setName(BoPersonIdentifier aName)
	{
	    iName = aName;
	}
	
	public BoPersonIdentifier getPersonIdentifier()
	{
		return iName;
	}
 	public void setAddress(BoAddress aAddr)
	{
		iAddress = aAddr;
	}
	
 	public BoAddress getAddress()
	{
		return iAddress;
	}
	/*void AddDateToRemember(BoDateToRemember aDateToAdd)
	{
	    iDates.add(aDateToAdd);
	}*/
 	
 	public Calendar getDoB()
	{
		return iDoB;
	}
	
	public void setDoB(Calendar aDoB)
	{
		iDoB = aDoB;
	}
	
	
	public String getEmail()
	{
		return iEMail;
	}
	
	public void setEmail(String aEMail)
	{
		iEMail = aEMail;
	}
	
	public String getMobileNumber()
	{
		return iPhoneNumber;
	}
	
	public void setMobileNumber(String aPhoneNumber)
	{
		iPhoneNumber = aPhoneNumber;
	}
	
	public Integer getId()
	{
		return iId;
	}
	
	public void setId(Integer aId)
	{
		iId = aId;
	}
	
	void deserialize(Document doc,Element aRootElement)
	{

		NodeList childNodeList = aRootElement.getChildNodes();
		int testcldCnt = childNodeList.getLength();
		for (int inLoop = 0; inLoop < childNodeList.getLength(); inLoop++)
		{
			Node inNode = (Node)childNodeList.item(inLoop);
			inNode.getNodeName();
			if (inNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element inElement = (Element) inNode;
				String testTagName = inNode.getNodeName();
				String testTagValue = inNode.getNodeValue();
		
					//iId =new  Integer(inElement.getFirstChild().getNodeValue());

				
				if (0 == inNode.getNodeName().compareTo("Id"))
				{
					iId =new  Integer(inElement.getFirstChild().getNodeValue());
				}
				if (inNode.getNodeName() == "Name")
				{
					NodeList nameNodes = inNode.getChildNodes();
					int namecldCnt = nameNodes.getLength();
					for (int nameLoop = 0; nameLoop < nameNodes.getLength(); nameLoop++)
					{
						Node nameNode = (Node)nameNodes.item(nameLoop);
						//Node.getNodeName();
						if (nameNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Element nameElement = (Element) nameNode;
							String nameTagName = nameElement.getNodeName();
							String nameTagValue = nameElement.getNodeValue();
							if (nameElement.getNodeName() == "FirstName")
							{
								//String test = nameElement.getFirstChild().getNodeValue();
								
								iName.setFirstName(nameElement.getFirstChild().getNodeValue());
								
							}
							if (nameElement.getNodeName() == "MiddleName")
							{
								iName.setMiddleName(nameElement.getFirstChild().getNodeValue());
							}
							if (nameElement.getNodeName() == "LastName")
							{
								iName.setLastName(nameElement.getFirstChild().getNodeValue());
							}
						}
					}
				}
				
				if (inNode.getNodeName() == "Address")
				{
					NodeList nameNodes = inNode.getChildNodes();
					for (int nameLoop = 0; nameLoop < nameNodes.getLength(); nameLoop++)
					{
						Node nameNode = (Node)nameNodes.item(nameLoop);
						//Node.getNodeName();
						if (nameNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Element nameElement = (Element) nameNode;
							String nameTagName = nameElement.getNodeName();
							String nameTagValue = nameElement.getNodeValue();
							if (nameElement.getNodeName() == "DoorNumber")
							{
								iAddress.setDoorNum(nameElement.getFirstChild().getNodeValue());
							}
							if (nameElement.getNodeName() == "Street")
							{
								iAddress.setStreet(nameElement.getFirstChild().getNodeValue());
							}
							if (nameElement.getNodeName() == "Area")
							{
								iAddress.setArea(nameElement.getFirstChild().getNodeValue());
							}
							if (nameElement.getNodeName() == "City")
							{
								iAddress.setCity(nameElement.getFirstChild().getNodeValue());
							}
							if (nameElement.getNodeName() == "State")
							{
								iAddress.setState(nameElement.getFirstChild().getNodeValue());
							}
							if (nameElement.getNodeName() == "PIN")
							{
								iAddress.setPin(nameElement.getFirstChild().getNodeValue());
							}
						}
					}
				}
				
				if (0 == inNode.getNodeName().compareTo( "E-Mail"))
				{
					iEMail=  inElement.getFirstChild().getNodeValue();
				}
				
				if (0 == inNode.getNodeName().compareTo( "Mobile-Number"))
				{
					iPhoneNumber=  inElement.getFirstChild().getNodeValue();
				}
				
				if (0 == inNode.getNodeName().compareTo("DoB") )
				{
					DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyy");
					try {
						iDoB.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
					} catch (DOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//iname=inElement.getFirstChild().getNodeValue();
				}
			}
		}
	}
	
	void serialize(Document doc,Element aRootElement)
	{
		Element rootElement = doc.createElement("Participant");
		aRootElement.appendChild(rootElement);
		
		/*if(iId !=0)
		{
			
		}*/
		
		Element id = doc.createElement("Id");
		id.appendChild(doc.createTextNode(iId.toString()));
		rootElement.appendChild(id);
		
		if( iPhoneNumber != null && iPhoneNumber.length()>0)
		{
			Element phonenumber = doc.createElement("Mobile-Number");
			phonenumber.appendChild(doc.createTextNode(iPhoneNumber.toString()));
			rootElement.appendChild(phonenumber);
					
		}
		
		if (iDoB != null)
		{
			// Name elements
			DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
			Element date = doc.createElement("DoB");
			date.appendChild(doc.createTextNode(dateFormat.format(iDoB.getTime())));
			rootElement.appendChild(date);
			
			

		}
		
		if( iEMail != null && iEMail.length()>0)
		{
			Element email = doc.createElement("E-Mail");
			email.appendChild(doc.createTextNode(iEMail.toString()));
			rootElement.appendChild(email);
					
		}
		if (iName != null)
		{
			// Name elements
			Element name = doc.createElement("Name");
			rootElement.appendChild(name);
			
			if ((iName.getFirstName()!= null) && (iName.getFirstName().length()>0))
			{
				Element fname = doc.createElement("FirstName");
				fname.appendChild(doc.createTextNode(iName.getFirstName()));
				name.appendChild(fname);
			}
			
			if ((iName.getMiddleName() != null) && (iName.getMiddleName().length()>0))
			{
				Element mname = doc.createElement("MiddleName");
				mname.appendChild(doc.createTextNode(iName.getMiddleName()));
				name.appendChild(mname);
			}
			
			if ((iName.getLastName() != null) && (iName.getLastName().length()>0))
			{
				Element lname = doc.createElement("LastName");
				lname.appendChild(doc.createTextNode(iName.getLastName()));
				name.appendChild(lname);
			}
		}
		
		if (iAddress != null)
		{
			// Name elements
			Element address = doc.createElement("Address");
			rootElement.appendChild(address);
			
			if ((iAddress.getDoorNum() != null) && (iAddress.getDoorNum().length()>0))
			{
				Element dn = doc.createElement("DoorNumber");
				dn.appendChild(doc.createTextNode(iAddress.getDoorNum()));
				address.appendChild(dn);
			}
			
			if ((iAddress.getStreet() != null) && (iAddress.getStreet().length()>0))
			{
				Element st = doc.createElement("Street");
				st.appendChild(doc.createTextNode(iAddress.getStreet()));
				address.appendChild(st);
			}
			
			if ((iAddress.getArea() != null) && (iAddress.getArea().length()>0))
			{
				Element area = doc.createElement("Area");
				area.appendChild(doc.createTextNode(iAddress.getArea()));
				address.appendChild(area);
			}
			
			if ((iAddress.getCity() != null) && (iAddress.getCity().length()>0))
			{
				Element city = doc.createElement("City");
				city.appendChild(doc.createTextNode(iAddress.getCity()));
				address.appendChild(city);
			}
			
			if ((iAddress.getCity() != null) && (iAddress.getCity().length()>0))
			{
				Element state = doc.createElement("State");
				state.appendChild(doc.createTextNode(iAddress.getCity()));
				address.appendChild(state);
			}
			
			if ((iAddress.getPin() != null) && (iAddress.getPin().length()>0))
			{
				Element pin = doc.createElement("PIN");
				pin.appendChild(doc.createTextNode(iAddress.getPin()));
				address.appendChild(pin);
			}
			
			
			
			
		}	
		
		
	}
}
