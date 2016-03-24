package com.Vlad.Herescu.simplehero.BusinessLogic;


import org.json.JSONObject;

import java.util.Observer;

/**
 * Created by admin on 11/21/2015.
 */
public interface ServerManagerItf {


    public void createUser(JSONObject user);

    public boolean pushIdea(String _type, String _idea, String _optional);

    public void sendThanksPrice(String _id,  String _message);

    public void getMoreData(int _skipSize);

    public void getNewData();


}
