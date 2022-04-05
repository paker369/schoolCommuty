package com.dev.common.database.question;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

/**
 * @author long.guo
 * @since 2/8/21
 */
@Dao
public interface QuestionDao {

    @Query("select * from question")
    List<Question> queryAll();

    @Transaction
    @Query("select * from question")
    List<QuestionWithComment> queryAllQuestionWithComment();

    @Transaction
    @Query("select * from question where id = :id")
    QuestionWithComment queryQuestionWithCommentById(long id);

    @Query("select * from question where creatorUserId = :ownerId")
    List<Question> queryAllQuestionByOwner(long ownerId);

    @Insert
    void insert(Question q);

    @Query("select * from question where id = :id")
    Question queryById(long id);
}
