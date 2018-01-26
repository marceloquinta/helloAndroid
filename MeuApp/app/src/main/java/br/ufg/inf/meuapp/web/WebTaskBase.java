package br.ufg.inf.meuapp.web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public abstract class WebTaskBase extends AsyncTask<Void, Void, Void> {

    private static int TIMEOUT = 60;
    private static String BASE_URL = "https://www.uol.com.br";

    private String serviceURL;
    private Context context;
    private Error error;
    private String responseString;
    private int responseHttpStatus;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public WebTaskBase(Context context, String serviceURL) {
        this.context = context;
        this.serviceURL = serviceURL;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if(!isOnline()){
            error = new Error("Você está com problemas de conexão com a internet");
            responseString = null;
            return null;
        }

        doRegularCall();

        return null;
    }

    private void doRegularCall() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, getRequestBody());

        client = client.newBuilder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();

        Request request = new Request.Builder()
                .url(getUrl())
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            responseString =  response.body().string();
            responseHttpStatus = response.code();
        } catch (IOException e) {
            if(e.getClass() == SocketTimeoutException.class){
                error = new Error("Servidor não responde. Tente mais tarde.");
            }else{
                error = new Error("WebTask failed calling service " + serviceURL
                        + ":" + e.getMessage());
            }

        }
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(error!= null){
           handleError();
        }else{
            try {
                JSONObject responseJSON = new JSONObject(responseString);
                String errorMessage = responseJSON.getString("error");
                EventBus.getDefault().post(new Error(errorMessage));
            } catch (JSONException e) {
                handleResponse(responseString);
            } catch (NullPointerException e) {
                handleResponse("");
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void handleError(){
        EventBus.getDefault().post(error);
    }

    abstract void handleResponse(String response);

    abstract String getRequestBody();

    public Context getContext() {
        return context;
    }

    public int getResponseHttpStatus() {
        return responseHttpStatus;
    }

    public Error getError() {
        return error;
    }

    public String getUrl(){
        return BASE_URL + serviceURL;
    }

}

