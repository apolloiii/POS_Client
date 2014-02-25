package iii.pos.client.calculator;
//package com.iii.pos.calculator;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.PopupMenu;
//import android.widget.PopupMenu.OnMenuItemClickListener;
//
//import com.iii.pos.R;
//
//public class Calculator extends Dialog implements PanelSwitcher.Listener,
//		Logic.Listener, OnClickListener, OnMenuItemClickListener {
//	
//	public Calculator(Context context, boolean cancelable,
//			OnCancelListener cancelListener) {
//		super(context, cancelable, cancelListener);
//		// TODO Auto-generated constructor stub
//	}
//
//	EventListener mListener = new EventListener();
//	private CalculatorDisplay mDisplay;
//	private Persist mPersist;
//	private History mHistory;
//	private Logic mLogic;
//	private ViewPager mPager;
//	private View mClearButton;
//	private View mBackspaceButton;
//	private View mOverflowMenuButton;
//
//	static final int BASIC_PANEL = 0;
//	static final int ADVANCED_PANEL = 1;
//
//	private static final String LOG_TAG = "Calculator";
//	private static final boolean DEBUG = false;
//	private static final boolean LOG_ENABLED = false;
//	private static final String STATE_CURRENT_VIEW = "state-current-view";
//
//	@Override
//	public boolean onMenuItemClick(MenuItem arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v.getId() == R.id.overflow_menu) {
//			PopupMenu menu = constructPopupMenu();
//			if (menu != null) {
//				menu.show();
//			}
//		}
//
//		switch (v.getId()) {
//		case R.id.overflow_menu:
//			PopupMenu menu = constructPopupMenu();
//			if (menu != null) {
//				menu.show();
//			}
//			break;
//		}
//	}
//
//	@Override
//	public void onDeleteModeChange() {
//		updateDeleteMode();
//	}
//
//	@Override
//	public void onChange() {
//		invalidateOptionsMenu();
//	}
//
//	@Override
//	public void onCreate(Bundle state) {
//		super.onCreate(state);
//
//		// Disable IME for this application
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
//				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//
//		setContentView(R.layout.main);
//		try {
//
//			mPager = (ViewPager) findViewById(R.id.panelswitch);
//			if (mPager != null) {
//				mPager.setAdapter(new PageAdapter(mPager));
//			} else {
//				// Single page UI
//
//				TypedArray buttons = getContext().getResources()
//						.obtainTypedArray(R.array.buttons);
//
//				for (int i = 0; i < buttons.length(); i++) {
//
//					setOnClickListener(null, buttons.getResourceId(i, 0));
//				}
//				buttons.recycle();
//			}
//		} catch (Exception e) {
//			Log.i("Log : ", "Exception : " + e.getMessage());
//		}
//
//		if (mClearButton == null) {
//			mClearButton = findViewById(R.id.clear);
//			mClearButton.setOnClickListener(mListener);
//			mClearButton.setOnLongClickListener(mListener);
//		}
//		if (mBackspaceButton == null) {
//			mBackspaceButton = findViewById(R.id.del);
//			mBackspaceButton.setOnClickListener(mListener);
//			mBackspaceButton.setOnLongClickListener(mListener);
//		}
//
//		mPersist = new Persist(getContext());
//		mPersist.load();
//
//		mHistory = mPersist.history;
//
//		mDisplay = (CalculatorDisplay) findViewById(R.id.display);
//
//		mLogic = new Logic(getContext(), mHistory, mDisplay);
//		mLogic.setListener(this);
//
//		mLogic.setDeleteMode(mPersist.getDeleteMode());
//		mLogic.setLineLength(mDisplay.getMaxDigits());
//
//		HistoryAdapter historyAdapter = new HistoryAdapter(getContext(),
//				mHistory, mLogic);
//		mHistory.setObserver(historyAdapter);
//
//		if (mPager != null) {
//			mPager.setCurrentItem(state == null ? 0 : state.getInt(
//					STATE_CURRENT_VIEW, 0));
//		}
//
//		mListener.setHandler(mLogic, mPager);
//		mDisplay.setOnKeyListener(mListener);
//
//		if (!ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
//			createFakeMenu();
//		}
//
//		mLogic.resumeWithHistory();
//		updateDeleteMode();
//	}
//
//	private void updateDeleteMode() {
//		if (mLogic.getDeleteMode() == Logic.DELETE_MODE_BACKSPACE) {
//			mClearButton.setVisibility(View.GONE);
//			mBackspaceButton.setVisibility(View.VISIBLE);
//		} else {
//			mClearButton.setVisibility(View.VISIBLE);
//			mBackspaceButton.setVisibility(View.GONE);
//		}
//	}
//
//	void setOnClickListener(View root, int id) {
//		final View target = root != null ? root.findViewById(id)
//				: findViewById(id);
//		target.setOnClickListener(mListener);
//	}
//
//	//
//	// @Override
//	// public boolean onCreateOptionsMenu(Menu menu) {
//	// super.onCreateOptionsMenu(menu);
//	// getMenuInflater().inflate(R.menu.menu, menu);
//	// return true;
//	// }
//	//
//	// @Override
//	// public boolean onPrepareOptionsMenu(Menu menu) {
//	// super.onPrepareOptionsMenu(menu);
//	// // menu.findItem(R.id.basic).setVisible(!getBasicVisibility());
//	// // menu.findItem(R.id.advanced).setVisible(!getAdvancedVisibility());
//	// return true;
//	// }
//	//
//	//
//	private void createFakeMenu() {
//		mOverflowMenuButton = findViewById(R.id.overflow_menu);
//		if (mOverflowMenuButton != null) {
//			mOverflowMenuButton.setVisibility(View.VISIBLE);
//			mOverflowMenuButton.setOnClickListener(this);
//		}
//	}
//
//	// @Override
//	// public void onClick(View v) {
//	// if(v.getId() == R.id.overflow_menu){
//	// PopupMenu menu = constructPopupMenu();
//	// if (menu != null) {
//	// menu.show();
//	// }
//	// }
//	//
//	// // switch (v.getId()) {
//	// // case R.id.overflow_menu:
//	// // PopupMenu menu = constructPopupMenu();
//	// // if (menu != null) {
//	// // menu.show();
//	// // }
//	// // break;
//	// // }
//	// }
//	//
//	private PopupMenu constructPopupMenu() {
//		final PopupMenu popupMenu = new PopupMenu(getContext(),
//				mOverflowMenuButton);
//		final Menu menu = popupMenu.getMenu();
//		popupMenu.inflate(R.menu.menu);
//		popupMenu.setOnMenuItemClickListener(this);
//		onPrepareOptionsMenu(menu);
//		return popupMenu;
//	}
//
//	//
//	//
//	// @Override
//	// public boolean onMenuItemClick(MenuItem item) {
//	// return onOptionsItemSelected(item);
//	// }
//	//
//	// // private boolean getBasicVisibility() {
//	// // return mPager != null && mPager.getCurrentItem() == BASIC_PANEL;
//	// // }
//	// //
//	// // private boolean getAdvancedVisibility() {
//	// // return mPager != null && mPager.getCurrentItem() == ADVANCED_PANEL;
//	// // }
//	// public static String getResult = "";
//	// @Override
//	// public boolean onOptionsItemSelected(MenuItem item) {
//	//
//	// int id = item.getItemId();
//	//
//	// if(id == R.id.clear_history){
//	// mHistory.clear();
//	// mLogic.onClear();
//	// }else if (id == R.id.Finish) {
//	// getResult = CalculatorDisplay.getResult().toString();
//	// Log.i("Log : ", "Text : "+ getResult);
//	// this.finish();
//	//
//	//
//	//
//	// // Log.i("Log : ", "Text : "+CalculatorDisplay.ResultText);
//	// }
//	//
//	//
//	// // switch (item.getItemId()) {
//	// // case R.id.clear_history:
//	// // mHistory.clear();
//	// // mLogic.onClear();
//	// // break;
//	//
//	// // case R.id.basic:
//	// // if (!getBasicVisibility() && mPager != null) {
//	// // mPager.setCurrentItem(BASIC_PANEL, true);
//	// // }
//	// // break;
//	// //
//	// // case R.id.advanced:
//	// // if (!getAdvancedVisibility() && mPager != null) {
//	// // mPager.setCurrentItem(ADVANCED_PANEL, true);
//	// // }
//	// // break;
//	// // }
//	// return super.onOptionsItemSelected(item);
//	// }
//	// // public static CharSequence getStringResult() {
//	// // return CalculatorDisplay.ResultText.toString();
//	// // }
//	//
//	//
//	// @Override
//	// protected void onSaveInstanceState(Bundle state) {
//	// super.onSaveInstanceState(state);
//	// if (mPager != null) {
//	// state.putInt(STATE_CURRENT_VIEW, mPager.getCurrentItem());
//	// }
//	// }
//	//
//	// @Override
//	// public void onPause() {
//	// super.onPause();
//	// mLogic.updateHistory();
//	// mPersist.setDeleteMode(mLogic.getDeleteMode());
//	// mPersist.save();
//	// }
//	//
//	// // @Override
//	// // public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
//	// // if (keyCode == KeyEvent.KEYCODE_BACK && getAdvancedVisibility()
//	// // && mPager != null) {
//	// // mPager.setCurrentItem(BASIC_PANEL);
//	// // return true;
//	// // } else {
//	// // return super.onKeyDown(keyCode, keyEvent);
//	// // }
//	// // }
//	//
//
//	static void log(String message) {
//		if (LOG_ENABLED) {
//			Log.v(LOG_TAG, message);
//		}
//	}
//
//	// @Override
//	// public void onChange() {
//	// invalidateOptionsMenu();
//	// }
//	//
//	// @Override
//	// public void onDeleteModeChange() {
//	// updateDeleteMode();
//	// }
//	//
//	class PageAdapter extends PagerAdapter {
//		private View mSimplePage;
//		private View mAdvancedPage;
//
//		public PageAdapter(ViewPager parent) {
//			final LayoutInflater inflater = LayoutInflater.from(parent
//					.getContext());
//			final View simplePage = inflater.inflate(R.layout.simple_pad,
//					parent, false);
//			final View advancedPage = inflater.inflate(R.layout.advanced_pad,
//					parent, false);
//			mSimplePage = simplePage;
//			mAdvancedPage = advancedPage;
//
//			final Resources res = getContext().getResources();
//			final TypedArray simpleButtons = res
//					.obtainTypedArray(R.array.simple_buttons);
//			for (int i = 0; i < simpleButtons.length(); i++) {
//				setOnClickListener(simplePage,
//						simpleButtons.getResourceId(i, 0));
//			}
//			simpleButtons.recycle();
//
//			final TypedArray advancedButtons = res
//					.obtainTypedArray(R.array.advanced_buttons);
//			for (int i = 0; i < advancedButtons.length(); i++) {
//				setOnClickListener(advancedPage,
//						advancedButtons.getResourceId(i, 0));
//			}
//			advancedButtons.recycle();
//
//			final View clearButton = simplePage.findViewById(R.id.clear);
//			if (clearButton != null) {
//				mClearButton = clearButton;
//			}
//
//			final View backspaceButton = simplePage.findViewById(R.id.del);
//			if (backspaceButton != null) {
//				mBackspaceButton = backspaceButton;
//			}
//		}
//
//		@Override
//		public int getCount() {
//			return 2;
//		}
//
//		@Override
//		public void startUpdate(View container) {
//		}
//
//		@Override
//		public Object instantiateItem(View container, int position) {
//			final View page = position == 0 ? mSimplePage : mAdvancedPage;
//			((ViewGroup) container).addView(page);
//			return page;
//		}
//
//		@Override
//		public void destroyItem(View container, int position, Object object) {
//			((ViewGroup) container).removeView((View) object);
//		}
//
//		@Override
//		public void finishUpdate(View container) {
//		}
//
//		@Override
//		public boolean isViewFromObject(View view, Object object) {
//			return view == object;
//		}
//
//		@Override
//		public Parcelable saveState() {
//			return null;
//		}
//
//		@Override
//		public void restoreState(Parcelable state, ClassLoader loader) {
//		}
//	}
//
//}
