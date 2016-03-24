package com.Vlad.Herescu.simplehero.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.widget.Filter;

/**
 * Created by admin on 2/19/2016.
 */
public class ListAdapterFriends  extends ArrayAdapter<User> implements Observer {

    private ArrayList<User> items;
    private ArrayList<User> itemsAll;

    Boolean m_received;



    //List<User> m_friends;
    Context m_context;
    int m_resource;

    public ListAdapterFriends(Context _context, int _resource, ArrayList<User> _values) {
        super(_context, _resource, _values);


        items = new ArrayList<User>(_values.size());
        items.addAll(_values);
        itemsAll = _values;


        m_context = _context;
        m_resource = _resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;

        if (view == null) {
            view = vi.inflate(m_resource, null);
        }

        User customer = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.textViewFriend);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearLayoutFriend);
        name.setText(customer.get_userName());
        layout.setTag(customer.getM_id());

        return view;

        /*
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(m_resource, null);
        }

        User customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v.findViewById(R.id.customerNameLabel);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.get_userName());
            }
        }
        return v;
        */

        /*
        LayoutInflater inflater = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(m_resource, parent, false);

    //    LinearLayout ll = (LinearLayout) rowView; // get the parent layout view
        TextView text = (TextView) rowView.findViewById(R.id.customerNameLabel);
      //  text.setText(m_friends.get(position));
      //  text.setText(m_friends.get(position).get_userName());
        text.setText(items.get(position).get_userName());
       // Log.i("friends", m_friends.get(position).get_userName());

        return rowView;
        */
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((User)(resultValue)).get_userName();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i("message", "intra in getView" + itemsAll.size() + " " + items.size());
            ArrayList<User> suggestions = new ArrayList<User>();
            if(constraint != null) {

                for (User customer : items) {

                    if(customer.get_userName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(new User(customer.getM_id(), customer.get_userName()));
                    }
                    Log.i("message", customer.get_userName());
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();



                return filterResults;
            } else {
                for (User customer : items) {

                    suggestions.add(new User(customer.getM_id(), customer.get_userName()));
                }



                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {

                Log.i("message PublishResults", itemsAll.size() + " " + items.size());
                addAll((ArrayList<User>) results.values);

            }
            else
            {
                Log.i("message PublishResults", itemsAll.size() + " " + items.size());
                addAll(items);
            }
            notifyDataSetChanged();
        }


    };


    @Override
    public void update(Observable observable, Object data) {

        Log.i("message", "numarul de itemuri este acum" + itemsAll.size() + " ");
        items.clear();
        items.addAll(itemsAll);

    }


    // It gets a View that displays in the drop down popup the data at the specified position
   /* @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(m_resource, parent, false);
        TextView text = (TextView) rowView.findViewById(R.id.textViewFriend);
        text.setText(m_friends.get(position).get_userName());

        return rowView;
    }
    */
}
