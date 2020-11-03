package code;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.w3c.dom.Element;
//import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ScraperThread implements Runnable {

	private volatile Boolean running = true;
	
	DoubleProperty progressProperty;
	
	StringProperty stringProperty;
	
	String givenURL = "http://animeburgers.tumblr.com/";
	String docPath = ((new File(System.getProperty("user.home"), "Desktop")).getAbsolutePath());
	
	

	public ScraperThread(String url, String filePath) {
		this.givenURL = (url != null) ? url : givenURL;
		this.docPath = (filePath != null) ? filePath : docPath;
		System.out.println("docPath: "+docPath);
		stringProperty = new SimpleStringProperty("");
		progressProperty = new SimpleDoubleProperty(0);	
	}
	
	public void stopRunning()
	{
		running = false;
	}
	
	
	
	@Override
	public void run() {
		try {
			progressProperty.set(0);
			
		while (running) {
				// TODO Auto-generated method stub
				int totalPosts = 0;
				Random rand = new Random();
				String folderName = "";
				for (int i = 0; i <= 4; i++)
					folderName += (char) (rand.nextInt(25) + 97);
				docPath += "/" + folderName;
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db;
				File inFolder = new File(docPath);
				inFolder.mkdir();
				String path = inFolder.getAbsolutePath();
				System.out.println("Folder path: " + path);
				try {
					URL howMany = new URL(givenURL + "nope");
					db = dbf.newDocumentBuilder();
					Document owo = db.parse(howMany.openStream());
					NodeList nopeList = owo.getElementsByTagName("posts");
					Element el = (Element) nopeList.item(0);
					totalPosts = Integer.parseInt(el.getAttribute("total"));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(totalPosts);

				HashSet<String> hash = new HashSet<String>();
				
				int start = 0;
				int num = 30;
				Document doc = null;
				db = dbf.newDocumentBuilder();
				while (start < totalPosts) {
					doc = null;
					try {
						URL xURL = new URL(givenURL + "nope");
						doc = db.parse(xURL.openStream());
					} catch (Exception e) {
						System.out.println("Problem generating xmls");
						e.printStackTrace();
					}
					if (doc == null)
						System.out.println("Error: Created docuement was null");
					else {
						NodeList nodeList = doc.getElementsByTagName("photo-url");
						for (int i = 0; i < nodeList.getLength(); i = i + 6) {
							String listText = nodeList.item(i).getTextContent();
							if (!hash.contains(listText)) {
								hash.add(listText);
								
							}
						}
					}
					stringProperty.set("Crawling Images");
					progressProperty.set((double) start/totalPosts);
					start = start + num;
				}
				
				
				
				progressProperty.set(0);
				stringProperty.set("xml gen");
				
				start = 0;
				while (start < totalPosts) {
					doc = null;
					try {
						URL xURL = new URL(givenURL + "nope");
						doc = db.parse(xURL.openStream());
					} catch (Exception e) {
						System.out.println("Problem generating xmls");
						e.printStackTrace();
					}
					if (doc == null)
						System.out.println("Error: Created docuement was null");
					else {
						NodeList nodeList = doc.getElementsByTagName("photo-url");
						for (int i = 0; i < nodeList.getLength(); i = i + 6) {
							String listText = nodeList.item(i).getTextContent();
							if (!hash.contains(listText)) {
								hash.add(listText);
								
							}
						}
						
						
						NodeList nodeList = doc.getElementsByTagName("video-player");
						
						for (int i = 0; i < nodeList.getLength(); i = i +1) {
							String nl = nodeList.item(i).getAttributes().getNamedItem("src").getTextContent();
							if (nl!=null) System.out.println(nl);
						}
						
					}
					stringProperty.set("Crawling Videos");
					progressProperty.set((double) start/totalPosts);
					start = start + num;
				}
				
				progressProperty.set(0);
				

				stringProperty.set("save crawl img");
				
				int i = 0;
				ArrayList<String> imageURLS = new ArrayList<String>(hash);
				totalPosts = imageURLS.size();
				System.out.println(totalPosts);
				InputStream instream = null;
				String fileType = null;
				for (String s : imageURLS) {
					try {
						fileType = s.substring(s.lastIndexOf(".") + 1);
						instream = new URL(s).openStream();
						Files.copy(instream, Paths.get(path.concat("/").concat(Integer.toString(i)).concat(".").concat(fileType)));
					} catch (Exception ex) {
						System.out.println("its broke m8");
					}
					i++;
					progressProperty.set((double) i/totalPosts);
				}
				
				instream.close();
			stopRunning();
            }
		}  catch (Exception e) {
            System.out.println("InterruptedException occursad");
        }
		System.exit(0);
	}

}
