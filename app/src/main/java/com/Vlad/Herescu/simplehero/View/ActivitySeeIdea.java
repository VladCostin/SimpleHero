package com.Vlad.Herescu.simplehero.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Vlad.Herescu.simplehero.logic.BusinessLogic.IdeasManager;
import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.model.Idea_Status;
import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.logic.BusinessLogic.ManagerSingleton;

public class ActivitySeeIdea extends AppCompatActivity implements View.OnClickListener {


    private TextView m_ideaShort;

    private TextView m_description;

    private Button m_doIt;

    private Idea m_idea;

    private String m_startFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_see_idea);

        Bundle data = getIntent().getExtras();
        m_idea =  data.getParcelable("idea");

        m_ideaShort = (TextView) findViewById(R.id.textViewIdea);
        m_description = (TextView) findViewById(R.id.textViewDescription);
        m_doIt = (Button) findViewById(R.id.buttonCanDoThis);
        m_doIt.setOnClickListener(this);

        if(m_idea.get_status().equals(Idea_Status.TO_DO))
            m_doIt.setText("FINISHED");


     //   Log.i("parceable", m_idea.getM_Idea());
     //   Log.i("parceable", idea.getM_Description());

        m_ideaShort.setText(m_idea.getM_Idea());
        m_description.setText(m_idea.getM_Description());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {


        if(m_idea.get_status().equals(Idea_Status.NOT_DONE))
        {
            Log.i("message", m_idea.getM_id());
            Log.i("message", m_idea.getM_Description());
            Log.i("message", m_idea.getM_Idea());
            Log.i("message", m_idea.getM_ideaType().toString());
            Log.i("message", m_idea.get_status().toString());

            m_idea.set_status(Idea_Status.TO_DO);
            Toast.makeText(this, "Idea moved to TODO", Toast.LENGTH_LONG);
            boolean result = ManagerSingleton.getInstance().get_IdeasHandler().removeIdea_NOT_DONE(m_idea);
            Log.i("messageDeletedIdea", result + " ");
            finish();
        }
        else
        if(m_idea.get_status().equals(Idea_Status.TO_DO))
        {

            m_idea.set_status(Idea_Status.DONE);
            ManagerSingleton.getInstance().get_IdeasHandler().removeIdeaToDO(m_idea);
            finish();

        }

    }



}
