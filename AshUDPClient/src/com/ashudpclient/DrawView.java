package com.ashudpclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	String message ="";
	String ServerIp="";
	String PortAddr="";
	String UserNm="";
	Boolean mt=false;
	List<String> list = new ArrayList<String>();
	
	private Timer myTimer;
	
	private static final float STROKE_WIDTH = 5f;

	  /** Need to track this so the dirty region can accommodate the stroke. **/
	  private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

	  private Paint paint = new Paint();
	  private Path path = new Path();

	  /**
	   * Optimizes painting by invalidating the smallest possible area.
	   */
	  private float lastTouchX;
	  private float lastTouchY;
	  private final RectF dirtyRect = new RectF();

	  public DrawView(Context context) {
	    super(context);

	    paint.setAntiAlias(true);
	    paint.setColor(Color.BLACK);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeJoin(Paint.Join.ROUND);
	    paint.setStrokeWidth(STROKE_WIDTH);
	    
	    
	        myTimer = new Timer();
	        myTimer.schedule(new TimerTask() {          
	            @Override
	            public void run() {
	                TimerMethod();
	            }
	        }, 0, 1000);
	  }
	  
	  private void TimerMethod()
	  {
		  message="";
	      //This method is called directly by the timer
	      //and runs in the same thread as the timer.

	      //We call the method that will work with the UI
	      //through the runOnUiThread method.
		
		  try
		  {
			  if(list.size()>0)
			  {
				//  list.add("-100,-100");
				  Iterator<String> iterator = list.iterator();
					while (iterator.hasNext()) {
						message=message + (iterator.next()) + "$";
					}
					list.clear();
					ValidateSendUDP();
					mt=false;
			  }    
		  }catch(Exception e)
		  {
			 
		  }
		  	  
	/*	else
		{
			if(mt==false){
			//message= "-100" + "," + "-100$";
			ValidateSendUDP();
			mt=true;
			}
		  }*/
	  }  
	  
	  /**
	   * Erases the signature.
	   */
	  public void clear() {
	    path.reset();

	    // Repaints the entire view.
	    invalidate();
	  }
	 

	  @Override
	  protected void onDraw(Canvas canvas) {
	    canvas.drawPath(path, paint);
	  }

	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
	    float eventX = event.getX();
	    float eventY = event.getY();

	    switch (event.getAction()) {
	      case MotionEvent.ACTION_DOWN:
	        path.moveTo(eventX, eventY);
	        //list.add("-100,-100");
	       // list.add(eventX + "," + eventY);
	        lastTouchX = eventX;
	        lastTouchY = eventY;
	        // There is no end point yet, so don't waste cycles invalidating.
	        return true;

	      case MotionEvent.ACTION_MOVE:
	      case MotionEvent.ACTION_UP:
	        // Start tracking the dirty region.
	        resetDirtyRect(eventX, eventY);

	        // When the hardware tracks events faster than they are delivered, the
	        // event will contain a history of those skipped points.
	        int historySize = event.getHistorySize();
	        for (int i = 0; i < historySize; i++) {
	          float historicalX = event.getHistoricalX(i);
	          float historicalY = event.getHistoricalY(i);
	          expandDirtyRect(historicalX, historicalY);
	          path.lineTo(historicalX, historicalY);
	          list.add(historicalX + "," + historicalY);
	        }

	        // After replaying history, connect the line to the touch point.
	        path.lineTo(eventX, eventY);
	        list.add(eventX + "," + eventY);        
	       break;

	      default:
	      //  debug("Ignored touch event: " + event.toString());
	        return false;
	    }

	    // Include half the stroke width to avoid clipping.
	    invalidate(
	        (int) (dirtyRect.left - HALF_STROKE_WIDTH),
	        (int) (dirtyRect.top - HALF_STROKE_WIDTH),
	        (int) (dirtyRect.right + HALF_STROKE_WIDTH),
	        (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
	    
	    lastTouchX = eventX;
	    lastTouchY = eventY;

	    return true;
	  }

	  /**
	   * Called when replaying history to ensure the dirty region includes all
	   * points.
	   */
	  private void expandDirtyRect(float historicalX, float historicalY) {
	    if (historicalX < dirtyRect.left) {
	      dirtyRect.left = historicalX;
	    } else if (historicalX > dirtyRect.right) {
	      dirtyRect.right = historicalX;
	    }
	    if (historicalY < dirtyRect.top) {
	      dirtyRect.top = historicalY;
	    } else if (historicalY > dirtyRect.bottom) {
	      dirtyRect.bottom = historicalY;
	    }
	  }

	  /**
	   * Resets the dirty region when the motion event occurs.
	   */
	  private void resetDirtyRect(float eventX, float eventY) {

	    // The lastTouchX and lastTouchY were set when the ACTION_DOWN
	    // motion event occurred.
	    dirtyRect.left = Math.min(lastTouchX, eventX);
	    dirtyRect.right = Math.max(lastTouchX, eventX);
	    dirtyRect.top = Math.min(lastTouchY, eventY);
	    dirtyRect.bottom = Math.max(lastTouchY, eventY);
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
	}

