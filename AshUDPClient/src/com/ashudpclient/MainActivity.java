package com.ashudpclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	String message ="";
	String ServerIp="";
	String PortAddr="";
	String UserNm="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText txtServer = (EditText) findViewById(R.id.editTextServer);
		final EditText txtPort = (EditText) findViewById(R.id.EditTextPort);
		final EditText txtUser = (EditText) findViewById(R.id.EditTextUser);
		final EditText txtMsg = (EditText) findViewById(R.id.EditTextMsg);
		
		Button StartBtn = (Button) findViewById (R.id.buttonStart);
		Button StopBtn = (Button) findViewById (R.id.ButtonStop);
		Button MsgBtn = (Button) findViewById (R.id.ButtonSend);
		Button BackBtn = (Button) findViewById (R.id.ButtonBack);
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null) {
		    ServerIp = extras.getString("serverIP");
		    PortAddr = extras.getString("portA");
		    UserNm = extras.getString("userNm");
		    txtServer.setText(ServerIp);
		    txtPort.setText(PortAddr);
		    txtUser.setText(UserNm);
		}


		StartBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
			ServerIp=txtServer.getText().toString();
			PortAddr=txtPort.getText().toString();
			UserNm=txtUser.getText().toString();
			message=txtMsg.getText().toString();
			
			SendUDP obj=new SendUDP();
        	obj.ServerIp=ServerIp;
        	obj.PortAddr=PortAddr;
        	obj.Command="Login";
        	obj.UserNm=UserNm;
        	obj.message=message;
        	Thread t = new Thread(obj);
            t.start();
            
            Toast toast = Toast.makeText(MainActivity.this,"Register clicked", Toast.LENGTH_LONG);
			toast.show();
			}
        });

	
		StopBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
			ServerIp=txtServer.getText().toString();
			PortAddr=txtPort.getText().toString();
			UserNm=txtUser.getText().toString();
			message=txtMsg.getText().toString();
			
			SendUDP obj=new SendUDP();
        	obj.ServerIp=ServerIp;
        	obj.PortAddr=PortAddr;
        	obj.Command="Logout";
        	obj.UserNm=UserNm;
        	obj.message=message;
        	Thread t = new Thread(obj);
            t.start();
            
            Toast toast = Toast.makeText(MainActivity.this,"Stop clicked", Toast.LENGTH_LONG);
			toast.show();
			} });
		
		
		MsgBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
			ServerIp=txtServer.getText().toString();
			PortAddr=txtPort.getText().toString();
			UserNm=txtUser.getText().toString();
			message=txtMsg.getText().toString();
			txtMsg.setText("");
			
			SendUDP obj=new SendUDP();
        	obj.ServerIp=ServerIp;
        	obj.PortAddr=PortAddr;
        	obj.Command="Message";
        	obj.UserNm=UserNm;
        	obj.message=message;
        	Thread t = new Thread(obj);
            t.start();
            
            Toast toast = Toast.makeText(MainActivity.this,"Test clicked", Toast.LENGTH_LONG);
			toast.show();
			}
        });
		
		
		BackBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, DrawActivity.class);
				intent.putExtra("serverIP", ServerIp);
				intent.putExtra("portA", PortAddr);
				intent.putExtra("userNm", UserNm);
				startActivity(intent);
			}
        });
				
	}
	
	@Override
	public void onBackPressed() {
	}
}
