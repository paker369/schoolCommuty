package com.dev.common.database.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author long.guo
 * @since 1/30/21
 */
@Dao
public interface UserDao {

    /**
     * 返回所有插入的用户id集合
     *
     * @param users 新用户
     * @return 用户id集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update
    void update(User user);

    @Query("select * from user")
    List<User> findAll();

    @Query("select * from user where id = :id")
    User findById(long id);

    @Query("select * from user where id in (:ids)")
    List<User> findByIds(long[] ids);

    @Query("select * from user where isAdmin = 1 limit 1")
    User findAdmin();

    @Query("select * from user where phone = :phone limit 1")
    User findByPhone(String phone);

    @Query("select * from user where nickName = :nickName")
    User findByNickname(String nickName);

    @Query("select * from user where name like :keyword or nickName like :keyword")
    List<User> search(String keyword);

    @Delete
    void deleteUser(User user);
}
