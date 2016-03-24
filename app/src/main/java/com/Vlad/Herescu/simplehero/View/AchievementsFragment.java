package com.Vlad.Herescu.simplehero.View;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.AchievementLogic;
import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.model.Idea_Type;
import com.Vlad.Herescu.simplehero.model.MileStone;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AchievementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AchievementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    AchievementLogic m_logic;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AchievementsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AchievementsFragment newInstance(String param1, String param2) {
        AchievementsFragment fragment = new AchievementsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        m_logic = new AchievementLogic();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int volunteer = 0, smile = 0, dollar = 0;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
     //   ImageView image = (ImageView) view.findViewById(R.id.imageViewMileStone);
        TextView message = (TextView) view.findViewById(R.id.textViewMessage);
        TextView textView = (TextView) view.findViewById(R.id.textViewScore);

        List<Idea> ideas= MainActivity.m_dataBase.getIdeasDone();
        int thanks = MainActivity.m_dataBase.getSizeThank();
        for(Idea idea : ideas)
        {
            if(idea.getM_ideaType().equals(Idea_Type.FINANCE))
                dollar++;
            if(idea.getM_ideaType().equals(Idea_Type.POSITIVE_ATITUDE))
                smile++;
            if(idea.getM_ideaType().equals(Idea_Type.VOLUNTEER))
                volunteer++;
        }


        MileStone stone = m_logic.getMileStone();
        int score = m_logic.getScore();
      //  image.setImageResource(stone.getM_image());
        message.setText(stone.getM_message());

        String scoreText = "Your score : " + score;
        textView.setText(scoreText);

        ((TextView) view.findViewById(R.id.textViewScoreVol)).setText(volunteer + " tasks done");
        ((TextView) view.findViewById(R.id.textViewScoreSmile)).setText(smile + " tasks done");
        ((TextView) view.findViewById(R.id.textViewScoreDollar)).setText(dollar + " tasks done");
        ((TextView) view.findViewById(R.id.textViewScoreThanks)).setText(thanks + " thanks");

        return view;
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
