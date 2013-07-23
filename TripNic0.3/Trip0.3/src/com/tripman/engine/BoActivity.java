package com.tripman.engine;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BoActivity
	{
		private String iname;
		private Calendar iActivityDateTime;
	    private String idescription;
	    private int iduration;
	    private String iPlaceOfActivity;
	    private Integer id;
	    
		
		public BoActivity()
		{
		    iname = "";
		    setActivityDateTime(Calendar.getInstance());
			idescription="";
			iduration=0;
			iPlaceOfActivity="";
			id=0;
		}
		
		public BoActivity(String pname,Calendar pdate ,String pdescription, String pmedia,BoActivitySortType ptype,int pduration,String pPlaceOfActivity,Integer pid)
		{
		    iname = pname;
		    iActivityDateTime = pdate;
			idescription = pdescription;
			iduration=pduration;
			iPlaceOfActivity=pPlaceOfActivity;
			id=pid;
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
					if (0 == inNode.getNodeName().compareTo("Name") )
					{
						iname=inElement.getFirstChild().getNodeValue();
					}
					if (0==inNode.getNodeName().compareTo("Date"))
					{
						DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
						try {
							iActivityDateTime.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//iDate= new Date(inElement.getFirstChild().getNodeValue());
					}
					if (0==inNode.getNodeName().compareTo( "Description"))
					{
						idescription=inElement.getFirstChild().getNodeValue();
					}
					if (0==inNode.getNodeName().compareTo("Duration"))
					{
						iduration=Integer.parseInt(inElement.getFirstChild().getNodeValue());
					}
				    						
					if (0==inNode.getNodeName().compareTo("PlaceOfActivity"))
					{
						iPlaceOfActivity=inElement.getFirstChild().getNodeValue();
					}
					if (0==inNode.getNodeName().compareTo("Id"))
					{
						id=new Integer(inElement.getFirstChild().getNodeValue());
					}
				}
			}
		}
		
		
		public void serialize(Document doc,Element aRootElement)
		{
			Element rootElement = doc.createElement("Activity");
			aRootElement.appendChild(rootElement);
			
			if (iname != null)
			{
				// Name elements
				Element name = doc.createElement("Name");
				name.appendChild(doc.createTextNode(iname));
				rootElement.appendChild(name);
				
			}	
			if(iActivityDateTime!=null)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
				
				Element date=doc.createElement("Date");
				date.appendChild(doc.createTextNode(dateFormat.format(iActivityDateTime.getTime())));
				rootElement.appendChild(date);
			}
			if (idescription != null)
			{
				// Name elements
				Element description = doc.createElement("Description");
				description.appendChild(doc.createTextNode(idescription));
				rootElement.appendChild(description);
				
			}
			
			Element duration = doc.createElement("Duration");
			duration.appendChild(doc.createTextNode(String.valueOf(iduration)));
			rootElement.appendChild(duration);
			
			if(iPlaceOfActivity!=null)
			{
			Element place = doc.createElement("PlaceOfActivity");
			place.appendChild(doc.createTextNode(iPlaceOfActivity));
			rootElement.appendChild(place);
			}
			
			if(id!=0)
			{
			Element id1 = doc.createElement("Id");
			id1.appendChild(doc.createTextNode(id.toString()));
			rootElement.appendChild(id1);
			}
		}
	
	public String getName()
	{
		return iname;
	}
	
	public void setName(String aName)
	{
		iname = aName;
	}
	
	public Calendar getDate()
	{
		return iActivityDateTime;
	}
	
	public void setDate(Calendar adate)
	{
		iActivityDateTime = adate;
	}
	public String getDescription()
	{
		return idescription;
	}
	
	public void setDescription(String aName)
	{
		idescription = aName;
	}
	public int getDuration()
	{
		return iduration;
	}
	
	public void setDuration(int aduration)
	{
		iduration = aduration;
	}
	public String getPlace()
	{
		return iPlaceOfActivity;
	}
	
	public void setPlace(String aName)
	{
		iPlaceOfActivity = aName;
	}
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer aid)
	{
		id = aid;
	}
	
	public Calendar getActivityDateTime() {
		return iActivityDateTime;
	}
	
	public void setActivityDateTime(Calendar aVisitDateTime) {
		iActivityDateTime = aVisitDateTime;
	}
}

//End of file
