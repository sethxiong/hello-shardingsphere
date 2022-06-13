package com.seth.hello.sharding.sphere.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_order")
public class TbOrder implements Serializable {

    public TbOrder() {
    }

    public TbOrder(Long userId, Long orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private Long orderId;

    @TableField("user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;
}