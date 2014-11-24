package com.KNASK.todayinthecity;

import java.util.Arrays;
import java.util.List;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener{

	private static final int RC_SIGN_IN = 0;
	private String TAG = "LoginActivity";
	
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
 
    private boolean mSignInClicked;
 
    private ConnectionResult mConnectionResult;
    
	// Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
	private SignInButton btnSignIn;
	private Button btnSignOut;
	
	private String userName;
	private String userEmail;
	
	//facebook login
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// uiHelper = new UiLifecycleHelper(this, statusCallback);
		// uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		btnSignOut = (Button) findViewById(R.id.btn_sign_out);

		// Button click listeners
		btnSignIn.setOnClickListener(this);
		btnSignOut.setOnClickListener(this);

		// Initializing google plus api client
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Plus.API, new Plus.PlusOptions.Builder().build()) // note
																			// the
																			// options
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();

		// ///////////////////////////////////////////////////////////////////////////////////
		// facebook login
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("public_profile"));
		// session state call back event
		authButton.setSessionStatusCallback(new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state, Exception exception) {

				if (session.isOpened()) {
					Log.i(TAG, "Access Token" + session.getAccessToken());
					Request.newMeRequest(session, new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user, Response response) {
							if (user != null) {
								Log.i(TAG, "User ID " + user.getId());
								Log.i(TAG, "Name "	+ user.getName());
								Log.i(TAG, "User Name "	+ user.getUsername());
								
								userName = user.getName();
								
								Toast.makeText(getApplicationContext(), "Logged User: " + userName, Toast.LENGTH_LONG).show();
							}
						}
					}).executeAsync();
				}
				else if(session.isClosed()){
		             Toast.makeText(getApplicationContext(), "You are logged out.", Toast.LENGTH_LONG).show();
		        }
			}
		});
		
	}
    
	/* (non-Javadoc)
	 * @see com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener#onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	    if (!arg0.hasResolution()) {
	        GooglePlayServicesUtil.getErrorDialog(arg0.getErrorCode(), this,
	                0).show();
	        return;
	    }
	 
	    if (!mIntentInProgress) {
	        // Store the ConnectionResult for later usage
	        mConnectionResult = arg0;
	 
	        if (mSignInClicked) {
	            // The user has already clicked 'sign-in' so we attempt to
	            // resolve all
	            // errors until the user is signed in, or they cancel.
	            resolveSignInError();
	        }
	    }
		
	}


	/* (non-Javadoc)
	 * @see com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks#onConnected(android.os.Bundle)
	 */
	@Override
	public void onConnected(Bundle arg0) {
	    mSignInClicked = false;
	 
	    // Get user's information
	    getProfileInformation();
	 
	    // Update the UI after signin
	    updateUI(true);
	    
	    //if login is succeeded, just disconnect from Google.
	    signOutFromGplus();
	    
	    LoadMainActivity();
		
	}

	/* (non-Javadoc)
	 * @see com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks#onConnectionSuspended(int)
	 */
	@Override
	public void onConnectionSuspended(int arg0) {
	    mGoogleApiClient.connect();
	    updateUI(false);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		mGoogleApiClient.connect();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		
		if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
	}
	
	   /**
     * Button on click listener
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_sign_in:
            // Signin button clicked
            signInWithGplus();
            break;
        case R.id.btn_sign_out:
            // Signout button clicked
            signOutFromGplus();
            break;
        }
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == RC_SIGN_IN) {
	        if (resultCode != RESULT_OK) {
	            mSignInClicked = false;
	        }
	 
	        mIntentInProgress = false;
	 
	        if (!mGoogleApiClient.isConnecting()) {
	            mGoogleApiClient.connect();
	        }
	    }
	    else {
	    	 super.onActivityResult(requestCode, resultCode, data);
	         Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
	    if (!mGoogleApiClient.isConnecting()) {
	        mSignInClicked = true;
	        resolveSignInError();
	    }
	}
	 
	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        mGoogleApiClient.disconnect();
	        mGoogleApiClient.connect();
	        updateUI(false);
	    }
	}
	
	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	        try {
	            mIntentInProgress = true;
	            mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	        } catch (SendIntentException e) {
	            mIntentInProgress = false;
	            mGoogleApiClient.connect();
	        }
	    }
	}
	
	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {
	    if (isSignedIn) {
	        btnSignIn.setVisibility(View.GONE);
	        btnSignOut.setVisibility(View.VISIBLE);
	    } else {
	        btnSignIn.setVisibility(View.VISIBLE);
	        btnSignOut.setVisibility(View.GONE);
	    }
	}
	
	/**
	 * Fetching user's information name, email
	 * */
	private void getProfileInformation() {
	    try {
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	            Person currentPerson = Plus.PeopleApi
	                    .getCurrentPerson(mGoogleApiClient);
	            userName = currentPerson.getDisplayName();
	            userEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
	            String personPhotoUrl = currentPerson.getImage().getUrl();
	            String personGooglePlusProfile = currentPerson.getUrl();
	 

	 
	 
	        } else {
	            Toast.makeText(getApplicationContext(),
	                    "Person information is null", Toast.LENGTH_LONG).show();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void LoadMainActivity() {
	    Intent i = new Intent(this, MainActivity.class);
	    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    i.putExtra("LOGINUSER", userName);
	    i.putExtra("LOGINEMAIL", userEmail);
	    startActivity(i);
	    
	    finish();		
		
	}
}
