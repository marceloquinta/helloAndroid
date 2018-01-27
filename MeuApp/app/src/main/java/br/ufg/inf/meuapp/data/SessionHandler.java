package br.ufg.inf.meuapp.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by marceloquinta on 26/01/2018.
 */

public class SessionHandler {

    private final String FIELD_TOKEN  = "token";
    private final String FIELD_EMAIL  = "email";
    private final String GROUP_USER  = "USER_DATA";

    public void saveSession(String email, String token, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(FIELD_EMAIL,email);
        editor.putString(FIELD_TOKEN,token);
        editor.commit();
    }

    public String getToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        return sharedPref.getString(FIELD_TOKEN,"");
    }

    public String getEmail(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        return sharedPref.getString(FIELD_EMAIL,"");
    }

    public void removeSession(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

}
