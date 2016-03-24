package com.Vlad.Herescu.simplehero.model;

import java.util.ArrayList;

/**
 * Created by admin on 10/20/2015.
 */
public class User
{

    private String m_id;

    private String m_userName;

    private String m_superHeroName;


    User()
    {

    }

    public User(String _superHeroName)
    {

    }

    public User(String _id, String _name)
    {
        m_id = _id;
        m_userName = _name;

    }

    public int calculateScore()
    {
        return 500;

        /*
        int score  = 0;

       for(Idea idea : m_ideasAccomplished)
            score += idea.getM_score();
       return score;
       */
    }

    public String getM_id() {
        return m_id;
    }

    public String get_userName() {
        return m_userName;
    }

    public String getM_superHeroName() {
        return m_superHeroName;
    }


}
