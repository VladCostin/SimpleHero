package com.Vlad.Herescu.simplehero.logic.BusinessLogic.FacebookLogic;

import android.os.Bundle;
import android.util.Log;

import com.Vlad.Herescu.simplehero.logic.BusinessLogic.ManagerSingleton;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONObject;

/**
 * Created by admin on 12/6/2015.
 */
public class FacebookLogic implements FacebookLogicITf
{
    public void getFriends()
    {
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
            //
            //    "/me/taggable_friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        //Log.i("getListFriends", response.toString());
                        JSONObject object = response.getJSONObject();
                        ManagerSingleton.getInstance().getM_friends().addFriends(object);

                    }
                }
        ).executeAsync();
    }

    public void getId()
    {
        String id = Profile.getCurrentProfile().getId();
        Log.i("idFacebook", id);
    }

    public String getName()
    {
        String name = Profile.getCurrentProfile().getName();
        return name;
    }

    public void postThanks()
    {
        Bundle params = new Bundle();
        params.putString("message", "This is a test message");
        params.putString("place", "155021662189");
        params.putString("tags", "1005169269546135");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.i("response", response.toString());
                    }
                }
        ).executeAsync();
    }


}
