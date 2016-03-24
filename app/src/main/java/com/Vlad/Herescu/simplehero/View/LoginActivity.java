package com.Vlad.Herescu.simplehero.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.BusinessLogic.ManagerSingleton;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import static com.facebook.GraphRequest.*;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

       login();
    }

    public void login()
    {
        if( checkLogin() == false)
            setLogin();
        else
            startMainActivity();
    }

    private boolean checkLogin()
    {
         AccessToken token =  AccessToken.getCurrentAccessToken();
        if(token == null)
        {
            Log.i("message", "este null");
            return false;
        }
        Log.i("message", "nu este null");
        return true;
    }

    private void setLogin()
    {
        callbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = (LoginButton) this.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
    //    loginButton.setPublishPermissions("publish_actions ");

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        Log.i("mesagjonScucess", "intru in onSuccess " + loginResult.getAccessToken());

                        final AccessToken accessToken = loginResult.getAccessToken();

                        GraphRequestAsyncTask request = newMeRequest(accessToken, new GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                                Log.i("idFacebook", user.optString("email"));
                                Log.i("idFacebook", user.optString("name"));
                                Log.i("idFacebook", user.optString("id"));


                                ManagerSingleton.getInstance().get_ServerHandler().createUser(user);

                            }
                        }).executeAsync();


                        startMainActivity();
                    }

                    @Override
                    public void onCancel() {
                        Log.i("FacebookMessage", "cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("FacebookMessage", "nu a mers");
                    }
                });



    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("mesagjActvityResult", "intru in onActivityResult");
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    public void startMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
