package com.ashudpclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DrawActivity extends Activity{
	String message ="";
	String ServerIp="10.201.4.1";
	String PortAddr="1000";
	String UserNm="";
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null) {
		    ServerIp = extras.getString("serverIP");
		    PortAddr = extras.getString("portA");
		    UserNm = extras.getString("userNm");
		    
		    if((ServerIp!="") && (PortAddr!="") && (UserNm!=""))
		    {
		    Toast toast = Toast.makeText(DrawActivity.this,"Connection Details Configured. Start Drawing!", Toast.LENGTH_LONG);
			toast.show();
		    }
		    else{
			Toast toast = Toast.makeText(DrawActivity.this,"Invalid Connection Details!", Toast.LENGTH_LONG);
			toast.show();
		    }
		}
		
		Button TestBtn = (Button) findViewById (R.id.buttonTest);
		TestBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				message="This is a Test Message...";
				ValidateSendUDP();
			}
		});
		
		    
		    DrawView drawView = new DrawView(this);
		    drawView.ServerIp=ServerIp;
		    drawView.PortAddr=PortAddr;
		    drawView.UserNm=UserNm;
		    drawView.setBackgroundColor(Color.WHITE);
	        setContentView(drawView);
	}
	
	
	
	public void ValidateSendUDP() {
		if (ServerIp=="") {return;}
		if (PortAddr=="") {return;}
		if (UserNm=="") {return;}
		if (message=="") {return;}
		
		SendUDP obj=new SendUDP();
    	obj.ServerIp=ServerIp;
    	obj.PortAddr=PortAddr;
    	obj.Command="Message";
    	obj.UserNm=UserNm;
    	obj.message=message;
    	Thread t = new Thread(obj);
        t.start();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
		    case R.id.action_settings:
		    	Intent intent = new Intent(this, MainActivity.class);
		    	intent.putExtra("serverIP", ServerIp);
				intent.putExtra("portA", PortAddr);
				intent.putExtra("userNm", UserNm);
		    	startActivity(intent);
		        return true;
		     default:
		        return super.onOptionsItemSelected(item);
		    }
}
	
	

}
