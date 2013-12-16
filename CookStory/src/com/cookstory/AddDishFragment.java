package com.cookstory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AddDishFragment extends Fragment {
	
	private TextView descriptionLabelView;
	private ImageView recipeImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
		
	    super.onCreateView(inflater, container, savedInstanceState);
	    
	    View view = inflater.inflate(R.layout.add_dish, container, false);

	 // Find the user's profile picture custom view
	 descriptionLabelView = (TextView) view.findViewById(R.id.description_label);
	 descriptionLabelView.setText("Description");

	 recipeImageView = (ImageView) view.findViewById(R.id.dishImage);
	 recipeImageView.setImageResource(R.drawable.dish);
	    return view;
	}
	
	
	
	
	private static final String TAG = "AddDishFragment";

}
