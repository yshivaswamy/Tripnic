package com.tripman.engine;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;


public interface IBoVisitManager 
{
	boolean addVisit( BoVisit aVisit);
	boolean removeVisit(int pId);
	boolean modifyVisit(BoVisit mVisit);
	BoVisit getVisit(int PId);
    List<BoVisit> getAllVisit();
    void serialize(String aPath) throws TransformerException;
	void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException;
	BoVisitManager getVisitmgr();
  //  BoVisit serialize();
  //  BoVisit deserilize();
    
}