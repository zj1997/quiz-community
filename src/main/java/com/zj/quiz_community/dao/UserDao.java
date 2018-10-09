package com.zj.quiz_community.dao;


import com.zj.quiz_community.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 22:39
 */
@Mapper
public interface UserDao {
   //注意抽取的关键字的空格
    String INSERT_FILED=" name,password,salt,head_url ";
    String TABLE_NAME="user";
    String SELECT_FILED=" id,"+INSERT_FILED;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILED,") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ",SELECT_FILED,"from ",TABLE_NAME," where id=#{id}"})
    User selectById(Integer id);


    @Select({"select ",SELECT_FILED,"from ",TABLE_NAME," where name=#{name}"})
    User selectByName(String name);


    @Update({"update ",TABLE_NAME," set password = #{password} where id =#{id}"})
    int updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME," where id =#{id}"})
    int deleteById(Integer id);

}
