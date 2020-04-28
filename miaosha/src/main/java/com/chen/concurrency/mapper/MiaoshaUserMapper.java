package com.chen.concurrency.mapper;

import com.chen.concurrency.model.dao.MiaoshaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 程强
 * @date 2020年03月05日 21:16
 * @Description:
 */
public interface MiaoshaUserMapper {
    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id")Long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    void updateUserPassword(MiaoshaUser updateUser);
}
