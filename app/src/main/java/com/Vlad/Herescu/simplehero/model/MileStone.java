package com.Vlad.Herescu.simplehero.model;

import android.widget.ImageView;

/**
 * Created by admin on 2/18/2016.
 */
public class MileStone
{

    private int m_image;

    private String m_message;

    private int m_score;

    public MileStone(int _image, String _message, int _score)
    {

        m_image = _image;
        m_message   = _message;
        m_score =  _score;
    }

    public String getM_message() {
        return m_message;
    }

    public void setM_message(String m_message) {
        this.m_message = m_message;
    }

    public int getM_image() {
        return m_image;
    }

    public void setM_image(int m_image) {
        this.m_image = m_image;
    }

    public int getM_score() {
        return m_score;
    }

    public void setM_score(int m_score) {
        this.m_score = m_score;
    }
}
