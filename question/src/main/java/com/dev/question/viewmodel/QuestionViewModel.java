package com.dev.question.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.base.viewmodel.BaseDetailViewModel;
import com.dev.common.base.BaseViewModel;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.question.Question;
import com.dev.user.UserSession;

import java.util.List;

import static com.dev.common.database.DaoProvider.commentDao;
import static com.dev.common.database.DaoProvider.questionDao;

import android.util.Log;

/**
 * @author long.guo
 * @since 2/8/21
 */
public class QuestionViewModel extends BaseDetailViewModel {

    public MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    public MutableLiveData<List<Comment>> myAnswer = new MutableLiveData<>();
    public MutableLiveData<Question> question = new MutableLiveData<>();

    public void searchQuestion(String keyword) {

    }

    public void loadQuestions(long userId) {
        questions.setValue(questionDao().queryAllQuestionByOwner(userId));
    }

    public void loadAnswer() {
        myAnswer.setValue(commentDao().getCommentByOwnerIdAndType(UserSession.getInstance().id()));
        Log.d("洒水", "loadAnswer: "+commentDao().getCommentByOwnerIdAndType(UserSession.getInstance().id()).size());
    }

    public void loadMyAnswer() {
        myAnswer.setValue(commentDao().getCommentByreat(UserSession.getInstance().id()));
        Log.d("洒水", "loadAnswer: "+commentDao().getCommentByreat(UserSession.getInstance().id()).size());
    }

    public void loadAllQuestions() {
        questions.setValue(questionDao().queryAll());
    }

    public void loadQuestionById(long id) {
        Question q = questionDao().queryById(id);
        question.setValue(q);
    }
}
