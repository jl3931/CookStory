package com.cookstory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;

import com.facebook.*;
import com.facebook.model.*;

public class MainActivity extends FragmentActivity {
  
	private static final int SPLASH = 0;
	private static final int SELECTION = 1;
	private static final int SETTINGS = 2;
	private static final int FRAGMENT_COUNT = SETTINGS + 1;
	private boolean isResumed = false;
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private MenuItem settings;
	
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    System.out.println("in on create func");
    uiHelper = new UiLifecycleHelper(this, callback);
    uiHelper.onCreate(savedInstanceState);
    
    setContentView(R.layout.activity_main);
    
    FragmentManager fm = getSupportFragmentManager();
    fragments[SPLASH] =fm.findFragmentById(R.id.splashFragment);
    fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);
    fragments[SETTINGS] = fm.findFragmentById(R.id.userSettingsFragment);
    
    
    FragmentTransaction transaction = fm.beginTransaction();
    
    for(int i=0; i< fragments.length; i++)
    {
    	transaction.hide(fragments[i]);
    }
    transaction.commit();
  }
  
  private void showFragment(int fragmentIndex, boolean addToBackStack){
	  FragmentManager fm = getSupportFragmentManager();
	  FragmentTransaction transaction = fm.beginTransaction();
	  for ( int i=0; i < fragments.length; i++)
	  {
		  if (i==fragmentIndex)
		  {
			  transaction.show(fragments[i]);
		  }
		  else{
			  transaction.hide(fragments[i]);
		  }
		  
	  }
	  if(addToBackStack)
	  {
		  transaction.addToBackStack(null);
	  }
	  transaction.commit();
  }
    
  @Override
  public void onResume(){
	  super.onResume();
	  uiHelper.onResume();
	  isResumed = true;
  }
  
  @Override
  public void onPause()
  {
	  super.onPause();
	  uiHelper.onPause();
	  isResumed = false;
  }
  
  private void onSessionStateChange(Session session, SessionState state, Exception exception)
  {
	  if (isResumed){
		  FragmentManager manager = getSupportFragmentManager();
		  
		  int backStackSize = manager.getBackStackEntryCount();
		  for (int i=0;i<backStackSize;i++)
		  {
			  manager.popBackStack();
		  }
		  
		  if (state.isOpened()){
			  showFragment(SELECTION, false);
		  }
		  else if (state.isClosed()){
			  showFragment(SPLASH, false);
		  }
		  
		  
	  }

	  
  }
  
  @Override
  
  protected void onResumeFragments() {
	  super.onResumeFragments();
	  Session session = Session.getActiveSession();
	  
	  if (session !=null && session.isOpened()){
		  System.out.println("Showing selection");
		  showFragment(SELECTION, false);
	  }
	  else{
		  showFragment(SPLASH, false);
	  }
  }
  
  private UiLifecycleHelper uiHelper;
  private Session.StatusCallback callback = 
		  new Session.StatusCallback(){
	  @Override
	  public void call(Session session,
			  SessionState state, Exception exception){
		  onSessionStateChange(session, state, exception);
	  }
  };
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      uiHelper.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onDestroy(){
	  super.onDestroy();
	  uiHelper.onDestroy();
  }
  
  @Override
  protected void onSaveInstanceState(Bundle outState){
	  super.onSaveInstanceState(outState);
	  uiHelper.onSaveInstanceState(outState);
	  
  }
  
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
      // only add the menu when the selection fragment is showing
      if (fragments[SELECTION].isVisible()) {
    	  System.out.println("Selection flag is on. displaying menu");
          if (menu.size() == 0) {
              settings = menu.add(R.string.settings);
          }
          return true;
      } else {
    	  System.out.println("else");
          menu.clear();
          settings = null;
      }
      return false;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      if (item.equals(settings)) {
          showFragment(SETTINGS, true);
          return true;
      }
      return false;
  }
}