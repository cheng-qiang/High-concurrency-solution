package com.chen.concurrency.model.message;

import com.chen.concurrency.model.dao.MiaoshaUser;
import lombok.Data;

/**
 * @author 言少钱
 * @date 2020年04月27日 22:27
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Data
public class MiAoShaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
