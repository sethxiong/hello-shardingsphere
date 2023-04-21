package com.seth.hello.sharding.sphere;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.seth.hello.sharding.sphere.domain.TbOrder;
import com.seth.hello.sharding.sphere.domain.TbOrderItem;
import com.seth.hello.sharding.sphere.mapper.TbOrderItemMapper;
import com.seth.hello.sharding.sphere.mapper.TbOrderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ShardingSphereProxyTests {

    @Resource
    private TbOrderMapper tbOrderMapper;
    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    /**
     * userId 偶数，  查偶数库
     * orderId 偶数， 查偶数表
     */
    @Transactional
//    @Rollback
    @Test
    public void testSelectAll() {
        int insert = tbOrderMapper.insert(new TbOrder(1000L,100L, 0L));
        TbOrder tbOrder = tbOrderMapper.selectById(1000L);
        Assertions.assertNotNull(tbOrder);

        if (insert > -1) {
            throw new RuntimeException("ex");
        }

        TbOrderItem item = new TbOrderItem();
        item.setId(100L);
        item.setUserId(1L);
        item.setOrderId(1L);
        item.setOrderItemId(1);
        tbOrderItemMapper.insert(item);
    }

    @Test
    void listAll() {
        tbOrderMapper.selectList(Wrappers.emptyWrapper())
                .forEach(System.out::println);
    }

    @Test
    void add() {
        int insert = tbOrderMapper.insert(new TbOrder(100L, 0L));
        Assertions.assertTrue(insert > 0);
    }

    @Test
    void noExist() {
        TbOrder tbOrder = tbOrderMapper.selectById(1000);
        Assertions.assertNull(tbOrder, "tbOrder 1000 exist");
        TbOrderItem tbOrderItem = tbOrderItemMapper.selectById(100);
        Assertions.assertNull(tbOrderItem, "tbOrderItem 100 exist");
    }
}
