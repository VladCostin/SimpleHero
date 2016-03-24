package com.Vlad.Herescu.simplehero.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.model.Idea_Status;
import com.Vlad.Herescu.simplehero.model.Idea_Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/5/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper implements  IdeaHandler, ThankHandler {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "ideasManager";

    // Contacts table name
    private static final String TABLE_IDEAS = "Idea";

    // Contacts table name
    private static final String TABLE_THANK = "Thanks";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_IDEAS + " ( "
                + AttributesConstants.m_Idea_id + " TEXT PRIMARY KEY,"
                +  AttributesConstants.m_Idea_Short + " TEXT,"
                + AttributesConstants.m_Idea_Desc + " TEXT,"
                + AttributesConstants.m_Idea_status + " TEXT,"
                + AttributesConstants.m_Idea_type + " TEXT)";


        String CREATE_THANK_TABLE = "CREATE TABLE " + TABLE_THANK + " ( "
                + AttributesThank.m_Idea_id + " TEXT PRIMARY KEY, "
                + AttributesThank.m_person + " TEXT)";


        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_THANK_TABLE);


    //    addDummyIdeas(db);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDEAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANK);

        // Create tables again
        onCreate(db);
    }



    public void addDummyIdeas(SQLiteDatabase db)
    {
        Log.i("addIdea", "enter is AddDummyIdeas");

        ArrayList<Idea> ideaslist = new ArrayList<Idea>();
        ideaslist.add(new Idea("Buy a homeless person a Big Mac",
                "Ok, maybe not necessarily a Big Mac, but something delicious, something he will remember for a long time and that will make him very cheerful and grateful: a donut, a shaorma, a pancake! What would you like to receive?",
                Idea_Type.FINANCE));
        ideaslist.add(new Idea("Donate to an NGO 20 bucks",
                "What's important for you? A child's education, the environment, saving a puppy's life? All three of these? Think about this and make the proper donation! We believe in you!",
                Idea_Type.FINANCE));
        ideaslist.add(new Idea("Smile to all people you meet today, strangers or not",
                "Just think about the good things in your life and share the positive mood by smiling to all people you meet. Your gorgeous smile will make a lot of people happy! You can do this!",
                Idea_Type.POSITIVE_ATITUDE));

        ideaslist.add(new Idea("Buy your mother a bouquet of flowers",
                "I will just do so myself now. I know my mother loves flowers and I bet your mother does to! "
                , Idea_Type.FINANCE));
        ideaslist.add(new Idea("Make a gift to someone special in your life",
                "You have someone dear to you in your life? A very good friend, a boyfriend/girlfriend, a sibling? Don't waste time and show it to him/her! What does he/she like the most?"
                , Idea_Type.FINANCE));
        ideaslist.add(new Idea("Post a motivational quote somewhere where close people to you can see it",
                "A very good friend did so for me and it helped me a lot! You can put it in the kitchen, at the entrance e.t.c. Whenever people you care about will be sad, the message will steal a smile from them. "
                , Idea_Type.POSITIVE_ATITUDE));
        ideaslist.add(new Idea("Help an elder with his/her luggage",
                "A lot of elder people need help when they carry luggage from market. No need to have super powers like Superman. Just a little bit of attention and ambition and you can help them.",
                Idea_Type.VOLUNTEER));
        ideaslist.add(new Idea("Make a sandwich to someone dear to you",
                "Make your husband/hife/parents some sandwiches before they go to work. What do you think?"
                , Idea_Type.VOLUNTEER));
        ideaslist.add(new Idea("Help a friend with his homework",
                "Are you good at math and your friend sucks? Help him with his homework by explaining him what he doesn't understand"
                , Idea_Type.VOLUNTEER));

        for(Idea idea : ideaslist)
        {
            ContentValues values = new ContentValues();
            values.put(AttributesConstants.m_Idea_Desc, idea.getM_Description());
            values.put(AttributesConstants.m_Idea_Short, idea.getM_Idea());
            values.put(AttributesConstants.m_Idea_type, idea.getM_ideaType().toString());

            db.insert(TABLE_IDEAS, null, values);
        }
    }

    public void addThank()
    {
        Log.i("addThank"," a intrat in add Thank");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AttributesThank.m_person, "Gigea");

        db.insert(TABLE_THANK, null, values);
        db.close();
    }



    @Override
    public boolean addIdea(Idea _idea) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.i("ideaInsert ", _idea.getM_id() + " " + _idea.getM_Idea() + " " + _idea.getM_Description() + " " + _idea.getM_ideaType());

        ContentValues values = new ContentValues();
        values.put(AttributesConstants.m_Idea_id, _idea.getM_id());
        values.put(AttributesConstants.m_Idea_Desc, _idea.getM_Description());
        values.put(AttributesConstants.m_Idea_Short, _idea.getM_Idea());
        values.put(AttributesConstants.m_Idea_type, _idea.getM_ideaType().toString());
        values.put(AttributesConstants.m_Idea_status, _idea.get_status().toString());

        db.insert(TABLE_IDEAS, null, values);
        db.close();

        return true;
    }

    public int getSizeThank()
    {
        List<Idea> ideasList = new ArrayList<Idea>();
        int size= 0 ;
        String selectQuery = "SELECT  * FROM " + TABLE_THANK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do
            {
                size++;
            } while (cursor.moveToNext());
        }


        return size;
    }


    @Override
    public Idea getIdea(String _parameter, String _value)
    {
        return null;

    }

    @Override
    public boolean deleteIdea(String _parameter, String _value) {
        return false;
    }


    @Override
    public List<Idea> getAllIdeas()
    {
        List<Idea> ideasList = new ArrayList<Idea>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IDEAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("message", cursor.toString());
                Idea idea = new Idea(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        Idea_Status.valueOf(cursor.getString(3)),
                        Idea_Type.valueOf(cursor.getString(4)));

                ideasList.add(idea);
                Log.i("messageIdea", idea.getM_Idea() + " " + idea.getM_Description() + " " + idea.getM_id() + " " + idea.getM_ideaType());


            } while (cursor.moveToNext());
        }

        // return contact list
        return ideasList;

    }

    public List<Idea> getIdeas(String _criteria, String _value )
    {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Idea> ideasList = new ArrayList<Idea>();

        Cursor cursor = db.query(TABLE_IDEAS, new String[] {
                        AttributesConstants.m_Idea_id,
                        AttributesConstants.m_Idea_Short,
                        AttributesConstants.m_Idea_Desc,
                        AttributesConstants.m_Idea_type,
                        AttributesConstants.m_Idea_status}, _criteria + "=?",
                new String[] { String.valueOf(_value) }, null, null, null, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("message", cursor.getColumnName(2) + " " +  cursor.getString(2));
                Idea idea = new Idea(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        Idea_Status.valueOf(cursor.getString(4)),
                        Idea_Type.valueOf(cursor.getString(3)));

                ideasList.add(idea);

            } while (cursor.moveToNext());
        }


        return ideasList;
    }
    public List<Idea> getIdeasToDo( )
    {
        return getIdeas(AttributesConstants.m_Idea_status, Idea_Status.TO_DO.toString());
    }

    public List<Idea> getIdeasDone()
    {
        return getIdeas(AttributesConstants.m_Idea_status, Idea_Status.DONE.toString());
    }


    public boolean deleteAllIdeas()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IDEAS, null, null);


        db.close();
        if(getAllIdeas().size() == 0)
            return true;
        return false;
    }

    @Override
    public boolean updateIdea(Idea _idea){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AttributesConstants.m_Idea_id, _idea.getM_id());
        values.put(AttributesConstants.m_Idea_status, _idea.get_status().toString());
        values.put(AttributesConstants.m_Idea_type, _idea.getM_ideaType().toString());
        values.put(AttributesConstants.m_Idea_Desc, _idea.getM_Description());
        values.put(AttributesConstants.m_Idea_Short, _idea.getM_Idea());


        db.update(TABLE_IDEAS, values, AttributesConstants.m_Idea_id + " = ?",
                new String[]{_idea.getM_id()});

        return true;
    }


}
