package com.Vlad.Herescu.simplehero.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.ManagerSingleton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThankFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    AutoCompleteTextView m_friends;
    EditText m_textViewMessage;
    String m_id;

    private ListAdapterFriends m_adapter;
    TextView m_textErorNoMessage;
    TextView m_textErrorNoHero;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThankFragment newInstance(String param1, String param2) {
        ThankFragment fragment = new ThankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ThankFragment() {
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


        View view =inflater.inflate(R.layout.fragment_thank, container, false);
        m_textViewMessage = (EditText) view.findViewById(R.id.editTexthanksMessage);
        m_friends = (AutoCompleteTextView) view.findViewById(R.id.spinnerListFriends);
        m_textErrorNoHero = (TextView) view.findViewById(R.id.textViewErrorHeroName);
        m_textErorNoMessage = (TextView) view.findViewById(R.id.textViewErrorThankMessage);

        ManagerSingleton.getInstance().get_facebook().getFriends();

        setSpinner();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabThankPerson);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isDataSpecified()) {


                    String message = m_textViewMessage.getText().toString();

                    ManagerSingleton.getInstance().get_ServerHandler().sendThanksPrice(m_id, message);

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    m_textViewMessage.setText("");
                    m_friends.setText("");
                    Toast.makeText(getActivity(), "Thank message sent", Toast.LENGTH_SHORT).show();

                }
            }
        });

        m_textViewMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                m_textErorNoMessage.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        m_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    m_textErrorNoHero.setVisibility(View.GONE);
                    m_friends.showDropDown();
            }
        });


        return view;
    }

    private void setSpinner() {

       m_adapter = new ListAdapterFriends(getActivity(),R.layout.item_listview_friends,
               ManagerSingleton.getInstance().getM_friends().get_user());
        ManagerSingleton.getInstance().getM_friends().addObserver(m_adapter);

        m_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_id = view.getTag().toString();
            }
        });


        m_friends.setAdapter(m_adapter);


    }

    /**
     *checks if the type of the idea was mentioned
     * @return : if a type is being checked
     */
    public boolean isHeroSelected()
    {
       if(m_id != null && m_friends.getText().toString().equals("") == false)
        return true;
        return false;
    }

    public boolean isMessageSpecified()
    {
       if(m_textViewMessage.getText().toString().equals(""))
           return false;
        return true;
    }

    /**
     *
     * @return true if all the information needed was specified
     */

    public boolean isDataSpecified()
    {
        boolean typeChecked = true, ideaSpecified = true;

        if(isHeroSelected() == false)
        {
            m_textErrorNoHero.setVisibility(View.VISIBLE);
            typeChecked = false;
        }
        if(isMessageSpecified() == false)
        {
            m_textErorNoMessage.setVisibility(View.VISIBLE);
            ideaSpecified = false;
        }
        return (ideaSpecified == true && typeChecked == true);
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
