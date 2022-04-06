package com.dev.main.ui.dynamic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.base.viewmodel.BaseDetailViewModel;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.question.Question;
import com.dev.common.database.user.follow.Follow;
import com.dev.user.UserSession;

import java.util.ArrayList;
import java.util.List;

import static com.dev.common.database.DaoProvider.dynamicDao;
import static com.dev.common.database.DaoProvider.followDao;
import static com.dev.common.database.DaoProvider.questionDao;

public class DynamicViewModel extends BaseDetailViewModel {

    public MutableLiveData<List<Dynamic>> dynamicList = new MutableLiveData<>();
    public MutableLiveData<Dynamic> dynamic = new MutableLiveData<>();
    public MutableLiveData<List<Question>> question = new MutableLiveData<>();

    public void loadDynamic(long userId) {
        if (userId == -1) {
            dynamicList.setValue(dynamicDao().findAll());
        } else {
            dynamicList.setValue(dynamicDao().findAllByUserId(userId));
        }
    }


    public void loadDynamic(String type) {

            dynamicList.setValue(dynamicDao().findByType(type));

    }

    public void loadQuestions() {
        question.setValue(questionDao().queryAll());
    }

    @Override
    public void loadById(long ownerId) {
        super.loadById(ownerId);
        Dynamic d = dynamicDao().findById(ownerId);
        dynamic.setValue(d);
    }

    public void loadDynamicFlower(long userId) {
        List<Follow> follows = followDao().findByFollowerId(UserSession.getInstance().id());
        List<Long> ids = new ArrayList<>();
        for (Follow follow : follows) {
            ids.add(follow.owner);
        }
        dynamicList.setValue(dynamicDao().findInIds(ids));
//        if (userId == -1) {
//            dynamicList.setValue(dynamicDao().findAll());
//        } else {
//            dynamicList.setValue(dynamicDao().findAllByUserId(userId));
//        }
    }
}