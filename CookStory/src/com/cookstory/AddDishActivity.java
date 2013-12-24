package com.cookstory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.cookstory.Dish;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddDishActivity extends Activity {
	
	Uri imagePath;
	String message;
	public final static String TEST="com.example.myfirstapp.MESSAGE";

	@Override
    public void onCreate(Bundle savedInstanceState) {         

       super.onCreate(savedInstanceState);    
       
       
       Intent intent = getIntent();
       message = intent.getStringExtra(MainActivity.ADD_DISH_TITLE);
       
       

       setContentView(R.layout.add_dish);
       TextView addDishTitle = (TextView)this.findViewById(R.id.title);
       
       addDishTitle.setText(message);
       
       TextView descriptionLabelView = (TextView)this.findViewById(R.id.description_label);
  	   descriptionLabelView.setText("Description");

  	   //ImageView recipeImageView = (ImageView)this.findViewById(R.id.dishImage);
  	   //recipeImageView.setImageResource(R.drawable.dish);
       
       
   }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	  public void cancelBack(View view) 
	  {
	      Intent intent = new Intent(AddDishActivity.this, MainActivity.class);
	      startActivity(intent);
	      
	  }
	  
	  
	  static final int REQUEST_IMAGE_CAPTURE = 1;
	  static final int REQUEST_TAKE_PHOTO = 1;

	  
	  private void dispatchTakePictureIntentThumb() {
		    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		    }
		}
	  
	  private Uri dispatchTakePictureIntent() {
		  Uri uri = null;
	      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	      // Ensure that there's a camera activity to handle the intent
	      if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	          // Create the File where the photo should go
	          File photoFile = null;
	          try {
	              photoFile = createImageFile();
	              Log.d("dispatchTakePictureIntent", "inside try");
	          } catch (IOException ex) {
	              // Error occurred while creating the File
	        	  Log.d("dispatchTakePictureIntent", "inside catch");
	             
	          }
	          // Continue only if the File was successfully created
	          if (photoFile != null) {
	        	  
	        	  Log.d("dispatchTakePictureIntent", "inside secondif");
	        	  Uri photoUri = Uri.fromFile(photoFile);
	              takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
	              Log.d("dispatchTakePictureIntent","after putExtra");
	              
	              startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	              Log.d("dispatchTakePictureIntent", photoUri.toString());
	              return photoUri;
	          }
	          
	      }
	      return uri;
	  }
	  public void clickPicture(View view) throws Exception
	  {
		  
		  imagePath= dispatchTakePictureIntent();
		  //dispatchTakePictureIntentThumb();
		    
	  }
	  
	  public void submitDish(View view) throws Exception 
	  {
		 /* Dish dish = new Dish("", "f1", "hello");
		  Gson gson = new Gson();
		  String json = gson.toJson(dish);  
		  
		  String result="one";

		    
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost("http://jl3931-cookstory.appspot.com/cookstory_backend/");

		   

		    //passes the results to a string builder/entity
		    StringEntity se = new StringEntity(json);

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");

		    //Handles what is returned from the page 
		    
		    
		    
		    
		    
		    
		    
		    
		    try{
			   HttpResponse response = httpclient.execute(httpost);

		   result ="two";
		 
		
		        // get response
		        HttpEntity entity = response.getEntity();
		        //TextView test = (TextView)this.findViewById(R.id.test);
		        if ( entity == null )
		        {
		            result = "No response from server";
		            //System.out.println(msg);
		            //test.setText(msg);
		            
		        }
		        else{
		        // get response content and convert it to json string
		        InputStream is = entity.getContent();
		        
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
		        StringBuilder sb = new StringBuilder();

		        String line = null;
		        String dummy_mess = "Inside else ";
		        sb.append(dummy_mess.toString());
		        while ((line = reader.readLine()) != null)
		        {
		            sb.append(line + "\n");
		        }
		        
		        
		        
		        //System.out.println(is.toString());
		        //test.setText(sb.toString());
		        result = sb.toString();
		      
		        }
		 }
		 catch(Exception E)
		 {
			 System.out.println(E.getMessage());
		 }
		 
		        
		        // handle response here...
			
			  
		        
		    
		    Intent intent = new Intent(AddDishActivity.this, TimelineActivity.class);
	        intent.putExtra(TEST, result);
	        
		      startActivity(intent); */
		  
		  
		  
		  //Uri uri = null;  a Uri pointing to the file to be uploaded
		  
		  Log.d("submitDish", "Inside submit dish");
		  
		  HttpClient httpclient = new DefaultHttpClient();
		  HttpPost httppost = new HttpPost("http://1.jl3931-cookstory.appspot.com/gcs/image/upload");
		   
		  InputStream stream = null;
		  try {
		  	stream = getBaseContext().getContentResolver().openInputStream(imagePath);
		   
		  	InputStreamEntity reqEntity = new InputStreamEntity(stream, -1);
		   
		  	httppost.setEntity(reqEntity);
		   
		  	HttpResponse response = httpclient.execute(httppost);
		  	if (response.getStatusLine().getStatusCode() == 200) {
		      // file uploaded successfully!
		  	} else {
		  		throw new RuntimeException("server couldn't handle request");
		  	}
		  } catch (Exception e) {
			Log.d("dispatchTakePictureIntent", e.getMessage());
		  	e.printStackTrace();
		    
		    // handle error
		  } finally {
		    stream.close();
		  }
		  
		  
		  
		  
		    Intent intent = new Intent(AddDishActivity.this, TimelineActivity.class);
	        intent.putExtra(TEST, imagePath);
	        
		      startActivity(intent);
	  }
	  
	  
	  
	  
	  

	  
	  
	  String mCurrentPhotoPath;

	  private File createImageFile() throws IOException {
	      // Create an image file name
	      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	      String imageFileName = "JPEG_" + timeStamp + "_";
	      File storageDir = Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES);
	      Log.d("createImageFile", Environment.DIRECTORY_PICTURES);
	      Log.d("createImageFile", storageDir.toString());
	      Log.d("createImageFile", imageFileName);
	      File image = File.createTempFile(
	          imageFileName,  /* prefix */
	          ".jpg",         /* suffix */
	          this.getCacheDir()     /* directory */
	          //storageDir
	      );
	      Log.d("createImageFile", image.toString());
	      // Save a file: path for use with ACTION_VIEW intents
	      mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	      Log.d("createImageFile", mCurrentPhotoPath);
	      return image;
	  }
	  
	  
	 


	  
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  Log.d("onActivityResult", "outside if");
		  ImageView recipeImageView = (ImageView)this.findViewById(R.id.dishImage);
		  Log.d("onActivityResult", "outside if");
		  Log.d("requestcode", requestCode+"");
		  Log.d("REQUEST_IMAGE_CAPTURE", REQUEST_IMAGE_CAPTURE+"");
		  Log.d("resultcode", resultCode+"");
		  Log.d("REESULT_OK", RESULT_OK+"");
		  Log.d("onActivityResult", "inside if");
	      if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	    	  Log.d("onActivityResult", "inside if");
	          Bundle extras = data.getExtras();
	          Bitmap imageBitmap = (Bitmap) extras.get("data");
	          recipeImageView.setImageBitmap(imageBitmap);
	      }
	      Log.d("onActivityResult", "completed if");
	  }
	  
	  
}