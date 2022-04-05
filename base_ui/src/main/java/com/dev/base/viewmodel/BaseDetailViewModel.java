package com.dev.base.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.favorite.Favorite;
import com.dev.common.database.like.Like;
import com.dev.user.UserSession;

import static com.dev.common.database.DaoProvider.commentDao;
import static com.dev.common.database.DaoProvider.favoriteDao;
import static com.dev.common.database.DaoProvider.likeDao;
import static com.dev.common.database.comment.Comment.NEWS_TYPE;

/**
 * @author long.guo
 * @since 2/6/21
 */
public abstract class BaseDetailViewModel extends BaseViewModel {
    public MutableLiveData<Integer> commentCount = new MutableLiveData<>();
    public MutableLiveData<Integer> likeCount = new MutableLiveData<>();
    public MutableLiveData<Like> myLike = new MutableLiveData<>();
    public MutableLiveData<Favorite> myFavorite = new MutableLiveData<>();

    public int commentType = NEWS_TYPE;

    public void loadById(long ownerId) {
        commentCount(ownerId);
        likeCount(ownerId);

        Like like = likeDao().getMyLike(UserSession.getInstance().id(), ownerId, commentType);
        myLike.setValue(like);

        Favorite favorite = favoriteDao().getFavorite(UserSession.getInstance().id(), ownerId, commentType);
        myFavorite.setValue(favorite);
    }

    protected void likeCount(long ownerId) {
        int likeCount = likeDao().getCountByOwnerIdAndType(ownerId, commentType);
        this.likeCount.setValue(likeCount);
    }

    public void commentCount(long ownerId) {
        int commentCount = commentDao().getCountByOwnerIdAndType(ownerId, commentType);
        this.commentCount.setValue(commentCount);
    }

    public void like(Like like) {
        like.id = likeDao().insert(like);
        this.myLike.setValue(like);
        likeCount(like.ownerId);
    }

    public void unLike(long ownerId) {
        if (myLike.getValue() != null)
            likeDao().delete(myLike.getValue());
        myLike.setValue(null);
        likeCount(ownerId);
    }

    public void favorite(Favorite favorite) {
        favorite.id = favoriteDao().insert(favorite);
        Favorite f = favoriteDao().getFavorite(UserSession.getInstance().id(), favorite.ownerId, commentType);
        myFavorite.setValue(f);
    }

    public void unFavorite() {
        if (myFavorite.getValue() != null) {
            favoriteDao().delete(myFavorite.getValue());
            long ownerId = myFavorite.getValue().ownerId;
            Favorite f = favoriteDao().getFavorite(UserSession.getInstance().id(), ownerId, commentType);
            myFavorite.setValue(f);
        }
    }
}
