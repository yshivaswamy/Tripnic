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

 /*
Class BoRequisite
Container class for Requisite info
*/

 public class BoRequisite   
 {
	 
	    private Integer     iId;	 	
	    private  String  iItemname;
	    private	String  iItemtype;
	    private Boolean iIsmanditory;
	    private  Integer  iParticipant_Id; 
	    private Calendar iRequisiteDateTime;
	    private Boolean iPendingRequisite;
	 	
	 	
	    
	    public BoRequisite()
	 	{
		  iId=0;
		  iItemname="";
		  iItemtype="";
		  iIsmanditory=false;
		  iParticipant_Id=0;
		  setRequisiteDateTime(Calendar.getInstance());
		  iPendingRequisite=false;
		  //iReminderType = BoRemaindertype.Bocommonnonmandatory;
		  //isearchrequisitetype=Bosearchrequisitetype.Alltype;
		  
	 	}
	 public BoRequisite(int PId,String PItemname,String PItemtype,Boolean PIsmanditory,int PParticipant_Id,Calendar PDeadline,Boolean PPendingRequisite)
	 	{
		  iId=PId;
		  iItemname=PItemname;
		  iItemtype=PItemtype;
		  iIsmanditory=PIsmanditory;
		  iParticipant_Id=PParticipant_Id;
		  iRequisiteDateTime=PDeadline;
		  iPendingRequisite=PPendingRequisite;
		  //isearchrequisitetype=PBosearchrequisitetype;
		  //iReminderType = PRemindertype;
	 	}
	 
	 
	 public void setiId(Integer aId)
	 {
		 iId=aId;
	 }
	  

	  public Integer getiId()
	   {
		   return iId;
	   }
	 
	public void setiItemname(String aItemname)
	 {
		 iItemname=aItemname;
	 }
	  

	  public String getiItemname()
	   {
		   return iItemname;
	   }
	    
	 public void setiItemtype(String aItemtype)
		 {
		  iItemtype=aItemtype;
		 }
		  

	 public String getiItemtype()
		 {
			   return iItemtype;
		 }

	public void setiIsmanditory(Boolean aIsmanditory)
		{
			iIsmanditory=aIsmanditory;
		}
			  

	 public Boolean getiIsmanditory()
		 {
			 return iIsmanditory;
		 }
	 
	 public void setiDeadline(Calendar aDeadline)
	 {
		 iRequisiteDateTime=aDeadline;
	 }
	  

 public Calendar getiDaeadline()
	 {
		   return iRequisiteDateTime;
	 }

 public void setiParticipant_Id(Integer aParticipant_Id)
	{
	 iParticipant_Id=aParticipant_Id;
	}
		  

public Integer getiParticipant_Id()
	 {
		 return iParticipant_Id;
	 }

public void setiPendingRequisite(Boolean aPendingRequisite)
{
	iPendingRequisite=aPendingRequisite;
}
	  

public Boolean getiPendingRequisite()
 {
	 return iPendingRequisite;
 }
 
	 void deserialize(Document doc,Element aRootElement)
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
					
					if (0 == inNode.getNodeName().compareTo("ID"))
					{
						iId =new  Integer(inElement.getFirstChild().getNodeValue());
					}
					if (0 == inNode.getNodeName().compareTo( "Itemname"))
					{
						iItemname=  inElement.getFirstChild().getNodeValue();
					}
					if (0 == inNode.getNodeName().compareTo("Itemtype"))
					{
						iItemtype =  inElement.getFirstChild().getNodeValue();
					}
					if (0 == inNode.getNodeName().compareTo("Ismanditory"))
					{
						iIsmanditory =   Boolean.valueOf(inElement.getFirstChild().getNodeValue());
					}
					
					if (0==inNode.getNodeName().compareTo("Participant_Id"))
					{
						iParticipant_Id =new  Integer(inElement.getFirstChild().getNodeValue());
					}
					if (0==inNode.getNodeName().compareTo("Date"))
					{
						DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
						try {
							iRequisiteDateTime.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//iDate= new Date(inElement.getFirstChild().getNodeValue());
					}
					if (0==inNode.getNodeName().compareTo("PendingRequisite"))
					{
						iPendingRequisite=  Boolean.valueOf(inElement.getFirstChild().getNodeValue());
					}
					
					
				}
			}
		}
		
		
		void serialize(Document doc,Element aRootElement)
		{
			Element rootElement = doc.createElement("BoRequisite");
			aRootElement.appendChild(rootElement);
			
			if(iId !=0)
			{
				//Id elements
				Element id= doc.createElement("ID");
				id.appendChild(doc.createTextNode(iId.toString()));
				rootElement.appendChild(id);
				
			}
			
			if (iItemname != null)
			{
				// Name elements
				Element Requisitename = doc.createElement("Itemname");
				Requisitename.appendChild(doc.createTextNode(iItemname));
				rootElement.appendChild(Requisitename);
			}
			if (iItemtype != null)
			{
				// Type elements
				Element Requisitetype = doc.createElement("Itemtype");
				Requisitetype.appendChild(doc.createTextNode(iItemtype));
				rootElement.appendChild(Requisitetype);
				
			}
			if (iIsmanditory != null)
			{
				// Manditory elements
				Element manditory = doc.createElement("Ismanditory".toString());
				manditory.appendChild(doc.createTextNode(String.valueOf(iIsmanditory)));
				rootElement.appendChild(manditory);
				
			}
			
			if ( iParticipant_Id != null)
			{
				//Personresponsible  elements
				Element id= doc.createElement("Participantid");
				id.appendChild(doc.createTextNode(iParticipant_Id.toString()));
				rootElement.appendChild(id);
				
			}
			 
			if(iRequisiteDateTime!=null)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
				
				Element date=doc.createElement("Date");
				date.appendChild(doc.createTextNode(dateFormat.format(iRequisiteDateTime.getTime())));
				rootElement.appendChild(date);
			}
			if(iPendingRequisite!=null)
			{
				// Manditory elements
				Element preq = doc.createElement("PendingRequisite".toString());
				preq.appendChild(doc.createTextNode(String.valueOf(iPendingRequisite)));
				rootElement.appendChild(preq);
				
			}			
}
		public Calendar getRequisiteDateTime() {
			return iRequisiteDateTime;
		}

		public void setRequisiteDateTime(Calendar aExpenseDateTime) {
			iRequisiteDateTime = aExpenseDateTime;
		}
			
 }
 
