package com.Vlad.Herescu.simplehero.logic.BusinessLogic;

import android.util.Log;

import com.Vlad.Herescu.simplehero.DatabaseHandler.AttributesConstants;
import com.Vlad.Herescu.simplehero.model.User;
import com.parse.FindCallback;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * deals with parsing the messages retrieved and creating the object to be sent
 * Created by Vlad Herescu on 11/21/2015.
 */
public class ServerManagerEntity implements ServerManagerItf {



    public void createUser(JSONObject user)
    {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("id", user.optString("id"));
        installation.saveInBackground();
    }

    public boolean pushIdea(String _type, String _idea, String _optional)
    {

        if(_type == null || _type.equals("") || _idea.equals(""))
            return false;


        ParseObject idea = new ParseObject("Idea");
        idea.put(AttributesConstants.m_Idea_Short, _idea);
        idea.put(AttributesConstants.m_Idea_Desc, _optional);
        idea.put(AttributesConstants.m_Idea_type, _type);
        idea.saveInBackground();

        /*
        List<Idea> ideas = MainActivity.m_dataBase.getAllIdeas();
        for(Idea idea : ideas)
        {
            ParseObject ideaObject = new ParseObject("Idea");
            ideaObject.put(AttributesConstants.m_Idea_Short, idea.getM_Idea());
            ideaObject.put(AttributesConstants.m_Idea_Desc, idea.getM_Description());
            ideaObject.put(AttributesConstants.m_Idea_type, idea.getM_ideaType().toString());
            ideaObject.saveInBackground();
        }
        */
        pushNotification();
        return true;
    }

    @Override
    public void sendThanksPrice(String _id,  String _message)
    {
        String name = ManagerSingleton.getInstance().get_facebook().getName();
        JSONObject object = createObject( name, _message);

        if(object == null)
            return;


        ParsePush push = new ParsePush();

        ParseQuery pQuery = ParseInstallation.getQuery(); // <-- Installation query
        pQuery.whereEqualTo("id", _id);


        push.setMessage(object.toString());

        push.setQuery(pQuery);
        push.sendInBackground();


    }

    public void pushNotification()
    {
        ParsePush push = new ParsePush();
        push.setMessage(PushTypes.RETRIEVE_DATA);
        push.sendInBackground();

    }
    /*
    public void getIfUserExists()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Installation");
        query.
    }
    */
    public void getMoreData(int _skipSize)
    {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Idea");
        query.setSkip(_skipSize);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                if (e == null) {
                    Log.i("parse", "nr items is : " + objects.size());
                    ManagerSingleton.getInstance().get_IdeasHandler().addIdeas(objects);
                } else {
                    Log.e("Parse", " there was an exception in retrieving the data");
                }


            }
        });

    }
    public JSONObject createObject( String _friend, String _message)
    {
        JSONObject jo = new JSONObject();
        try {

            jo.put(ParseConstants.KEY_HERO, _friend);
            jo.put(ParseConstants.KEY_MESSAGE, _message);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Log.i("pushNotification", jo.toString());
        return jo;
    }




    public void getNewData()
    {
        Log.i("message", "reintra aici,");
        getMoreData(0);
    }



}
