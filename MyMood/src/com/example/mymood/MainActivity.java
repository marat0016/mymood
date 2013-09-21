package com.example.mymood;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.java.core.ConnectToWeb;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

	  ConnectToWeb connect = new ConnectToWeb("http://oprosi.me/tests/getMusicList/2");

	  ListView mListView = (ListView) findViewById(R.id.listView1);
	  
	  try {
		  LinkedList<String[]> list = connect.parseMusicFromXml(connect.getSource(getApplicationContext()));
		  Iterator<String[]> iter = list.iterator();
		  String[] catsList = new String[list.size()];
		  int i = 0;
		  while(iter.hasNext()){
			  catsList[i] = iter.next()[1];
			  i++;
		  } 
		  System.out.println("Hello");
		  ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
		  android.R.layout.simple_list_item_1, catsList);
		  mListView.setAdapter(mAdapter);
	  } catch (IOException e) {
		  e.printStackTrace();
	  }

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
