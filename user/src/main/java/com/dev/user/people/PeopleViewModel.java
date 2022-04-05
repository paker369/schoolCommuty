package com.dev.user.people;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.user.User;
import com.dev.common.database.user.follow.Follow;
import com.dev.user.UserSession;

import static com.dev.common.database.DaoProvider.followDao;
import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 2/7/21
 */
public class PeopleViewModel extends BaseViewModel {

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<Follow> follow = new MutableLiveData<>();

    public void getUser(long userId) {
        user.setValue(userDao().findById(userId));

        Follow follow = followDao().findById(userId, UserSession.getInstance().id());
        this.follow.setValue(follow);
    }

    public void follow(long userId) {
        Follow follow = new Follow(userId, UserSession.getInstance().id());
        followDao().insert(follow);
        this.follow.setValue(follow);
    }

    public void cancelFollow(long userId) {
        followDao().delete(follow.getValue());
        follow.setValue(null);
    }
}
