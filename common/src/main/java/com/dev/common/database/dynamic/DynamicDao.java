package com.dev.common.database.dynamic;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dev.common.database.user.User;

import java.util.List;

/**
 * @author long.guo
 * @since 1/30/21
 */
@Dao
public interface  DynamicDao {
    @Query("select * from dynamic order by top")
    List<Dynamic> findAll();

    @Query("select * from dynamic where userId = :userId")
    List<Dynamic> findAllByUserId(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Dynamic dynamic);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(Dynamic... dynamic);

    @Query("select * from dynamic where id = :id")
    Dynamic findById(long id);

    @Query("select * from dynamic where  userId in (:ids)")
    List<Dynamic> findInIds(List<Long> ids);

    @Query("select * from dynamic where  dynamicType in (:type)")
    List<Dynamic> findByType(String type);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(Dynamic dynamic);


    @Delete
    void deleteUser(Dynamic dynamic);
}
