package com.example.app_initial_package;

import android.view.View;
import android.view.MotionEvent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
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
	private int framerate = 100;
	private static final String TAG = "MyLog";
	private Bitmap woodbm;
	private BitmapShader woodshader;
	private long starttime;
	private float fps = 0.0f;

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
		woodbm = BitmapFactory.decodeResource(getResources(), R.drawable.wood);
		woodshader = new BitmapShader(woodbm,
				TileMode.REPEAT, TileMode.REPEAT);



		// Schedule a redraw
		TimerTask redrawTask = new TimerTask() {
			public void run() {
				postInvalidate();
			}
		};
		mTimer = new Timer(true);
		mTimer.schedule(redrawTask, 0, 1000/*ms*/ / framerate);
		starttime = System.currentTimeMillis();

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

	}

	@Override
	public void onDraw(Canvas canvas) {
		float mUnit = 70.0f; //200.0f;

		paint.setShader(woodshader);
		canvas.drawPaint(paint);

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
		if(++framecount == 20)
		{
			long now = System.currentTimeMillis();
			long delta = now - starttime;
			starttime = now;
			fps = framecount * 1000.0f/ delta;
		}


		canvas.drawText(String.format("Some text: %dx%d fps %.2f",
			width, height, fps),
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
