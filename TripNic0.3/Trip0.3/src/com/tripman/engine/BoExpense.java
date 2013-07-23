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


//import expense.BoExpense;

//import Mathutil.BoRemaindertype;



public class BoExpense 
{
		
	private String iDescription;
	private Calendar iExpenseDateTime;
	private float iAmount;
	private String iSpent_by_participant;
	private String iPlace_spent;
	private Integer EId;
	private Boolean iIsshared;
//BoExpsorttype iExpsorttype;
//BoSearchExpensetype iSearchExpensetype;

 public BoExpense()
{
	 iDescription=""; 
	 setExpenseDateTime(Calendar.getInstance());
	//iAmount="";
	 iSpent_by_participant="";
	 iIsshared=false;
	 iPlace_spent="";
	// iExpsorttype = BoExpsorttype.Place;
	 //iSearchExpensetype=BoSearchExpensetype.Personal;
	 EId=0;
	 
	}
 public BoExpense(Integer pId,String PDescription,String PSpent_by_participant,Boolean PIsshared,float PAmount,String PPlace_spent,Calendar pexpDate)
 {
	 EId=pId;
 	 iDescription=PDescription;
 	 iAmount=PAmount;
 	 iSpent_by_participant=PSpent_by_participant;
 	 iIsshared=PIsshared;
 	 iPlace_spent=PPlace_spent;
 	//pexpDate = new Date();
 	iExpenseDateTime=pexpDate;
 	
 	}
/* public BoExpence()
 {
	 iexpDate=aexpDate;
 }*/
 public void setiId(Integer aId)
 {
	 EId=aId;
 }
  

  public Integer getiId()
   {
	   return EId;
   }
 public void setiDescription(String adescription)
 {
	 iDescription=adescription;
 }
  

  public String getiDescription()
   {
	   return iDescription;
   }
 
 /* public void setiDate(Calendar aDate)
  {
	  iExpenseDateTime =aDate;
  }
  public Calendar getiDate()
	{
	    return iExpenseDateTime;
	}*/

  public void setiSpent_by_participant(String aSpent_by_participant)
  {
	  iSpent_by_participant =aSpent_by_participant;
  }
  public   String getiSpent_by_participant()
   {
	   return iSpent_by_participant;
   }
  public void setiIsshared(Boolean aIsshared)
  {
	  iIsshared =aIsshared;
  }
   public Boolean getiIsshared()
   {
	   return iIsshared;
   }
   public void setiPlace_spent(String aPlace_spent)
   {
	   iPlace_spent =aPlace_spent;
   }
   public String getiPlace_spent()
   {
	   return iPlace_spent;
   }
	
  public void setiAmount(float aAmount)
   {
	   iAmount=aAmount;
   }
  public float getiAmount()
   {
	   return iAmount;
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
				
				if (0 == inElement.getNodeName().compareTo("Description"))
				{
					
					iDescription =  inElement.getFirstChild().getNodeValue();
					
				}
				if (0==inNode.getNodeName().compareTo("Date"))
				{
					DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
					try {
						iExpenseDateTime.setTime(dateFormat.parse(inElement.getFirstChild().getNodeValue()));
					} catch (DOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//iDate= new Date(inElement.getFirstChild().getNodeValue());
				}
				if (0 == inElement.getNodeName().compareTo("Amount"))
				{
					iAmount = Float.valueOf( inElement.getFirstChild().getNodeValue());
				}
				if (0 == inElement.getNodeName().compareTo("Spent_by_participant"))
				{
					iSpent_by_participant =  inElement.getFirstChild().getNodeValue();
				}
				
				if (0 == inElement.getNodeName().compareTo("ParticipantId")  )
				{
					EId = Integer.valueOf(inElement.getFirstChild().getNodeValue()).intValue();// = inElement.getFirstChild().getNodeValue().intValue();
				}
				
				if (0 == inElement.getNodeName().compareTo("Isshared"))
				{
					iIsshared = Boolean.valueOf(inElement.getFirstChild().getNodeValue());
				}
				if (0 == inElement.getNodeName().compareTo("Place_spent"))
				{
					iPlace_spent = inElement.getFirstChild().getNodeValue();
				}
				
			}
	}
}
   

void serialize(Document doc,Element aRootElement)
   {
	Element rootElement = doc.createElement("Expence");
	aRootElement.appendChild(rootElement);
	
	if (iDescription != null)
	{
		// Name elements
		Element description = doc.createElement("Description");
		description.appendChild(doc.createTextNode(iDescription));
		rootElement.appendChild(description);
		
	}
	if(iExpenseDateTime!=null)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/mmm/yyyy");
		
		Element date=doc.createElement("Date");
		date.appendChild(doc.createTextNode(dateFormat.format(iExpenseDateTime.getTime())));
		rootElement.appendChild(date);
	}
	if (iAmount != 0.0)
	{
		
		// Name elements
		Element amount = doc.createElement("Amount");
		String test = String.valueOf(iAmount);
		amount.appendChild(doc.createTextNode(String.valueOf(iAmount)));
		rootElement.appendChild(amount);
		
	}
	
	if (iSpent_by_participant != null)
	{
		// Name elements
		Element Spent_by_participant = doc.createElement("Spent_by_participant");
		Spent_by_participant.appendChild(doc.createTextNode(iSpent_by_participant));
		rootElement.appendChild(Spent_by_participant);
		
	}
	
	if (iPlace_spent != null)
	{
		// Name elements
		Element Place_spent = doc.createElement("Place_spent");
		Place_spent.appendChild(doc.createTextNode(iPlace_spent));
		rootElement.appendChild(Place_spent);
		
	}
	if (EId!=0)
	{
		// Name elements
		Element partiid = doc.createElement("ParticipantId");
		partiid.appendChild(doc.createTextNode(String.valueOf(EId)));
		rootElement.appendChild(partiid);
		
	}
	
		// Name elements
		Element sort = doc.createElement("Isshared".toString());
		sort.appendChild(doc.createTextNode(String.valueOf(iIsshared)));
		rootElement.appendChild(sort);
	}
public Calendar getExpenseDateTime() {
	return iExpenseDateTime;
}

public void setExpenseDateTime(Calendar aExpenseDateTime) {
	iExpenseDateTime = aExpenseDateTime;
}
}
