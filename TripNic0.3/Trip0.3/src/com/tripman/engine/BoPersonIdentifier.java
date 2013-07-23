package com.tripman.engine;

import java.util.Date;

public class BoPersonIdentifier
{
	private String iFirstName;
	private String iMiddleName;
	private String iLastName;
	
	public BoPersonIdentifier()
	{
	    iFirstName = "";
		iMiddleName = "";
		iLastName = "";
	}
	
	public BoPersonIdentifier(String aFirstName,String aMiddleName,String aLastName)
	{
	    iFirstName = aFirstName;
		iMiddleName = aMiddleName;
		iLastName = aLastName;
	}
	
	public void setFirstName(String aFirstName)
	{
		iFirstName = aFirstName;
	}
	
	public String getFirstName()
	{
		return iFirstName;
	}
	
	public void setMiddleName(String aMiddleName)
	{
		iMiddleName = aMiddleName;
	}
	
	public String getMiddleName()
	{
		return iMiddleName;
	}
	
	public void setLastName(String aLastName)
	{
		iLastName = aLastName;
	}
	
	public String getLastName()
	{
		return iLastName;
	}
	
}

/*
Class BoDateToRemember
Container class for date and event name
*/
class BoDateToRemember
{
	private Date iDateToRemember;
	private String iEventName;
	
	
	public BoDateToRemember()
	{
	    iDateToRemember = new Date();
		iEventName = "";
	}
	
	public BoDateToRemember(Date aDateToRem, String aEventName)
	{
	    iDateToRemember = aDateToRem;
		iEventName = aEventName;
	}
	
	public Date GetDate()
	{
	    return iDateToRemember;
	}
	
	public void setDate(Date aDateToRemember)
	{
	     iDateToRemember = aDateToRemember;
	}
	
	public String GetEventName()
	{
	    return iEventName;
	}
	
	public void setEventName(String aEventName)
	{
	     iEventName = aEventName;
	}
}
