package com.Vlad.Herescu.simplehero.DatabaseHandler;

import android.database.sqlite.SQLiteDatabase;

import com.Vlad.Herescu.simplehero.model.Idea;

import java.util.List;

/**
 * Created by admin on 12/5/2015.
 */
public interface IdeaHandler
{
    public void addDummyIdeas(SQLiteDatabase db);


    public boolean addIdea( Idea _idea);

    public Idea getIdea(String _parameter, String _value);

    public boolean deleteIdea(String _parameter, String _value);

    public List<Idea> getAllIdeas();

    public boolean updateIdea(Idea _idea);

}
