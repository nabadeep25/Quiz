package com.nabadeep.quiz2.data;

import com.nabadeep.quiz2.model.Question;

import java.util.ArrayList;

public interface QuestionListAsyncResponse {
    void processfinished(ArrayList<Question> questionArrayList);
}
