package com.seth.hello.sharding.sphere;

import com.seth.hello.sharding.sphere.domain.TbOrder;
import com.seth.hello.sharding.sphere.mapper.TbOrderItemMapper;
import com.seth.hello.sharding.sphere.mapper.TbOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ShardingSphereTests {

    @Resource
    private TbOrderMapper tbOrderMapper;

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    /**
     * 1库，1表
     */
    @Test
    public void testInsertOrder() {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setUserId(1L);
        tbOrder.setOrderId(1L);

        tbOrderMapper.insert(tbOrder);
    }

    /**
     * 0库，1表
     */
    @Test
    public void testInsertOrder2() {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setUserId(0L);
        tbOrder.setOrderId(1L);

        tbOrderMapper.insert(tbOrder);
    }

    /**
     * 0库，0表
     */
    @Test
    public void testInsertOrder3() {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setUserId(0L);
        tbOrder.setOrderId(2L);

        tbOrderMapper.insert(tbOrder);
    }

    /**
     * userId 偶数，  查偶数库
     * orderId 偶数， 查偶数表
     */
    @Test
    public void testSelectAll() {
        // w0-ds0
        tbOrderMapper.insert(new TbOrder(0L, 0L));
        // w1-ds1
        tbOrderMapper.insert(new TbOrder(1L, 1L));
        // w1-ds0
        tbOrderMapper.insert(new TbOrder(0L, 1L));
        // w0-ds1
        tbOrderMapper.insert(new TbOrder(1L, 0L));
        List<TbOrder> tbOrders = tbOrderMapper.selectList(null);
        tbOrders.forEach(System.out::println);
        // Logic SQL: INSERT INTO tb_order  ( order_id, user_id )  VALUES  ( ?, ? )
        // Actual SQL: w-ds0 ::: INSERT INTO tb_order_0  ( order_id, user_id )  VALUES  (?, ?) ::: [0, 0]
        // INFO 24220 --- [main] ShardingSphere-SQL Actual SQL: r1-ds0 ::: SELECT  id,order_id,user_id  FROM tb_order_0
        // INFO 24220 --- [main] ShardingSphere-SQL Actual SQL: r2-ds0 ::: SELECT  id,order_id,user_id  FROM tb_order_1
        // INFO 24220 --- [main] ShardingSphere-SQL Actual SQL: r1-ds1 ::: SELECT  id,order_id,user_id  FROM tb_order_0
        // INFO 24220 --- [main] ShardingSphere-SQL Actual SQL: r2-ds1 ::: SELECT  id,order_id,user_id  FROM tb_order_1
        // TbOrder(id=1, orderId=0, userId=0)
        // TbOrder(id=1, orderId=1, userId=0)
        // TbOrder(id=1, orderId=0, userId=1)
        // TbOrder(id=1, orderId=1, userId=1)
    }
}
