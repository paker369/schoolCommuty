package com.dev.news.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.base.viewmodel.BaseDetailViewModel;
import com.dev.common.base.BaseViewModel;
import com.dev.common.database.favorite.Favorite;
import com.dev.common.database.like.Like;
import com.dev.common.database.news.News;
import com.dev.common.database.news.NewsWithCommentAndLike;
import com.dev.user.UserSession;

import java.util.List;

import static com.dev.common.database.DaoProvider.commentDao;
import static com.dev.common.database.DaoProvider.favoriteDao;
import static com.dev.common.database.DaoProvider.likeDao;
import static com.dev.common.database.DaoProvider.newsDao;
import static com.dev.common.database.comment.Comment.NEWS_TYPE;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class NewsViewModel extends BaseDetailViewModel {
    public MutableLiveData<List<NewsWithCommentAndLike>> newsList = new MutableLiveData<>();

    public void loadNews(String category) {
        if (category.equals("全部")) {
            newsList.setValue(newsDao().getNewsWithCommentsAndLike());
        } else {
            newsList.setValue(newsDao().getNewsWithCommentsAndLikeByCategory(category));
        }
    }

    public MutableLiveData<News> news = new MutableLiveData<>();
    public MutableLiveData<Integer> commentCount = new MutableLiveData<>();
    public MutableLiveData<Integer> likeCount = new MutableLiveData<>();
    public MutableLiveData<Like> myLike = new MutableLiveData<>();
    public MutableLiveData<Favorite> myFavorite = new MutableLiveData<>();

    public void loadById(long ownerId) {
        News news = newsDao().getNewsById(ownerId);
        this.news.setValue(news);

        commentCount(ownerId);
        likeCount(ownerId);

        Like like = likeDao().getMyLike(UserSession.getInstance().id(), ownerId, NEWS_TYPE);
        myLike.setValue(like);

        Favorite favorite = favoriteDao().getFavorite(UserSession.getInstance().id(), ownerId, NEWS_TYPE);
        myFavorite.setValue(favorite);
    }
}
