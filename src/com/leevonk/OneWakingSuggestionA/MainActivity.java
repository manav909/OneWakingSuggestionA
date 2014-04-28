package com.leevonk.OneWakingSuggestionA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener,
		SensorEventListener {
	MediaPlayer mediaPlayer;
	Button button;
	View LeeTest;
	 Rect rect;
	int touchCount;
	private Paint pain = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Context context;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private TextView timelabel;
	private Handler mHandler;
	Runnable run;
	float[] linear_acceleration = new float[3];
	float[] gravity = new float[3];
	float[] distanceInMeter = new float[3];
	float[] speed = new float[3];
	long timeStamp;
	float dT;
	int touch=0;
	private boolean firstTimeOfMeasurement = false;
	private float mLastX, mLastY, mLastZ;
	private final float NOISE = (float) 3.0;
    int count=0;
	int pp;
	public SeekBar seekBar;
	public TextView textView;
	Button buttonA;
	int score[] = new int[10];
	int scoreNum;
	int VibrateTime;
	int DelayPreVibrate;
	int DelayHandPost;
	int Improvement;
	int counter;
	Float X1;
	Float X2;
	Float X3;
	Float X4;
	Float Y1;
	Float Y2;
	Float Y3;
	Float Y4;
	Canvas canv;
	String ImprovementString;
	HashMap<String, Float> coordinates1 = new HashMap<String, Float>();
	HashMap<String, Float> coordinates2 = new HashMap<String, Float>();
	ArrayList<HashMap<String, Float>> coordinate_list1 = new ArrayList<HashMap<String, Float>>();
	ArrayList<HashMap<String, Float>> coordinate_list2 = new ArrayList<HashMap<String, Float>>();
	String Debug1;
	String Debug2;
	int Test1;
	int count_toast=0;
	int radius;
	String mp3Array[] = new String[10];
	int DiagramArray[] = new int[10];
	int xCoord;
	int yCoord;
	float mAccel;
	String VisCurrStep[] = new String[15];
	String AudCurrStep[] = new String[15];
	int ImageDisplayNum;
	int DrawImageNum;
	int mp3Num;
	String TextArray[] = new String[15];
	int TextNum;
	String VibrateCurrStep[] = new String[15];
	String BCurrStep[] = new String[15];
	int x;
	Float tot_dist=0f;
	int xxx = 0;
	int TotalScore;
	String PerformanceFeedback;
	final int MAX_NUMBER_OF_POINT = 10;
	float[] xx = new float[MAX_NUMBER_OF_POINT];
	float[] yy = new float[MAX_NUMBER_OF_POINT];
	boolean[] touching = new boolean[MAX_NUMBER_OF_POINT];
	double finger1;
	double finger2;
	final Handler handler = new Handler();
	Timer t = new Timer();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scoreNum = 0;
		score[0] = 0;
		score[1] = 0;
		score[2] = 0;
		score[3] = 0;
		TotalScore = 0;
		VibrateTime = 9000; // 9000
		DelayPreVibrate = 5000; // 5000
		DelayHandPost = 400; // 4000
		ImprovementString = "";
		Debug1 = "";
		Debug2 = "";
		Test1 = 99;
		ImageDisplayNum = 0;
		DrawImageNum = 0;
		mp3Num = 0;
		TextNum = 0;
		pp = 0;
		PerformanceFeedback = "";

		// ---------------------------
		// --set up full screen mode--
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// --set media volume to max--
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
				AudioManager.FLAG_SHOW_UI);

		// --intro screen button--
		button = new Button(this);
		button.setText("M y n d");
		button.setTextSize(50);
		// button.setTextColor(Color.RED);
		button.setOnClickListener(this);
		setContentView(button);

		// ===============================================
		// ===========--set up flow--=====================
		// ===============================================
		x = 0;

		VisCurrStep[x] = "";
		AudCurrStep[x] = "";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Paragraph";
		x++;
		VisCurrStep[x] = "";
		AudCurrStep[x] = "";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
		x++;
		// ===============================================
		VisCurrStep[x] = "ImageDisplay";
		AudCurrStep[x] = "mp3";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
		x++;
		VisCurrStep[x] = "DrawImage";
		AudCurrStep[x] = "mp3";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
		x++;
		VisCurrStep[x] = "SliderDisplay";
		AudCurrStep[x] = "";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "0";
		x++;
		// ===============================================
		VisCurrStep[x] = "";
		AudCurrStep[x] = "";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
		x++;
		VisCurrStep[x] = "ImageDisplay";
		AudCurrStep[x] = "mp3";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
		x++;
		VisCurrStep[x] = "DrawImage";
		AudCurrStep[x] = "mp3";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
		x++;
		VisCurrStep[x] = "SliderDisplay";
		AudCurrStep[x] = "";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "0";
		x++;
		// ===============================================
		VisCurrStep[x] = "";
		AudCurrStep[x] = "";
		VibrateCurrStep[x] = "0";
		BCurrStep[x] = "Center";
	}

	// ==============================================================
	// ===========--set up Main Button Actions--=====================
	// ==============================================================
	public void onClick(View vv) {
		TotalScore = score[1] + score[2] + score[3];

		DiagramArray[0] = R.drawable.holdfingerlocktest;
		DiagramArray[1] = R.drawable.holdheavytest;
		// DiagramArray[2] = R.drawable.holdfingerlocktest;

		mp3Array[0] = "HandPositioning.mp3";
		mp3Array[1] = "3Finloc1.MP3";
		mp3Array[2] = "HandPositioning.mp3";
		mp3Array[3] = "1Barbell1.MP3";
		// mp3Array[4] = "HandPositioning.mp3";
		// mp3Array[5] = "3Finloc1.MP3";

		/*
		 * mp3Array[0] = "bip.mp3"; mp3Array[1] = "bip.mp3"; mp3Array[2] =
		 * "bip.mp3"; mp3Array[3] = "bip.mp3"; mp3Array[4] = "bip.mp3";
		 * mp3Array[5] = "bip.mp3";
		 */

		TextArray[0] = "\nMynd will exercise your brain's control over its best tool, your body. "
				+ "These exercises will allow your mind to control things like sleep patterns, energy levels"
				+ "etc. that may have previously seemed uncontrollable.\n\n"
				+ "These abilities can be learned through practice. We will start with basic exercises and work "
				+ "our way up as you improve. \n\nDon't be discouraged if at first you are not successful at some "
				+ "of the exercises. Simply follow the instructions and use your imagination as described.";
		TextArray[1] = "First Practice Session";
		TextArray[2] = "Let's Start";
		TextArray[3] = "How did you Do?";
		TextArray[4] = "Next Exercise";
		TextArray[5] = "Let's do it";
		TextArray[6] = "How'd you Do?";
		TextArray[7] = "Done! Your scores were: " + score[1] + ", " + score[2]
				+ "\n\n Total Score = " + TotalScore + " / 200"
				+ PerformanceFeedback + "\n First finger distance=" + finger1
				+ "\n Second finger distance=" + finger2;
		// TextArray[8] = "Here we go";
		// TextArray[9] = "How did you Do?";
		if (TotalScore > 100 & TotalScore < 150) {
			PerformanceFeedback = "\n\nNot Bad!";
		}
		if (TotalScore > 150) {
			PerformanceFeedback = "\n\nYour Doing Great!";
		}
		if (TotalScore < 50) {
			PerformanceFeedback = "\n\nNot Bad!\nYou'll keep getting better with practice!";
		}
		// TextArray[10] =

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mediaPlayer = new MediaPlayer();
		// #######################################
		// #######---Do Images if Needed---#######
		// #######################################

		class MyView extends View {
			// public MyView(Context context) { super(context); }
			// public MyView() { }
			public MyView(Context context, AttributeSet attrs, int defStyle) {
				super(context, attrs, defStyle);
				init();
			}

			public MyView(Context context, AttributeSet attrs) {
				super(context, attrs);
				init();
			}

			public MyView(Context context) {
				super(context);
				init();
			}

			void init() {
				pain.setStyle(Paint.Style.STROKE);
				pain.setStrokeWidth(1);
				pain.setColor(Color.RED);
			}

			@Override
			public void onDraw(final Canvas canvas) {
				int x = getWidth();
				int y = getHeight();

				// display .png images if needed
				if (VisCurrStep[pp] == "ImageDisplay"
						&& DiagramArray.length > ImageDisplayNum) {
					int mWidth;
					int mHeight;
					mWidth = View.MeasureSpec.getSize(getWidth());
					mHeight = View.MeasureSpec.getSize(getHeight());
					Bitmap myBitmap = BitmapFactory.decodeResource(
							getResources(), DiagramArray[ImageDisplayNum]);
					int cx = (mWidth - myBitmap.getWidth()) / 2;
					int cy = (mHeight - myBitmap.getHeight()) / 2;
					canvas.drawBitmap(myBitmap, cx, cy, null);
                
				}

				// draw android-drawn circles if needed
				if (VisCurrStep[pp] == "DrawImage") {
					super.onDraw(canvas);
					sensorManager = (SensorManager) MainActivity.this
							.getSystemService(Context.SENSOR_SERVICE);
					accelerometer = sensorManager
							.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
					sensorManager.registerListener(MainActivity.this,
							accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
					radius = 0;
					final Paint paint = new Paint();
					paint.setStyle(Paint.Style.FILL);
					paint.setColor(Color.WHITE);
					canvas.drawPaint(paint);
					paint.setColor(Color.parseColor("#CD5C5C"));
					xCoord = x / 2;
					yCoord = y / 2;
					if (DrawImageNum == 0) {
						radius = 20;
					} // heavy circle
					if (DrawImageNum == 1) {
						radius = 100;
						
						t.scheduleAtFixedRate(new TimerTask() {

							@Override
							public void run() {
	                    System.out.println("-------------------------"+22222);
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										System.out.println("-------------------------------111");
										if(count_toast<20){
										Toast.makeText(getApplicationContext(),"total distance moved="+tot_dist/100,Toast.LENGTH_LONG).show();
										count_toast++;
									}
									}
								});
							}
						}, 2000, 10000);
					} // expanding circle
					// if(DrawImageNum == 2) { radius = 20; } //suction circle
					canvas.drawCircle(xCoord, yCoord, radius, paint);
					final Paint pai = new Paint();
					pai.setColor(Color.BLACK);
					pai.setTextAlign(Align.CENTER);
					pai.setTypeface(Typeface.DEFAULT_BOLD);
					pai.setTextSize(55);
					FontMetrics fm = new FontMetrics();
		            pai.setTextAlign(Paint.Align.CENTER);
		            pai.getFontMetrics(fm);
		      
					for (int i = 0; i < MAX_NUMBER_OF_POINT; i++) {
						if (touching[i]) {
							canvas.drawCircle(xx[i], yy[i], 70f, pain);
							super.onDraw(canvas);
						}
					}
					// DrawImageNum++;
				}
			}

			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
				// TODO Auto-generated method stub
				setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
						MeasureSpec.getSize(heightMeasureSpec));
			}

			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				int action = (event.getAction() & MotionEvent.ACTION_MASK);
				int pointCount = event.getPointerCount();

				for (int i = 0; i < pointCount; i++) {
					int id = event.getPointerId(i);
					System.out.println("------" + id);
					// Ignore pointer higher than our max.
					if (id < MAX_NUMBER_OF_POINT) {
						// x[id] = (int)event.getX(i);
						// y[id] = (int)event.getY(i);
						xx[id] = (int) event.getX(i);
						yy[id] = (int) event.getY(i);
						if ((action == MotionEvent.ACTION_DOWN)
								|| (action == MotionEvent.ACTION_POINTER_DOWN)) {

							if (id == 0) {
								coordinates1.put("x" + id, event.getX(i));
								coordinates1.put("y" + id, event.getY(i));
								coordinate_list1.add(coordinates1);
								HashMap<String, Float> hashX1 = coordinate_list1
										.get(0);
								X1 = hashX1.get("x0");
								Y1 = hashX1.get("y0");
							} else if (id == 1) {
								coordinates1.put("x" + id, event.getX(i));
								coordinates1.put("y" + id, event.getY(i));
								coordinate_list1.add(coordinates1);
								HashMap<String, Float> hashX3 = coordinate_list1
										.get(1);
								X3 = hashX3.get("x1");
								Y3 = hashX3.get("y1");
							}
							touching[id] = true;
						} else if ((action == MotionEvent.ACTION_UP)
								|| (action == MotionEvent.ACTION_POINTER_UP)) {

							if (id == 0) {
								coordinates2.put("x" + id, event.getX(i));
								coordinates2.put("y" + id, event.getY(i));
								coordinate_list2.add(coordinates2);
								HashMap<String, Float> hashX2 = coordinate_list2
										.get(0);
								X2 = hashX2.get("x0");
								Y2 = hashX2.get("y0");
								System.out.println("first finger"
										+ Math.sqrt(Math.pow(X1 - X2, 2)
												+ Math.pow(Y1 - Y2, 2)));
								finger1 = Math.sqrt(Math.pow(X1 - X2, 2)
										+ Math.pow(Y1 - Y2, 2));
							} else if (id == 1) {
								coordinates2.put("x" + id, event.getX(i));
								coordinates2.put("y" + id, event.getY(i));
								coordinate_list2.add(coordinates2);
								HashMap<String, Float> hashX4 = coordinate_list2
										.get(1);
								X4 = hashX4.get("x1");
								Y4 = hashX4.get("y1");
								System.out.println("second  finger"
										+ Math.sqrt(Math.pow(X3 - X4, 2)
												+ Math.pow(Y3 - Y4, 2)));
								finger2 = Math.sqrt(Math.pow(X3 - X4, 2)
										+ Math.pow(Y3 - Y4, 2));
							}
							touching[id] = false;
						}
					}
				}
				invalidate();
				return true;
			}
		}

		setContentView(new MyView(this));

		if (VisCurrStep[pp] == "SliderDisplay") {
			scoreNum++;
			// ----Seek Bar (slider Bar)-----
			setContentView(R.layout.activity_main);
	       
			seekBar = (SeekBar) findViewById(R.id.seekBar1);
			textView = (TextView) findViewById(R.id.textView1);
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				int progress = 0;

				@Override
				public void onProgressChanged(SeekBar seekBar,
						int progressValue, boolean fromUser) {
					progress = progressValue;
					score[scoreNum] = progress;
					// Test1= progress;
					// Debug1 = Integer.toString(progress);
					// Log.d("progress---->",Debug1);
					// Log.d("Progress test", "Progress is " + progressValue);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});
			textView.setText("\nRate how well you think you performed that exercise!\n"
					+ "(Left=Low / Right=High)");
			textView.setTextSize(19);
			addListenerOnButton();
		}

		// #######################################
		// ########---Do Audio if Needed---#######
		// #######################################

		if (AudCurrStep[pp] == "mp3") {
			try {
				AssetManager assetManager = getAssets();
				AssetFileDescriptor descriptor = assetManager
						.openFd(mp3Array[mp3Num]);
				mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
						descriptor.getStartOffset(), descriptor.getLength());
				mediaPlayer.prepare();
				mediaPlayer.setLooping(false);
				mp3Num++;
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}

		mediaPlayer.start();

		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					public void onCompletion(MediaPlayer player) {
						mediaPlayer.stop();

						// #######################################
						// ########---Do Pauses if Needed---#######
						// #######################################
						// ---Hand Positioning Pauses---
						if (VisCurrStep[pp] == "ImageDisplay") {
							try {
								Thread.sleep(DelayHandPost);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
						}

						// ----Vibrate---
						if (VibrateCurrStep[pp] == "1") {
							try {
								Thread.sleep(DelayPreVibrate);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
							v.vibrate(VibrateTime);
						}
						// #######################################
						// ########---Do Text if Needed---########
						// #######################################
						if (BCurrStep[pp] == "Paragraph") {
							//Toast.makeText(getApplicationContext(),TextNum,Toast.LENGTH_LONG).show();
							if(TextNum> 5 && t!=null){
								t.cancel();
								t.purge();
								t=null;
							}
							button.setText(TextArray[TextNum]);
							button.setTextSize(19);
							button.setGravity(Gravity.LEFT);
							//Toast.makeText(getApplicationContext(),"in paragraph"+TextNum,Toast.LENGTH_LONG).show();
							Log.e("num",""+TextNum);
							TextNum++;
							setContentView(button);
						}
						if (BCurrStep[pp] == "Center") {
							//Toast.makeText(getApplicationContext(),TextNum,Toast.LENGTH_LONG).show();
							if(TextNum> 5 && t!=null){
								t.cancel();
								t.purge();
								t=null;
							}
							button.setText(TextArray[TextNum]);
							button.setTextSize(25);
							button.setGravity(Gravity.CENTER);
							//Toast.makeText(getApplicationContext(),"in center"+TextNum,Toast.LENGTH_LONG).show();
							Log.e("num",""+TextNum);
							TextNum++;
							setContentView(button);
						}

						pp++;
					}
				});

	}

	// ##########--button below slider bar--###########
	public void addListenerOnButton() {
		buttonA = (Button) findViewById(R.id.button1);
		buttonA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(touch==0){
					touch++;
				}
				else if (touch==1){
					touch=0;
					Intent intentEmail = new Intent(Intent.ACTION_SEND);
					intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"leevonk@gmail.com"});
					intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Mynd Scores");
					intentEmail.putExtra(Intent.EXTRA_TEXT,"\n First finger distance=" + finger1
							+ "\n Second finger distance=" + finger2+"\n Total distance moved="+tot_dist/100);
					intentEmail.setType("message/rfc822");
					startActivity(Intent.createChooser(intentEmail, "Choose an email provider :"));
				}
				DrawImageNum++;
				ImageDisplayNum++;
				button.performClick(); // call the main button (below)
			}
		});
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

			if (!firstTimeOfMeasurement) {
				firstTimeOfMeasurement = true;
				timeStamp = event.timestamp;
				speed[0] = speed[1] = speed[2] = 0;
				distanceInMeter[0] = distanceInMeter[1] = distanceInMeter[2] = 0;

			} else {
				dT = (event.timestamp - timeStamp) / 1000000000.0f;
				timeStamp = event.timestamp;
				calculateDistance(event.values, dT);
				// Log.d("","X: " + Float.toString(distanceInMeter[0]) + " Y: "
				// + Float.toString(distanceInMeter[1]) + " Z : " +
				// Float.toString(distanceInMeter[2]));
				tot_dist=tot_dist+distanceInMeter[1];
				Log.d("distance y", "" + Float.toString(distanceInMeter[1]));
				// Toast.makeText(MainActivity.this,Float.toString(distanceInMeter[1]),Toast.LENGTH_LONG).show();
			}
		}
	}

	public void calculateDistance(float[] acceleration, float deltaTime) {
		for (int i = 0; i < acceleration.length; i++) {
			speed[i] = acceleration[i] * deltaTime;
			distanceInMeter[i] += speed[i] * deltaTime + acceleration[i]
					* deltaTime * deltaTime / 2;
		}
	}
}