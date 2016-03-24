package com.Vlad.Herescu.simplehero.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Vlad.Herescu.simplehero.logic.BusinessLogic.ManagerSingleton;
import com.Vlad.Herescu.simplehero.R;

public class ActivityAddIdea extends AppCompatActivity {

    EditText m_shortDesc;
    EditText m_longDesc;
    TextView m_textRemainingLetters;
    TextView m_textError;

    int m_maxLetter;

    RadioGroup m_radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_add_idea);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabUploadIdea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();
            }
        });

        m_shortDesc = (EditText) findViewById(R.id.editTextIdeaShort);
        m_longDesc = (EditText) findViewById(R.id.editTextIdeaDescription);
        m_textRemainingLetters = (TextView) findViewById(R.id.textViewRemainingLetters);
        m_textError = (TextView) findViewById(R.id.textViewErrorSelection);
        m_radioGroup = (RadioGroup) findViewById(R.id.radioGroupIdeaType);
        m_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                m_textError.setVisibility(View.GONE);
            }
        });
        m_maxLetter = 100;

        m_shortDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                m_textRemainingLetters.setText(Integer.toString(m_maxLetter - s.length()) + " letters remaining");
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void sendData()
    {
        if(isDataSpecified())
        {
            String idea = m_shortDesc.getText().toString();
            String optional = m_longDesc.getText().toString();
            int radioButtonID = m_radioGroup.getCheckedRadioButtonId();
            View radioButton = m_radioGroup.findViewById(radioButtonID);


            boolean pushSuccess = ManagerSingleton.getInstance().get_ServerHandler()
                                 .pushIdea(radioButton.getTag().toString(), idea, optional);
            if(pushSuccess)
            {
                m_shortDesc.setText("");
                m_longDesc.setText("");
                m_textRemainingLetters.setText(Integer.toString(m_maxLetter));

                Toast.makeText(this, "Idea sent", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "The idea could not be sent", Toast.LENGTH_LONG).show();

        }


    }

    /**
     *checks if the type of the idea was mentioned
     * @return : if a type is being checked
     */
    public boolean isTypeChecked()
    {
        int radioButtonID = m_radioGroup.getCheckedRadioButtonId();
        if( radioButtonID == -1)
            return false;
        return true;
    }

    public boolean isIdeaSpecified()
    {
        String idea = m_shortDesc.getText().toString();
        Log.i("the idea wirttern", idea);
        if( idea.equals(""))
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

        if(isTypeChecked() == false)
        {
            m_textError.setVisibility(View.VISIBLE);
            typeChecked = false;
        }
        if(isIdeaSpecified() == false)
        {
            m_textRemainingLetters.setText("Please specify the idea");
            ideaSpecified = false;
        }
        return (ideaSpecified == true && typeChecked == true);
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

}
