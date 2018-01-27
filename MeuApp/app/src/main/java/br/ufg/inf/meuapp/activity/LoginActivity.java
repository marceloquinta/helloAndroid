package br.ufg.inf.meuapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.meuapp.R;
import br.ufg.inf.meuapp.data.SessionHandler;
import br.ufg.inf.meuapp.model.User;
import br.ufg.inf.meuapp.web.WebTaskLogin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

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
        EventBus.getDefault().register(this);
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
        EventBus.getDefault().unregister(this);
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
        String email = fieldEmail.getText().toString();

        EditText fieldPassword = findViewById(R.id.field_password);
        String password = fieldPassword.getText().toString();

        if(isFieldsValidated(email,password)){
            showDialog();

            WebTaskLogin taskLogin = new WebTaskLogin(this, email,password);
            taskLogin.execute();
        }
    }

    @Subscribe
    public void onError(Error error){
        failedLogin(error.getMessage());
    }

    @Subscribe
    public void onUser(User user){
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.saveSession(user,this);
        loginComSucesso();
    }


    private void failedLogin(String errorMessage){
        hideDialog();
        Snackbar.make(getCurrentFocus(),errorMessage,Snackbar.LENGTH_LONG).show();
    }

    private void loginComSucesso() {

        hideDialog();
        finish();
    }

    public boolean isFieldsValidated(String text, String password) {
        if("".equals(text)){
            Snackbar.make(getCurrentFocus(),R.string.error_field_email,Snackbar.LENGTH_LONG).show();
            return false;
        }

        if("".equals(password)){
            Snackbar.make(getCurrentFocus(),R.string.error_field_password,Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
