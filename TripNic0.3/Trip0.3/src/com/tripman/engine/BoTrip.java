package com.tripman.engine;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


enum BoTripState
{
	BoEnjoyed,
	BoEnjoying,
	BoToEnjoy,
	BoStateEnd
};

enum BoTripType
{
	BoTripTypeSharing,
	BoTripTypePersonal,
	BoTripTypeEnd
};

public class BoTrip {

	private BoTripState iState;
	private String iTripName;
	private BoTripSettings iSettings;
	private BoTripType iType;
	private Calendar iStartDate;
	private Calendar iEndDate;
	private int iId;
	
	public BoTrip(String aTripName, int aId)
	{
		setState(BoTripState.BoToEnjoy);
		setType(BoTripType.BoTripTypePersonal);
		setStartDate(Calendar.getInstance());
		setEndDate(Calendar.getInstance());
		//iStartDate = new GregorianCalendar();
		//iEndDate = new GregorianCalendar();
		setTripName(aTripName);
		setSettings(new BoTripSettings());
		setId(aId);
	}

	public int getId() {
		return iId;
	}

	public void setId(int aId) {
		iId = aId;
	}

	public BoTripState getState() {
		return iState;
	}

	public void setState(BoTripState aState) {
		iState = aState;
	}

	public String getTripName() {
		return iTripName;
	}

	public void setTripName(String aTripName) {
		iTripName = aTripName;
	}

	public BoTripSettings getSettings() {
		return iSettings;
	}

	public void setSettings(BoTripSettings aSettings) {
		iSettings = aSettings;
	}

	public BoTripType getType() {
		return iType;
	}

	public void setType(BoTripType aType) {
		iType = aType;
	}

	public Calendar getStartDate() {
		return iStartDate;
	}

	public void setStartDate(Calendar aStartDate) {
		iStartDate = aStartDate;
	}

	public Calendar getEndDate() {
		return iEndDate;
	}

	public void setEndDate(Calendar aEndDate) {
		iEndDate = aEndDate;
	}
	
	public void serialize(Document doc)
	{
		Element rootElement = doc.createElement("BoTrip");
		doc.appendChild(rootElement);
		
		Element idElement = doc.createElement("ID");
		idElement.appendChild(doc.createTextNode(String.valueOf(iId)));
		rootElement.appendChild(idElement);
		
		if (iTripName != null)
		{
			Element nameElement = doc.createElement("TripName");
			nameElement.appendChild(doc.createTextNode(iTripName));
			rootElement.appendChild(nameElement);
		}
		
		if (iState != null)
		{
			Element stateElement = doc.createElement("TripState");
			stateElement.appendChild(doc.createTextNode(String.valueOf(iState)));
			rootElement.appendChild(stateElement);
		}
		
		if (iType != null)
		{
			Element typeElement = doc.createElement("TripType");
			typeElement.appendChild(doc.createTextNode(String.valueOf(iType)));
			rootElement.appendChild(typeElement);
		}
		
		if (iStartDate != null)
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
			
			Element startDateElement = doc.createElement("TripStartDate");
			startDateElement.appendChild(doc.createTextNode(dateFormat.format(iStartDate.getTime())));
			rootElement.appendChild(startDateElement);
		}
		
		if (iEndDate != null)
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
			Element endDateElement = doc.createElement("TripEndDate");
			endDateElement.appendChild(doc.createTextNode(dateFormat.format(iEndDate.getTime())));
			rootElement.appendChild(endDateElement);
		}
		
		if (iSettings != null)
		{
			Element settingsElement = doc.createElement("TripSettings");
			rootElement.appendChild(settingsElement);
			
			
			Element smsElement = doc.createElement("EnableSMS");
			smsElement.appendChild(doc.createTextNode(String.valueOf(iSettings.isEnableSms())));
			settingsElement.appendChild(smsElement);
			
			Element emailElement = doc.createElement("EnableEmail");
			emailElement.appendChild(doc.createTextNode(String.valueOf(iSettings.isEnableEmail())));
			settingsElement.appendChild(emailElement);
			
			Element smssyncElement = doc.createElement("EnableSMSSync");
			smssyncElement.appendChild(doc.createTextNode(String.valueOf(iSettings.isSms_sync())));
			settingsElement.appendChild(smssyncElement);
			
			Element emailsyncElement = doc.createElement("EnableEmailSync");
			emailsyncElement.appendChild(doc.createTextNode(String.valueOf(iSettings.isEmail_sync())));
			settingsElement.appendChild(emailsyncElement);
		}
	}
	
	public void deserialize(Document doc)
	{
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("BoTrip");
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = (Node)nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				NodeList childNodeList = nNode.getChildNodes();
				for (int inLoop = 0; inLoop < childNodeList.getLength(); inLoop++)
				{
					Node inNode = (Node)childNodeList.item(inLoop);
					if (inNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element inElement = (Element) inNode;
						if (0 == inNode.getNodeName().compareTo("ID") )
						{
							Integer id = Integer.valueOf(inElement.getFirstChild().getNodeValue());
							iId=id.intValue();
						}
						if (0 == inNode.getNodeName().compareTo("TripName") )
						{
							iTripName=inElement.getFirstChild().getNodeValue();
						}
						if (0 == inNode.getNodeName().compareTo("TripState") )
						{
							iState=BoTripState.valueOf(inElement.getFirstChild().getNodeValue());
						}
						if (0 == inNode.getNodeName().compareTo("TripType") )
						{
							iType=BoTripType.valueOf(inElement.getFirstChild().getNodeValue());
						}
						if (0 == inNode.getNodeName().compareTo("TripStartDate") )
						{
							DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyy");
							try {
								iStartDate.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
							} catch (DOMException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//iname=inElement.getFirstChild().getNodeValue();
						}
						if (0 == inNode.getNodeName().compareTo("TripEndDate") )
						{
							DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyy");
							try {
								iEndDate.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
							} catch (DOMException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (0 == inNode.getNodeName().compareTo("TripSettings") )
						{
							//iname=inElement.getFirstChild().getNodeValue();
							NodeList setCNList = inNode.getChildNodes();
							for (int setLoop = 0; setLoop < setCNList.getLength(); setLoop++)
							{
								Node setCNode = (Node)childNodeList.item(setLoop);
								if (setCNode.getNodeType() == Node.ELEMENT_NODE)
								{
									Element smsElement = (Element) setCNode;
									if (0 == inNode.getNodeName().compareTo("EnableSMS") )
									{
										Boolean id = Boolean.valueOf(smsElement.getFirstChild().getNodeValue());
										iSettings.setEnableSms(id.booleanValue());
									}
									if (0 == inNode.getNodeName().compareTo("EnableEmail") )
									{
										Boolean id = Boolean.valueOf(smsElement.getFirstChild().getNodeValue());
										iSettings.setEnableEmail(id.booleanValue());
									}
									if (0 == inNode.getNodeName().compareTo("EnableSMSSync") )
									{
										Boolean id = Boolean.valueOf(smsElement.getFirstChild().getNodeValue());
										iSettings.setSms_sync(id.booleanValue());
									}
									if (0 == inNode.getNodeName().compareTo("EnableEmailSync") )
									{
										Boolean id = Boolean.valueOf(smsElement.getFirstChild().getNodeValue());
										iSettings.setEmail_sync(id.booleanValue());
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
