package com.nabadeep.quiz2.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
   private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void sethighscore(int score)
    {
       int lasthscoree=preferences.getInt("hscore",0);
        if(score>lasthscoree) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("hscore", score);

            editor.apply();
        }
    }

    public int gethighscore()
    {
        return preferences.getInt("hscore",0);
    }

}
