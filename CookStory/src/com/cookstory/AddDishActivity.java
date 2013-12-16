package com.cookstory;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class AddDishActivity extends FragmentActivity{


	  
	private static final int ADD_DISH = 0;
	private static final int SPLASH = 1; 
	private boolean isResumed = false;
	
	private static final int FRAGMENT_COUNT = SPLASH + 1;
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

	
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    uiHelper = new UiLifecycleHelper(this, callback);
    uiHelper.onCreate(savedInstanceState);
    
    setContentView(R.layout.activity_add_dish);
    
    FragmentManager fm = getSupportFragmentManager();
    fragments[ADD_DISH] =fm.findFragmentById(R.id.addDishFragment);

    
    
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
			  showFragment(ADD_DISH, false);
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
		  showFragment(ADD_DISH, false);
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
  


  
 
}
