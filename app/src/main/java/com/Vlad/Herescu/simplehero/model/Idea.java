package com.Vlad.Herescu.simplehero.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Vlad Herescu on 10/14/2015.
 */
public class Idea implements Parcelable
{
    String m_id;

    String m_Idea;

    String m_Description;

    Idea_Type m_ideaType;


    Idea_Status m_status;


    Date m_date;



    int m_score;


    public Idea(String _id, String _idea, String _description, Idea_Type _ideaType, Date _date)
    {
        m_id = _id;
        m_Idea = _idea;
        m_Description = _description;
        m_ideaType = _ideaType;
        m_score = 500;
        m_status = Idea_Status.NOT_DONE;
        m_date = _date;
    }




    public Idea(String _id, String _idea, Idea_Type _ideaType)
    {
        m_Idea = _idea;
        m_id = _id;
        m_ideaType = _ideaType;
        m_score = 500;
        m_status = Idea_Status.NOT_DONE;
    }
    public Idea(String _id, String _idea,String _desc,Idea_Status _status, Idea_Type _ideaType)
    {
        m_Idea = _idea;
        m_id = _id;
        m_ideaType = _ideaType;
        m_score = 500;
        m_status = _status;
        m_Description = _desc;

    }


    /*
    public Idea(String _id, String _idea,String _description,  Idea_Type _ideaType)
    {
        m_Idea = _idea;
        m_ideaType = _ideaType;
        m_score = 500;
        m_Description = _description;
        m_status = Idea_Status.NOT_DONE;
    }
    */
    // Parcelling part
    public Idea(Parcel _in){
        m_id = _in.readString();
        m_Idea = _in.readString();
        m_Description =_in.readString();
        m_ideaType = Idea_Type.valueOf(_in.readString());
        m_status = Idea_Status.valueOf(_in.readString());


    }

    public String getM_id() {
        return m_id;
    }

    public String getM_Idea() {
        return m_Idea;
    }

    public String getM_Description() {
        return m_Description;
    }


    public Idea_Type getM_ideaType() {
        return m_ideaType;
    }


    public Idea_Status get_status() {
        return m_status;
    }

    public void set_status(Idea_Status m_status) {
        this.m_status = m_status;
    }


    public int getM_score() {
        return m_score;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public Date getM_date() {
        return m_date;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(m_id);
        dest.writeString(m_Idea);
        dest.writeString(m_Description);
        dest.writeString(m_ideaType.toString());
        dest.writeString(m_status.toString());

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Idea createFromParcel(Parcel in) {
            return new Idea(in);
        }

        public Idea[] newArray(int size) {
            return new Idea[size];
        }
    };
}
