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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectionFragment extends Fragment implements OnClickListener{
	
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private TextView dishesCookedView;
	private TextView addNewView;
	private ImageView badgeView;

	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
		
	    super.onCreateView(inflater, container, savedInstanceState);
	    
	    View view = inflater.inflate(R.layout.selection, container, false);

	 // Find the user's profile picture custom view
	 profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
	 profilePictureView.setCropped(true);
	 
	badgeView = (ImageView) view.findViewById(R.id.badgeImage);
	badgeView.setImageResource(R.drawable.star);


     Button b = (Button) view.findViewById(R.id.timeline);
     b.setOnClickListener(this);
	 
	    // Check for an open session
	    Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        // Get the user's data
	        makeMeRequest(session);
	    }
	    
	 // Find the user's name view
	 userNameView = (TextView) view.findViewById(R.id.selection_user_name);
	 
	//  Find dishes cooked view
	 dishesCookedView = (TextView) view.findViewById(R.id.dishes_cooked);
	 
	 
	  addNewView =(TextView) view.findViewById(R.id.addNew);
	 
	    return view;
	}
	
	
	private void makeMeRequest(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                    // Set the id for the ProfilePictureView
	                    // view that in turn displays the profile picture.
	                    profilePictureView.setProfileId(user.getId());
	                    // Set the Textview's text to the user's name.
	                    userNameView.setText(user.getName());
	                    
	                    dishesCookedView.setText("# Dishes cooked");
	                    
	                    addNewView.setText("Add a new dish:");
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }

			
	    });
	    request.executeAsync();
	} 
	
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (session != null && session.isOpened()) {
	        // Get the user's data.
	        makeMeRequest(session);
	    }
	}
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(final Session session, final SessionState state, final Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	   
	}
	
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.timeline:

            break;
        }
    }
	
	
	private static final int REAUTH_ACTIVITY_CODE = 100;
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == REAUTH_ACTIVITY_CODE) {
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
	    super.onSaveInstanceState(bundle);
	    uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	
	
	private static final String TAG = "SelectionFragment";

}
