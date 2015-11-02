
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.EventListenerList;


class Vendor//Publisher
{
	protected EventListenerList listenerList = new EventListenerList();

	  public void addMyEventListener(BrokerListener listener) {
	    listenerList.add(BrokerListener.class, listener);
	  }
	  public void removeMyEventListener(BrokerListener listener) {
	    listenerList.remove(BrokerListener.class, listener);
	  }
	  void fireMyEvent(AddProductEvent evt) {
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == BrokerListener.class) {
	        ((BrokerListener) listeners[i+1]).OnPublish();
	      }
	    }
	  }
	
	  
	public void publishProduct(String VendorName, String ProductType, String ProductName)
	{
		//fire event
	}
}
class Consumer//Subscriber
{
	public String SubscriberName;
	public void update(String SubName, String ProductName, String Vendor)
	{
		System.out.println(SubName+" notified of "+ProductName+" from "+Vendor);
	}
	
}
class Product
{
	public String ProductName;
	public String ProductType;
	
}
class AddProductEvent extends EventObject//may be AddNewProduct
{
	
		private String productName;
		private String vendorName;
			public String getProductName() 
			{ 
		 		return this.productName;
		  	}
			public String getVendorName() 
			{ 
		 		return this.vendorName;
		  	}
			// event constructor to set instance property
		 	public AddProductEvent(Object source, String productName, String vendorName)
		 	{
		 		super(source);
		 		this.productName = productName;
		 		this.vendorName=vendorName;
	
		 	}
}
//BrokerListener
interface BrokerListener extends EventListener {
	//void AddProduct(PublishProductEvent PublishProductEvent);
  public void OnPublish();
  public void OnSubscribe();
  public void OnUnsubscribe();
}


class Broker implements BrokerListener// Provides Notification Service
{	
	Product p;
	Consumer s;
	public Map<Consumer,Product> map = new HashMap<Consumer,Product>();
	public void OnPublish()
	{
		/* Iterator it = map.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        if(pair.getKey()==s)
		        {
		        	
		        }
		        it.remove(); // avoids a ConcurrentModificationException
		    }*/
		//call update method in subscriber
	}
	  public void OnSubscribe()
	  {
		  map.put(s,p);
	  }
	  public void OnUnsubscribe()
	  {
		  
		 // map.remove(s);
	  }
	
}

class ConsumerVEndorNotification
{
	public static void main(String[] args)
	{
		Broker listener=new Broker();
		Vendor vendor=new Vendor();
	    vendor.addMyEventListener(listener); 
	   

		
		
		
		
		
		File file = new File(args[0]);
		BufferedReader br;
		String line="";
		String csvSplit = ",";
		try {
			 
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
	 
			        // use comma as separator
				String[] keyWords = line.split(csvSplit);
				if(keyWords[0]"Publish")
				{
					vendor.publishProduct(keyWords[1],keyWords[2],keyWords[3]);
				}
				else if(keyWords[0]=="Subscribe")
				{
					listener.OnSubscribe();
				}
				else if(keyWords[0]=="Unsubscribe")
				{	
					
					listener.OnUnsubscribe();
				}
				else
				{
					System.out.println("input not recognized");
				}
	 
			}
	 
		}catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		
	}
}
}


  
