package com.cookstory;
import com.facebook.*;
import com.facebook.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.content.Intent;




/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

		    // callback when session changes state
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		    	if (session.isOpened()) {
		    		
		    		// make request to the /me API
		    		Request.newMeRequest(session, new Request.GraphUserCallback() {

		    			  // callback after Graph API response with user object
		    			  @Override
		    			  public void onCompleted(GraphUser user, Response response) {
		    			    if (user != null) {
		    			      TextView welcome = (TextView) findViewById(R.id.welcome);
		    			      welcome.setText("Hello " + user.getName() + "!");
		    			    }
		    			  }
		    			}).executeAsync();
		    	}

		    }
		  });
		  
		
		// Start up RegisterActivity right away
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		// Since this is just a wrapper to start the main activity,
		// finish it after launching RegisterActivity
		finish();
	}
	
	//update the active session
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	
}
