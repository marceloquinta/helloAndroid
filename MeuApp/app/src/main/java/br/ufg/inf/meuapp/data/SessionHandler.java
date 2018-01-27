package br.ufg.inf.meuapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import br.ufg.inf.meuapp.R;
import br.ufg.inf.meuapp.model.User;

/**
 * Created by marceloquinta on 26/01/2018.
 */

public class SessionHandler {

    private final String FIELD_NAME = "name";
    private final String FIELD_TOKEN = "token";
    private final String FIELD_PHOTO_URL = "photo_url";
    private final String GROUP_USER  = "USER_DATA";

    public void saveSession(User user, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(FIELD_TOKEN,user.getToken());
        editor.putString(FIELD_NAME,user.getUsername());
        editor.putString(FIELD_PHOTO_URL,user.getPhotoUrl());
        editor.commit();
    }

    public User getUser(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        String name  =  sharedPref.getString(FIELD_NAME,"");
        String token  =  sharedPref.getString(FIELD_TOKEN,"");
        String photoUrl  =  sharedPref.getString(FIELD_PHOTO_URL,"");

        if( "".equals(name) || "".equals(token) ){
            throw new RuntimeException(context.getString(R.string.error_no_user));
        }

        User user = new User();
        user.setUsername(name);
        user.setToken(token);
        user.setPhotoUrl(photoUrl);

        return user;
    }


    public void removeSession(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(GROUP_USER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

}
