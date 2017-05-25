package service;

import data.Rates;
import entity.Expenses;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Date;


/**
 * Created by nazar on 25.05.17.
 */
public class ExpensesServiceTest {

    private ExpensesService expensesService = new ExpensesService();

    @Test
    public void testCreateExpenses() throws Exception {
        Assert.assertTrue(expensesService.createExpenses("1900-1-1", "123.2", "AUD", "juice").getState());
        Assert.assertTrue(expensesService.deleteExpensesByDate("1900-1-1").getState());
    }

    @Test
    public void testDeleteExpensesByDate() throws Exception {
        Assert.assertTrue(expensesService.createExpenses("1900-1-1", "123.2", "AUD", "juice").getState());
        Assert.assertTrue(expensesService.deleteExpensesByDate("1900-1-1").getState());
    }

    @Test
    public void testTotal() throws Exception {
        String a = expensesService.total("USD").getMassage();
        String b = expensesService.total("EUR").getMassage();
        String[] first = a.split(" ");
        String[] second = b.split(" ");
        Rates rates = expensesService.getRatesByBase("USD");
        Double end = Double.parseDouble(second[0]) / Double.parseDouble(first[0]);
        end =(Math.round(end * 100.0) / 100.0);
        Double ratesEurbaseUsd = (Math.round(rates.getEUR() * 100.0) / 100.0);
        System.out.println("end = " + end);
        System.out.println(rates);
        Assert.assertEquals(end,ratesEurbaseUsd);
    }

    @Test
    public void testIntrospect() throws Exception {
        Expenses expenses = new Expenses(new Date(), 12.0, "USD", "apples");
            Method method = expensesService.getClass().getDeclaredMethod("introspect", Object.class);
            method.setAccessible(true);
            Assert.assertNotNull(method.invoke(expensesService, expenses));
    }

} 
