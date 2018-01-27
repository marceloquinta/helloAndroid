package br.ufg.inf.meuapp.web;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufg.inf.meuapp.R;
import br.ufg.inf.meuapp.model.Task;

public class WebTaskTodo extends WebTaskBase {

    private static final String URL_SERVICE = "todos";
    private static final String FIELD_TOKEN = "token";

    private String token;

    public WebTaskTodo(Context context, String token) {
        super(context, URL_SERVICE);
        this.token = token;
    }

    @Override
    void handleResponse(String response) {
        Gson gson = new Gson();
        List<Task> taskList;
        Type listType = new TypeToken<List<Task>>() {}.getType();
        try {
            taskList  = gson.fromJson(response,listType);
            EventBus.getDefault().post(taskList);
        } catch (JsonSyntaxException x){
            Error error = new Error(getContext().getString(R.string.error_json));
            EventBus.getDefault().post(error);
        }
    }

    /**
     * Retorna o conteúdo do corpo da requisição.
     * @return String representando a requisição em JSON.
     */
    @Override
    String getRequestBody() {
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put(FIELD_TOKEN, token);

        JSONObject json = new JSONObject(requestMap);
        String jsonString = json.toString();

        return  jsonString;
    }
}
