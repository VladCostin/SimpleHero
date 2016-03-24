package com.Vlad.Herescu.simplehero.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.model.Idea_Type;
import com.Vlad.Herescu.simplehero.R;

import java.util.List;

/**
 * Created by Vlad Herescu on 10/14/2015.
 */
public class ListAdapterIdeas extends ArrayAdapter<Idea>
{
    List<Idea> m_ideas;
    Context m_context;
    int m_resource;

    public ListAdapterIdeas(Context _context, int _resource, List<Idea> _values) {
        super(_context, _resource, _values);
        m_context = _context;
        m_ideas = _values;
        m_resource = _resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(m_resource, parent, false);
        TextView text = (TextView) rowView.findViewById(R.id.textViewIdea);
        text.setText(m_ideas.get(position).getM_Idea());
        ImageView image = (ImageView) rowView.findViewById(R.id.imageViewIdeas);

        Idea_Type type = m_ideas.get(position).getM_ideaType();
        switch(type)
        {
            case FINANCE:
                image.setImageDrawable(m_context.getResources().getDrawable(R.drawable.ic_dollar));
                text.setTextColor(m_context.getResources().getColor(R.color.cyanDark));
                break;
            case POSITIVE_ATITUDE:
                image.setImageDrawable(m_context.getResources().getDrawable(R.drawable.ic_smile));
                text.setTextColor(m_context.getResources().getColor(R.color.ornageFont));
                break;
            case VOLUNTEER:
                image.setImageDrawable(m_context.getResources().getDrawable(R.drawable.ic_volunteer));

                break;

        }


        return rowView;
    }
}
