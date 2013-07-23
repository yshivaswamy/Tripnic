package com.tripman.engine;

public class BoAddress
{	
	
	private String iStreet;
	private String iDoorNum;
	private String iArea;
	private String iCity;
	private String iState;
	private String iPin;

	public BoAddress()
	{
	    iStreet = "";
		iDoorNum = "";
		iArea = "";
		iCity = "";
		iState = "";
		iPin = "";
	}
	
	public BoAddress(String aStreet,String aDoorNum,String aArea,String aCity,String aState,String aPin)

	{
	    iStreet = aStreet;
		iDoorNum = aDoorNum;
		iArea = aArea;
		iCity = aCity;
		iState = aState;
		iPin = aPin;
	} 
	
	public void setStreet(String aStreet)
	{
		iStreet = aStreet;
	}
	public String getStreet()
	{
		return iStreet;
	}
	
	public void setDoorNum(String aDoorNum)
	{
		iDoorNum = aDoorNum;
	}

	public String getDoorNum()
	{
		return iDoorNum;
	}

	public String getArea()
	{
		return iArea;
	}
	
	public void setArea(String aArea)
	{
		iArea = aArea;
	}
	
	public String getCity()
	{
		return iCity;
	}
	
	public void setCity(String aCity)
	{
		iCity = aCity;
	}
	
	public String getState()
	{
		return iState;
	}
	
	public void setState(String aState)
	{
		iState = aState; 
	}
	
	public String getPin()
	{
		return iPin;
	}
	
	public void setPin(String aPin) 
	{
		iPin = aPin;
	}
}

