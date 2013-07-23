package com.tripman.engine;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public  interface IBoParticipant {
	
	
	public List<BoParticipant> getAllParticipant();
	public BoParticipant getParticipant(int aParticipantId);
	public boolean addParticipant(BoParticipant aParticipant);
	public boolean deleteParticipant(int aParticipantId);
	public boolean modifyParticipant(BoParticipant aParticipant);
	public void deserialize(String path) throws ParserConfigurationException, SAXException, IOException;
	public void serialize(String path) throws TransformerException;
	

}
