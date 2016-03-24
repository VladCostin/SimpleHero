package com.Vlad.Herescu.simplehero.BusinessLogic;

import com.Vlad.Herescu.simplehero.R;
import com.Vlad.Herescu.simplehero.View.MainActivity;
import com.Vlad.Herescu.simplehero.model.Idea;
import com.Vlad.Herescu.simplehero.model.MileStone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2/18/2016.
 */
public class AchievementLogic {

    ArrayList<MileStone> _milestones;

    public AchievementLogic()
    {
        _milestones = new ArrayList<MileStone>();
        addMilestones();
    }

    private void addMilestones()
    {
        _milestones.add(new MileStone( R.drawable.hero1,"If you believe a little in yourself, you will find the power to change the world", 0));
        _milestones.add(new MileStone( R.drawable.hero2,"There you go, how is it changing the world?",1));
        _milestones.add(new MileStone( R.drawable.hero3,"Start feeling powerful?", 10));
        _milestones.add(new MileStone( R.drawable.hero4,"The people start to rely on you, friendly neighbourhood hero", 50));
        _milestones.add(new MileStone( R.drawable.hero5,"Cause I'm batman! Ok..maybe not yet, but you're doing a great job", 100));
        _milestones.add(new MileStone( R.drawable.hero6,"Small changes have a big impact, no need to..fly around the earth to turn back the time like Superman did( if that's even possible)", 150));
        _milestones.add(new MileStone(R.drawable.hero8, " The importance of a person's life is defined by what he believes in and how much he fights for what he believes ", 200));
        _milestones.add(new MileStone( R.drawable.hero7,"\"It's the action, not the fruit of the action, that's important. You have to do the right thing. It may not be in your power, may not be in your time, that there'll be any fruit. But that doesn't mean you stop doing the right thing. You may never know what results come from your action. But if you do nothing, there will be no result.\" -  Mahatma Gandhi", 250));
        _milestones.add(new MileStone(R.drawable.hero8, " \"In a gentle way, you can shake the world \" - Mahatma Gandhi ", 300));
        _milestones.add(new MileStone(R.drawable.hero8, " \"Do more than belong: participate. Do more than care: help. Do more than believe: practice. Do more than be fair: be kind. Do more than forgive: forget. Do more than dream: work. \" - William Arthur Ward ", 350));
        _milestones.add(new MileStone(R.drawable.hero8, " \"The purpose of life is to contribute in some way to making things better. \" - Robert F. Kennedy ", 400));
        _milestones.add(new MileStone(R.drawable.hero8, " \"No work is insignificant. All labor that uplifts humanity has dignity and importance and should be undertaken with painstaking excellence. \" - Martin Luther King Jr. ", 450));
        _milestones.add(new MileStone(R.drawable.hero8, " \"I am only one, but I am one. I cannot do everything, but I can do something. And I will not let what I cannot do interfere with what I can do. \" - Edward Everett Hale ", 500));
        _milestones.add(new MileStone(R.drawable.hero8, " \"Never underestimate the difference YOU can make in the lives of others. Step forward, reach out and help. This week reach to someone that might need a lift \" - Pablo ", 550));
        _milestones.add(new MileStone(R.drawable.hero8, " \"I have one life and one chance to make it count for something... My faith demands that I do whatever I can, wherever I am, whenever I can, for as long as I can with whatever I have to try to make a difference. \" - Jimmy Carter ", 600));

    }


    public MileStone getMileStone()
    {

        MileStone stoneShown = _milestones.get(0);
        int score = getScore();

        for(MileStone stone : _milestones)
        {
            if(stone.getM_score() > score )
                break;
            else
                stoneShown = stone;
        }

        return stoneShown;
    }

    public int getScore()
    {
        int score = MainActivity.m_dataBase.getIdeasDone().size();
        int thanks = MainActivity.m_dataBase.getSizeThank() * 25;
        score += thanks;
        return score;
    }

    public int getThanks()
    {
        int thanks = MainActivity.m_dataBase.getSizeThank();
        return thanks;
    }



}
