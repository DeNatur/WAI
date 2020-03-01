package com.erunetimeterror.wai.utils;

import android.util.Log;
import android.widget.Toast;

import com.erunetimeterror.wai.activities.QuizActivity;

public class Answers {
    private int Introverted = 0;
    private int Extroverted = 0;
    private int Intuitive = 0;
    private int Sensing = 0;
    private int Thinking = 0;
    private int Feeling = 0;
    private int Judging = 0;
    private int Perceiving = 0;

    public void setAnswer(String key, int point, int selection)
    {
        switch (key) {
            case "EI": {
                if (selection == 0 || selection == 1)
                    setIntroverted(getIntroverted() + point);
                else
                    setIntroverted(getExtroverted() + point);
            }
            break;
            case "SN": {
                if (selection == 0 || selection == 1)
                    setSensing(getSensing() + point);
                else
                    setIntuitive(getIntuitive() + point);
            }
            break;
        }
        printAnswers();
    }

    public void printAnswers()
    {
        Log.i("stats: ", Introverted + " " + Extroverted + " " + Intuitive + " " + Sensing);
    }

    private int getIntroverted() {
        return Introverted;
    }

    private void setIntroverted(int introverted) {
        Introverted = introverted;
    }

    private int getExtroverted() {
        return Extroverted;
    }

    private void setExtroverted(int extroverted) {
        Extroverted = extroverted;
    }

    private int getIntuitive() {
        return Intuitive;
    }

    private void setIntuitive(int intuitive) {
        Intuitive = intuitive;
    }

    private int getSensing() {
        return Sensing;
    }

    private void setSensing(int sensing) {
        Sensing = sensing;
    }

    private int getThinking() {
        return Thinking;
    }

    private void setThinking(int thinking) {
        Thinking = thinking;
    }

    private int getFeeling() {
        return Feeling;
    }

    private void setFeeling(int feeling) {
        Feeling = feeling;
    }

    private int getJudging() {
        return Judging;
    }

    private void setJudging(int judging) {
        Judging = judging;
    }

    private int getPerceiving() {
        return Perceiving;
    }

    private void setPerceiving(int perceiving) {
        Perceiving = perceiving;
    }
}