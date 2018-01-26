package br.ufg.inf.meuapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);

        Log.d("LIFECYCLE","CRIOU");
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideDialog();
        Log.d("LIFECYCLE","INICIOU");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LIFECYCLE","TUDO CERTO. RODANDO");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LIFECYCLE","PAUSOU");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LIFECYCLE","PAROU");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LIFECYCLE","REINICIOU");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE","MORREU");
    }


    public void showDialog(){
        pd = new ProgressDialog(this);
        pd.setMessage("Carregando");
        pd.show();
    }

    public void hideDialog(){
        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (
                    InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {

        hideKeyboard();

        EditText fieldEmail = findViewById(R.id.field_email);
        String text = fieldEmail.getText().toString();

        EditText fieldPassword = findViewById(R.id.field_password);
        String password = fieldPassword.getText().toString();

        if("".equals(text)){
            Snackbar.make(view,R.string.error_field_email,Snackbar.LENGTH_LONG).show();
            return;
        }

        if("".equals(password)){
            Snackbar.make(view,R.string.error_field_password,Snackbar.LENGTH_LONG).show();
            return;
        }

        showDialog();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(getApplicationContext(), SecondActivity.class);

                EditText fieldEmail = findViewById(R.id.field_email);
                String text = fieldEmail.getText().toString();

                myIntent.putExtra("nome",text);
                startActivity(myIntent);
                finish();
            }
        }, 4000);
    }
}
