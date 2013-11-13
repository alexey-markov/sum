package ru.avm.sum.business.service.common;

import junit.framework.Assert;
import org.junit.Test;
import ru.avm.sum.data.model.money.Deal;
import ru.avm.sum.data.model.money.Category;
import ru.avm.sum.business.controller.SelectRequest;
import ru.avm.sum.business.controller.CAccountant;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class SAccountantTest {

    @Resource(name = "controller")
    private CAccountant accountant;

    @Test
    public void testGetList() {
        List<Deal> balance = accountant.list(new SelectRequest(0L, 100L, null, new Category("group")));
        Assert.assertEquals(balance.size(), 0);
    }

}
