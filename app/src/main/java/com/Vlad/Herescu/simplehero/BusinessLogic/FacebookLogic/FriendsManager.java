package com.Vlad.Herescu.simplehero.BusinessLogic.FacebookLogic;

import android.util.Log;

import com.Vlad.Herescu.simplehero.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by admin on 2/19/2016.
 */
public class FriendsManager extends Observable
{

    private ArrayList<User> m_user;

    public FriendsManager()
    {
        m_user = new ArrayList<User>();
    }


    public void addFriends(JSONObject _friends)
    {

        JSONArray array = null;
        m_user.clear();

        try {
            array = _friends.getJSONArray("data");
            int length = array.length();
            //Log.i("getListFriends", array.toString());
            for(int i = 0; i < length; i++)
            {
                JSONObject resultObject = array.getJSONObject(i);
                String id = resultObject.get("id").toString();
                String name = resultObject.get("name").toString();

                Log.i("friend", id + " " + name);
                m_user.add(new User(id,name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("friends", "Nr friends is " + m_user.size());

        /*
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        m_user.add(new User("AAA","BBBBB"));
        */

        Log.i("friends", "Nr friends is " + m_user.size());
        setChanged();
        notifyObservers();
    }

    public ArrayList<User> get_user() {
        return m_user;
    }

    public void setM_user(ArrayList<User> m_user) {
        this.m_user = m_user;
    }
}
