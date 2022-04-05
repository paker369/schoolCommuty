package com.dev.question.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.favorite.Favorite;
import com.dev.common.database.like.Like;
import com.dev.common.database.question.Question;
import com.dev.question.activity.FavoriteActivity;
import com.dev.user.UserSession;

import java.util.ArrayList;
import java.util.List;

import static com.dev.common.database.DaoProvider.dynamicDao;
import static com.dev.common.database.DaoProvider.favoriteDao;
import static com.dev.common.database.DaoProvider.likeDao;
import static com.dev.common.database.DaoProvider.questionDao;

/**
 * @author long.guo
 * @since 2/25/21
 */
public class FavoriteViewModel extends BaseViewModel {

    public MutableLiveData<List<FavoriteActivity.FavoriteBean>> favorite = new MutableLiveData<>();
    public void loadFavorite() {
        List<Favorite> f1 = favoriteDao().getFavorite(UserSession.getInstance().id(), Comment.DynamicType);
        List<Favorite> f2 = favoriteDao().getFavorite(UserSession.getInstance().id(), Comment.QuestionType);

        List<Like> l1 = likeDao().getMyLike(UserSession.getInstance().id(), Comment.DynamicType);
        List<Like> l2 = likeDao().getMyLike(UserSession.getInstance().id(), Comment.QuestionType);

        List<FavoriteActivity.FavoriteBean> result = new ArrayList<>();
        for (Favorite favorite : f1) {
            Dynamic dynamic = dynamicDao().findById(favorite.ownerId);
            result.add(new FavoriteActivity.FavoriteBean(dynamic.title, 0));
        }

        for (Favorite favorite : f2) {
            Question question = questionDao().queryById(favorite.ownerId);
            result.add(new FavoriteActivity.FavoriteBean(question.title, 0));
        }

        for (Like like : l1) {
            Dynamic dynamic = dynamicDao().findById(like.ownerId);
            result.add(new FavoriteActivity.FavoriteBean(dynamic.title, 1));
        }

        for (Like like : l2) {
            Question question = questionDao().queryById(like.ownerId);
            result.add(new FavoriteActivity.FavoriteBean(question.title, 1));
        }
        this.favorite.setValue(result);
    }
}
