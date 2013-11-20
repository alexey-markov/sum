package ru.avm.sum.business.service.common;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.avm.sum.business.controller.CAccountant;
import ru.avm.sum.business.controller.SelectRequest;
import ru.avm.sum.data.model.money.Category;
import ru.avm.sum.data.model.money.Deal;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/integration-tests-context.xml"})
public class SAccountantTest {

    @Resource(name = "controller")
    private CAccountant accountant;

    @Test
    public void testGetList() {
        List<Deal> balance = accountant.list("user", new SelectRequest(0L, 100L, null, new Category("group")));
        Assert.assertEquals(balance.size(), 0);
    }

}
