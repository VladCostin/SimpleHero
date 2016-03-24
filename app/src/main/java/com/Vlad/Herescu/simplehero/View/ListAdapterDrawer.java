package com.Vlad.Herescu.simplehero.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.Vlad.Herescu.simplehero.model.ItemModelDrawer;
import com.Vlad.Herescu.simplehero.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/12/2015.
 */
public class ListAdapterDrawer extends ArrayAdapter<String> {

    ArrayList<ItemModelDrawer> m_items;

    Context m_context;

    public ListAdapterDrawer(Context _context, int textViewResourceId,
                              List<String> objects, ArrayList<ItemModelDrawer> _items)
    {
        super(_context, textViewResourceId, objects);
        m_items = _items;
        m_context = _context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_listview_drawer, parent, false);
        m_items.get(position).setData(rowView, position);

        return rowView;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }
}
