package br.ufg.inf.meuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.ufg.inf.meuapp.R;
import br.ufg.inf.meuapp.data.SessionHandler;
import br.ufg.inf.meuapp.model.User;

public class UserActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionHandler sessionHandler = new SessionHandler();
        try {
            user = sessionHandler.getUser(this);
            getSupportActionBar().setTitle(user.getUsername());
            ImageView imageView = findViewById(R.id.imageview_user);
            Picasso.with(this).load(user.getPhotoUrl()).into(imageView);
        } catch (RuntimeException x){
            sessionHandler.removeSession(this);
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
        }


    }
}
