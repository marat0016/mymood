package com.java.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.mymood.MainActivity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ConnectToWeb {

	public String url;
	
	public ConnectToWeb(String urlConnection){
		this.url = urlConnection;
	}
	
	public String getSource(Context context){
		final StringBuilder a = new StringBuilder();
		try{
			Thread thread = new Thread(new Runnable(){
			    @Override
			    public void run() {
			        try {
				        URL site = new URL(url);
				        URLConnection yc = site.openConnection();
				        BufferedReader in = new BufferedReader(new InputStreamReader(
				                yc.getInputStream()));
				        String inputLine;	        
				        while ((inputLine = in.readLine()) != null)
				            a.append(inputLine);
				        in.close();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			});
			thread.start();
			//∆дем пока данные не загруз€тс€!
			while(thread.isAlive());
		}catch(Exception e){
		}
        return a.toString();
    }
	
	public LinkedList<String[]> parseCatsFromXml(String xml) throws IOException{
		LinkedList<String[]> list = new LinkedList<String[]>();
		try {			
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
         
        	doc.getDocumentElement().normalize();
 
            NodeList musics = doc.getElementsByTagName("cat");
 
            for (int i = 0; i < musics.getLength(); i++) {
            	Node nNode = musics.item(i);
            	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            		Element eElement = (Element) nNode;
            		list.add(new String[]{
            				eElement.getAttribute("id"),
            				eElement.getElementsByTagName("name").item(0).getTextContent()
            		});
            	}
            }            
 
        } catch (Exception e){
        	
        }
		return list;
	}
	
	public LinkedList<String[]> parseMusicFromXml(String xml) throws IOException{
		LinkedList<String[]> list = new LinkedList<String[]>();
        try {
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
         
        	doc.getDocumentElement().normalize();
 
            NodeList musics = doc.getElementsByTagName("music");
 
            for (int i = 0; i < musics.getLength(); i++) {
            	Node nNode = musics.item(i);
            	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            		Element eElement = (Element) nNode;
            		list.add(new String[]{
            				eElement.getAttribute("id"),
            				eElement.getElementsByTagName("url").item(0).getTextContent()
            		});
            	}
            }
 
        } catch (Exception e){
        	
        }
        return list;
	}

}
