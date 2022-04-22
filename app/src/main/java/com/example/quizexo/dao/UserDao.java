package com.example.quizexo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quizexo.models.Choice;
import com.example.quizexo.models.User;
import com.example.quizexo.models.UserAnswers;

import java.util.List;
import java.util.Optional;

@Dao
public interface UserDao {

    @Insert
    public void save(User user);

    @Insert
    public void saveAll(User ...users);

    @Update
    public void update(User user);

    @Delete
    public void delete(User user);

    @Query("select * from user where username = :username")
    public User getByUsername(String username);

    @Query("select * from user")
    public List<User> getAll();

    @Query("select * from user")
    public List<UserAnswers> getAllUserAnswer();

    @Query("select * from user where username = :username")
    public UserAnswers getUserAnswer(String username);


}
