package com.chen.concurrency.model;

import com.chen.concurrency.vo.LoginVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月05日 21:12
 * @Description:
 */
@Getter
@Setter
public class MiaoshaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

    @Override
    public String toString() {
        return "MiaoshaUser{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", head='" + head + '\'' +
                ", registerDate=" + registerDate +
                ", lastLoginDate=" + lastLoginDate +
                ", loginCount=" + loginCount +
                '}';
    }
}
