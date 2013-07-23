package com.tripman.engine;
    
    import java.io.File;

    import java.io.IOException;
	import java.util.ArrayList;
    import java.util.Date;
	import java.util.Iterator;
	import java.util.List;

	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.ParserConfigurationException;
	import javax.xml.transform.OutputKeys;
	import javax.xml.transform.Transformer;
	import javax.xml.transform.TransformerException;
	import javax.xml.transform.TransformerFactory;
	import javax.xml.transform.dom.DOMSource;
	import javax.xml.transform.stream.StreamResult;

	import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tripman.BoRemaindertype;
	



	public class BoRequisiteManger implements IBoRequisite{
		

		private static int selecteditemindex=-1;
		private  static BoRequisiteManger iInstance = null;
		private static List<BoRequisite> iRequisiteList;
		private BoRemaindertype iReminderType;
	    private Bosearchrequisitetype isearchrequisitetype;
	   // private String sFilePath;
	    private String iTripPath;
	    private String iCurrentRequisiteFile;
		
		public void setslectedindex(int  aIndex)
		{
			selecteditemindex = aIndex;
		}
		
		public int getslectedindex()
		{
			return selecteditemindex;
		}
		
		public static void destoryInstance()
		{
			if(null != iInstance)
			{
				iInstance=null;
				System.gc();
			}
		}
		
		public static BoRequisiteManger getInstance()
		{
			if (null == iInstance)
			{
				iInstance = new BoRequisiteManger();
				try {
					
					iInstance.deserialize(iInstance.iTripPath+"//Requisite.xml");
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return iInstance;
		}
		
		public BoRequisiteManger  getRequisiteMgr()
		{
			return BoRequisiteManger.getInstance();
			
		}
	
		
		
		private BoRequisiteManger()
		{
			iRequisiteList = new ArrayList<BoRequisite>();
			iTripPath=BoTripManager.getInstance().getTripDirectory();
		}
		
		public List<BoRequisite> getAllRequisite(Bosearchrequisitetype searchType)
	    
		{
			
                 if( searchType==Bosearchrequisitetype.Alltype)
		{
		        return iRequisiteList;
		}
                 else if( searchType==Bosearchrequisitetype.mandatorytype)
         		{
         			List<BoRequisite> mandatorytype = new ArrayList<BoRequisite>();
         			//Filter only mandatorytype
         			Iterator<BoRequisite> it=iRequisiteList.iterator();
         			while(it.hasNext())
         			{
         				BoRequisite mandatory=it.next();
         				if(mandatory.getiIsmanditory())
         				{
         					mandatorytype.add((BoRequisite) mandatorytype);
         				}	
         				
         			}
         			return mandatorytype;
         		}
         		else
         		{
         			List<BoRequisite> nonmandatorytype = new ArrayList<BoRequisite>();
         			//Filter only nonmandatorytype
         			return nonmandatorytype;
         		}	
         			
         }
         	
		
		
		public BoRequisite getRequisite(int aRequisiteId)
		{
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				if (req.getiId() == aRequisiteId)
				{
					return req;
				}
			}
			return null;
		}
		
		public BoRequisite getRequisiteAt(int aIndex)
		{
			if(iRequisiteList.size()>=aIndex)
	     	return iRequisiteList.get(aIndex);
			return null;
			
		}
		
		
		
		
		public boolean addRequisite(BoRequisite aRequisite)
		{
			aRequisite.setiId( getNewId()); 
			return iRequisiteList.add(aRequisite);
			
	    }
		
		private int getNewId()
		 {
			  int id = 1;
			  Iterator<BoRequisite> it=iRequisiteList.iterator();
			  while(it.hasNext())
			  {
			   BoRequisite req = it.next();
			   if (req.getiId() >= id)
			   {
			    id = req.getiId()+1;
			   }
			  }
			  return id;
			 }
		
		
		public boolean removeRequisite(int aRequisiteId)
		{
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				if (req.getiId() == aRequisiteId)
				{
					it.remove();
					return true;
				}
			}
			return false;
		}
		
		public int getRequsiteIdAt(int aIndex)
		{
			int count = 0;
			
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				
				if (count == aIndex)
					return req.getiId();
				
				count++;
			}
			return -1;
			
		}
		
		public boolean modifyRequisite(BoRequisite aRequisite)
		{
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				if (req.getiId() == aRequisite.getiId())
				{
					aRequisite.setiId(req.getiId());
					it.remove();
					iRequisiteList.add(aRequisite);
					return true;
				}
			}
			return false;
		}
		public boolean SearchRequisite(BoRequisite aRequisite)
		{
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				if (req.getiId() == aRequisite.getiId())
				{
					//aRequisite.iId = req.iId;
					return true;
				}
			}
			return false;
			
		}
		public List<BoRequisite> getPendingRequisite(Bosearchrequisitetype type, Date deadLineDate)
		{
			List<BoRequisite> pendingRequisite= new ArrayList<BoRequisite>();
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				if (req.getiDaeadline().after(deadLineDate)  && req.getiPendingRequisite())
				{
					pendingRequisite.add(req);
				}
			}
			return pendingRequisite;
		
		}
		public void deserialize(String aPath) throws ParserConfigurationException, SAXException, IOException
		{
			File fXmlFile = new File(aPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("BoRequisite");
			System.out.println("----------------------------");
			
			iRequisiteList.clear();
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				BoRequisite R = new BoRequisite();
				Node nNode = (Node)nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					R.deserialize(doc, eElement);
				}
				
				iRequisiteList.add(R);
			}
		}
		
		
		public void serialize(String aPath) throws TransformerException
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = null;
			try {
				docBuilder = docFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("RequisiteList");
			doc.appendChild(rootElement);
			
			Iterator<BoRequisite> it=iRequisiteList.iterator();
			while(it.hasNext())
			{
				BoRequisite req = it.next();
				req.serialize(doc,rootElement);
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			
			
			File tripDirectory = new File(iTripPath);
			File tripFile = null;
			if(tripDirectory.exists() && tripDirectory.isDirectory())
			{
				tripFile = new File(tripDirectory.getPath()+"//"+"Requisite.xml");
			}
			else
			{
				tripDirectory.mkdir();
				tripFile = new File(tripDirectory.getPath()+"//"+"Requisite.xml");
			}
			
			StreamResult result = new StreamResult(tripFile);
			transformer.transform(source, result);
			System.out.println("File saved!");
		}
		public String getCurrentRequisiteFile() {
			return iCurrentRequisiteFile;
		}
		
	
		
	}



