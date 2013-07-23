package com.tripman.engine;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

//public class IBoRequisite
public interface IBoRequisite
{
	List<BoRequisite>getAllRequisite(Bosearchrequisitetype searchType );
	boolean addRequisite(BoRequisite aRequisite);
	boolean removeRequisite(int aRequisiteId);
	boolean modifyRequisite(BoRequisite aRequisite);
	BoRequisite getRequisite(int aRequisiteId);
	List<BoRequisite>getPendingRequisite(Bosearchrequisitetype type,Date Date);
	
	void serialize(String aPath) throws TransformerException;
	void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException;
	BoRequisiteManger  getRequisiteMgr(); 
}
