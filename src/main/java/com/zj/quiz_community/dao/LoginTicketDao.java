package com.zj.quiz_community.dao;

import com.zj.quiz_community.pojo.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author zhaojie
 * @date 2018\10\8 0008 - 20:25
 */
@Mapper
public interface LoginTicketDao {

    String INSERT_FIELD=" user_id,ticket,expired,status ";
    String TABLE_NAME="login_ticket";
    String SELECT_FIELD=" id,"+INSERT_FIELD;


    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{userId},#{ticket},#{expired},#{status})"})
    int addLoginTicket(LoginTicket ticket);

    @Select({"select ",SELECT_FIELD,"from ",TABLE_NAME,"where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status= #{status} where ticket=#{ticket}"})
    void updateStatus(@Param("status") Integer status,@Param("ticket") String ticket);




}
