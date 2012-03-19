package com.example.app_initial_package;

import android.view.View;
import android.view.MotionEvent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.graphics.RadialGradient;
import android.util.AttributeSet;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class rtest extends View {
	private Paint paint;
	private int size;
	private float centerx = 21, centery = 21;
	private int framecount = 0;
	private Timer mTimer;
	private int width, height;
	private float mBallX = 0;
	private float mBallY = 0;
	private float bx2 = 0, by2 = 0;
	private int framerate = 60;
	private Drawable wood;
	private static final String TAG = "MyLog";

	public rtest(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Set up default Paint values
		paint = new Paint();
		paint.setAntiAlias(true);

		// Calculate geometry
		int w = getWidth();
		int h = getHeight();
		size = Math.min(w, h);
		width = w;
		height = h;
		centerx = w / 2;
		centery = h / 2;
		wood=this.getResources().getDrawable(R.drawable.wood);
//		wood=this.getResources().getDrawable(R.drawable.boxtest);

		// Schedule a redraw
		TimerTask redrawTask = new TimerTask() {
			public void run() {
				postInvalidate();
			}
		};
		mTimer = new Timer(true);
		mTimer.schedule(redrawTask, 0, 1000/*ms*/ / framerate);


	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		width = w;
		height = h;
		centerx = w * .5f;
		centery = h * .5f;
		mBallX = centerx;
		mBallY = centery;
		bx2 = centerx;
		by2 = centery;
		int sw = wood.getIntrinsicWidth();
		int sh = wood.getIntrinsicHeight();
		int tw = sw, th = sh;
		if(w!=0 && h!=0)
		{
			tw = sh * w / h;
			th = sw * h / w;
			if(tw > sw)
				tw = sw;
			else
				th = sh;
		}
		int ox, oy;
		ox = (sw - tw) / 2;
		oy = (sh - th) / 2;

		ox = ox * w / tw;
		oy = oy * h / th;

		wood.setBounds(-ox, -oy, w+ox, h+oy);

Log.d(TAG, "ox, oy = " + ox + "," + oy);


	}

	@Override
	public void onDraw(Canvas canvas) {
		float mUnit = 70.0f; //200.0f;

//wood.draw(canvas);
		for(int i=0;i<4;++i)
		{
			int color1, color2;

			float tx = (i&1)==1 ? mBallX : bx2;
			float ty = (i&1)==1 ? mBallY : by2;

			if((i&2)==0)
			{
				float sf = .2f * mUnit;
				tx += sf;
				ty += sf;
				color1 = color2 = 0x40000000;

			} else
			{
				color1 = (i&1)==1 ? getResources().getColor(R.color.ball_highlight) :
						0xffff0000;
				color2 = getResources().getColor(R.color.ball_shadow);
			}


			paint.setShader(new RadialGradient(
				tx - 0.05f * mUnit,
				ty - 0.05f * mUnit,
				mUnit * 0.35f,
				color1,
				color2,
				TileMode.MIRROR));

			paint.setStyle(Style.FILL);
			canvas.drawCircle(
				tx,
				ty,
				mUnit * .4f,
				paint);
			paint.setShader(null);
		}
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		++framecount;

		canvas.drawText(String.format("Some text: %dx%d frame %.2f",
			width, height, framecount/(float)framerate),
			 15, 10, paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int c = e.getPointerCount();
		for(int i = 0;i<c;++i)
		{
			int id = e.getPointerId(i);
			if(id>1) continue;
			float x, y;
			x = e.getX(i);
			y = e.getY(i);
			if(id==0)
			{
				mBallX = x;
				mBallY = y;
			} else
			{
				bx2 = x;
				by2 = y;
			}
		}
		return true;
	}

}
