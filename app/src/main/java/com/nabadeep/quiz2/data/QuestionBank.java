package com.nabadeep.quiz2.data;

import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nabadeep.quiz2.controller.Appcontroller;
import com.nabadeep.quiz2.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.nabadeep.quiz2.controller.Appcontroller.*;
import static com.nabadeep.quiz2.controller.Appcontroller.TAG;

public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();


    public List<Question> getQuestion(final QuestionListAsyncResponse callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json",
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            try {
                                Question question=new Question();
                                question.setQuestion(response.getJSONArray(i).get(0).toString());
                                question.setAnswer(response.getJSONArray(i).getBoolean(1));
                                questionArrayList.add(question);
                                Log.d("JS", "onResponse: "+question);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(null != callback) callback.processfinished(questionArrayList);
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Appcontroller.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;

    }
}
//}
