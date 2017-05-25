package repository;

import entity.Expenses;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nazar on 24.05.17.
 */
public class TestExpensesRepository {

    private ExpensesRepository expensesRepository = new ExpensesRepository();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testAddExpenses()  throws Exception{
        Expenses expenses = new Expenses(new Date(), 1234.1, "USD", "some product name");
        Assert.assertTrue(expensesRepository.addExpenses(expenses));
        expensesRepository.deleteByObj(expenses);
    }

    @Test
    public void testAddExpensesWithEmptyObj() throws Exception{
        Expenses exp = new Expenses();
        exp.setDate(new Date());
        Assert.assertFalse(expensesRepository.addExpenses(exp));
    }

    @Test
    public void testAllExpenses() throws Exception{
        Expenses expenses = new Expenses(new Date(), 1234.1, "USD", "some product name");
        expensesRepository.addExpenses(expenses);
        List<Expenses> list = expensesRepository.getAllExpenses();
        Assert.assertNotNull(list);
        expensesRepository.deleteByObj(expenses);
    }

    @Test
    public void testDeleteByDate() throws Exception{
        Expenses expenses = new Expenses(simpleDateFormat.parse("1900-11-1"), 1834.1, "USD", "some product name");
        Expenses expenses1 = new Expenses(simpleDateFormat.parse("1900-11-1"), 34.1, "EUR", "some product name");
        expensesRepository.addExpenses(expenses);
        expensesRepository.addExpenses(expenses1);
        expensesRepository.deleteByDate(simpleDateFormat.parse("1900-11-1"));
        List<Expenses> listAfter = expensesRepository.getAllExpenses();
        Iterator<Expenses> iterator = listAfter.iterator();
        while (iterator.hasNext()){
            Expenses exp = iterator.next();
            if (exp.getDate().equals(simpleDateFormat.parse("1900-11-1"))) Assert.fail();
        }
    }


}
