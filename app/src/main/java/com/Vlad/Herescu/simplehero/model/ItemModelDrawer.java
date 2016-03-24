package com.Vlad.Herescu.simplehero.model;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Vlad.Herescu.simplehero.R;

/**
 * Created by admin on 10/12/2015.
 */
public class ItemModelDrawer
{

    Drawable m_icon;
    String m_textViewTitle;
    int m_color;
    boolean m_selected;

    public ItemModelDrawer( Drawable _icon, String _textViewTitle, int _color, boolean _selected)
    {
        m_icon = _icon;
        m_textViewTitle = _textViewTitle;
        m_color = _color;
        m_selected = _selected;
    }

    public ItemModelDrawer( String _textViewTitle)
    {
        m_textViewTitle = _textViewTitle;
    }


    public void setData(View _view, int position)
    {
        TextView textView = (TextView) _view.findViewById(R.id.textViewDrawer);
        textView.setText(m_textViewTitle);
        if(m_selected == true)
            textView.setTextColor( m_color);

        ImageView image = (ImageView) _view.findViewById(R.id.imageViewDrawer);
        image.setImageDrawable(m_icon);
    }


    public Drawable getM_icon() {
        return m_icon;
    }

    public void setM_icon(Drawable m_icon) {
        this.m_icon = m_icon;
    }

    public String getM_textViewTitle() {
        return m_textViewTitle;
    }

    public void setM_textViewTitle(String m_textViewTitle) {
        this.m_textViewTitle = m_textViewTitle;
    }
    public void set_selected(boolean _selected)
    {
        m_selected = _selected;
    }


}
