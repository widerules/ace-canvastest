package com.example.app_initial_package;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.graphics.Point;
import java.lang.System;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;

public class app_initial extends Activity
{
	private rtest myrtest;
	private static final String TAG = "MyLog";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		if(false)
		{
			Properties props = System.getProperties();
			Set set = props.stringPropertyNames();
			Iterator it = set.iterator();
			while(it.hasNext())
			{
				Object v = it.next();
				Log.d(TAG, "something: " + v + "=" + System.getProperty(v.toString()));
			}
		}
//Log.d(TAG, "

//		myrtest = (rtest) findViewById(R.id.r_test);
DisplayMetrics metrics = new DisplayMetrics();
Display d = getWindowManager().getDefaultDisplay();
d.getMetrics(metrics);
Log.d(TAG, "density=" + metrics.density);
Log.d(TAG, "densityDpi=" + metrics.densityDpi);
Log.d(TAG, "widthPixels=" + metrics.widthPixels);
Log.d(TAG, "heightPixels=" + metrics.heightPixels);
Log.d(TAG, "scaledDensity=" + metrics.scaledDensity);
Log.d(TAG, "xdpi=" + metrics.xdpi);
Log.d(TAG, "ydpi=" + metrics.ydpi);

Point p = new Point();
d.getSize(p);
Log.d(TAG, "p.x = " + p.x);
Log.d(TAG, "p.y = " + p.y);
Log.d(TAG, "refreshrate = " + d.getRefreshRate());

	}
}
