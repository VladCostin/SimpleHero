package com.Vlad.Herescu.simplehero.BusinessLogic;

import android.util.Log;

import com.Vlad.Herescu.simplehero.DatabaseHandler.AttributesConstants;
import com.Vlad.Herescu.simplehero.View.ToDoFragment;
import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.model.Idea_Type;
import com.Vlad.Herescu.simplehero.View.MainActivity;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

/**
 * Created by admin on 2/16/2016.
 */
public class IdeasManager  extends Observable
{

    List<Idea> m_ideas;



    List<Idea> m_ideasTODO;

    public IdeasManager()
    {

        m_ideas = new ArrayList<Idea>();
        m_ideasTODO = new ArrayList<Idea>();
    }



    public void addIdeas(Object data)
    {
        List<ParseObject> objects = (List<ParseObject>) data;
        List<Idea> ideas = MainActivity.m_dataBase.getAllIdeas();
        Log.i("message", "size este " + ideas.size());
        Date firstElement;

        if(m_ideas.size() == 0)
            firstElement = null;
        else
            firstElement = m_ideas.get(0).getM_date();

        for(ParseObject object : objects)
        {
            String shortIdea = object.getString(AttributesConstants.m_Idea_Short);
            String desc = object.getString(AttributesConstants.m_Idea_Desc);
            String type = object.getString(AttributesConstants.m_Idea_type);
            String id = object.getObjectId();
            Date date = object.getCreatedAt();

            Log.i("messageAddIdea", type);

            Idea idea = new Idea(id, shortIdea, desc, Idea_Type.valueOf(type), date);
            if(ideasExists_IN_Database(idea, ideas) == false &&
                    ideaExists_In_Not_Done(idea) == false)
            {
                if(firstElement == null)
                {
                    firstElement = date;
                    m_ideas.add(idea);
                }
                else
                {
                    if(firstElement.before(date))
                    {
                        m_ideas.add(0, idea);
                        firstElement = date;
                    }
                    else
                    {
                        m_ideas.add(idea);

                    }

                }
            }
        }
        setChanged();
        notifyObservers();

    }

    public boolean ideaExists_In_Not_Done(Idea _idea)
    {
        for(Idea idea : m_ideas)
        {
            if(idea.getM_id().equals(_idea.getM_id()))
                return true;
        }
        return false;
    }

    public boolean ideasExists_IN_Database(Idea _idea, List<Idea> _ideas)
    {
        for(Idea idea : _ideas)
        {

            if(idea.getM_id().equals(_idea.getM_id()))
                return true;
        }
        return false;
    }


    public boolean removeIdea_NOT_DONE(Idea _idea)
    {
        for(Idea idea : m_ideas)
            if(idea.getM_id().equals(_idea.getM_id()))
            {
                m_ideas.remove(idea);
                MainActivity.m_dataBase.addIdea(_idea);
                setChanged();
                notifyObservers();
                return true;
            }
        return false;
    }

    public boolean removeIdeaToDO(Idea _idea)
    {
        for(Idea idea : m_ideasTODO)
            if(idea.getM_id().equals(_idea.getM_id()))
            {
                m_ideasTODO.remove(idea);
                MainActivity.m_dataBase.updateIdea(_idea);
                setChanged();
                notifyObservers();
                return true;
            }
        return false;
    }


    public List<Idea> get_ideas() {
        return m_ideas;
    }
    public List<Idea> getM_ideasTODO() {
        return m_ideasTODO;
    }

    public void setM_ideasTODO(List<Idea> m_ideasTODO) {
        this.m_ideasTODO = m_ideasTODO;
    }

}
