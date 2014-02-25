/*package iii.pos.client.fragment;

import iii.pos.client.R;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FloorFragment4 extends Fragment {
	View mainView = null;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();

	float oldDist = 1f;
	PointF oldDistPoint = new PointF();

	public static String TAG = "ZOOM";

	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View mapLayout = inflater.inflate(
				R.layout.floor4, container, false);
		mainView = (LinearLayout) mapLayout.findViewById(R.id.linearLayout);
		zoom(0.5f, 0.5f, new PointF(0, 0));
		mainView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d(TAG, "mode=DRAG");
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					start.set(event.getX(), event.getY());
					Log.d(TAG, "mode=DRAG");
					mode = DRAG;

					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					oldDistPoint = spacingPoint(event);
					Log.d(TAG, "oldDist=" + oldDist);
					if (oldDist > 10f) {
						midPoint(mid, event);
						mode = ZOOM;
						Log.d(TAG, "mode=ZOOM");
					}
					//System.out.println("current time :" + System.currentTimeMillis());
					break;// return !gestureDetector.onTouchEvent(event);
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					Log.d(TAG, "mode=NONE");
					mode = NONE;
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {

						mainView.setPivotX(event.getX() - start.x);
						mainView.setPivotY(event.getY() - start.y);

					} else if (mode == ZOOM) {
						PointF newDist = spacingPoint(event);
						float newD = spacing(event);

						Log.e(TAG, "newDist=" + newDist);
						float[] old = new float[9];
						float[] newm = new float[9];
						Log.e(TAG, "x=" + old[0] + ":&:" + old[2]);
						Log.e(TAG, "y=" + old[4] + ":&:" + old[5]);
						float scale = newD / oldDist;
						float scalex = newDist.x / oldDistPoint.x;
						float scaley = newDist.y / oldDistPoint.y;
						zoom(scale, scale, start);

					}
					break;
				}
				return true;
			}
		});
		
		return mapLayout;
	}

	public void selectTable(View v) {

		ImageView imgView = (ImageView) v;
		int idImageView = v.getId();
		Object obj = v.getTag();
		Toast.makeText(getActivity(), "ID Là:  " + obj.toString(),
				Toast.LENGTH_SHORT).show();
	}

	*//**
	 * zooming is done from here
	 *//*
	public void zoom(Float scaleX, Float scaleY, PointF pivot) {
		mainView.setPivotX(pivot.x);
		mainView.setPivotY(pivot.y);
		mainView.setScaleX(scaleX);
		mainView.setScaleY(scaleY);
	}

	*//**
	 * space between the first two fingers
	 *//*
	private float spacing(MotionEvent event) {
		// ...
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private PointF spacingPoint(MotionEvent event) {
		PointF f = new PointF();
		f.x = event.getX(0) - event.getX(1);
		f.y = event.getY(0) - event.getY(1);
		return f;
	}

	public void drag() {

	}

	*//**
	 * the mid point of the first two fingers
	 *//*
	private void midPoint(PointF point, MotionEvent event) {
		// ...
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}*/