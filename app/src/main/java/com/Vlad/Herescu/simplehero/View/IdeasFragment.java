package com.Vlad.Herescu.simplehero.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.ManagerSingleton;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IdeasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IdeasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdeasFragment extends Fragment implements AdapterView.OnItemClickListener, Observer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    ListView m_listViewIdeas;
    public static ArrayAdapter<Idea> m_adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ideas.
     */
    // TODO: Rename and change types and number of parameters
    public static IdeasFragment newInstance(String param1, String param2) {
        IdeasFragment fragment = new IdeasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public IdeasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ideas, container, false);


        ManagerSingleton.getInstance().get_IdeasHandler().addObserver(this);
        ManagerSingleton.getInstance().get_ServerHandler().getNewData();

        m_listViewIdeas = (ListView) view.findViewById(R.id.listViewIdeas);

        m_adapter = new ListAdapterIdeas(getActivity(),R.layout.item_listview_ideas,
                         ManagerSingleton.getInstance().get_IdeasHandler().get_ideas());
        m_listViewIdeas.setOnItemClickListener(this);
        m_listViewIdeas.setAdapter(m_adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddIdea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityAddIdea();
            }
        });

        return view;
    }

    public void startActivityAddIdea()
    {

     //   m_ideas.getM_ideas().add(0,new Idea("aaa", "bbb", Idea_Type.FINANCE));
      //  m_adapter.notifyDataSetChanged();
        Intent intent = new Intent(getActivity(), ActivityAddIdea.class);
        startActivity(intent);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
            List<Idea> ideas = ManagerSingleton.getInstance().get_IdeasHandler().get_ideas();


            Idea idea = ideas.get(position);
            Intent intent = new Intent(getActivity(), ActivitySeeIdea.class);
            intent.putExtra("idea", idea);
            getActivity().startActivity(intent);

    }

    @Override
    public void update(Observable observable, Object data)
    {
        m_adapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
