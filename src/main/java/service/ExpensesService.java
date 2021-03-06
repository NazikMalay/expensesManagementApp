package service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import data.Rates;
import entity.Expenses;
import repository.ExpensesRepository;
import util.ExpensesComparator;
import util.MyResp;
import util.RetrofitUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nazar on 23.05.17.
 */

public class ExpensesService {

    private final String BASE_URL = "http://api.fixer.io/";
    private MyResp myResp = new MyResp();
    private final String[] ratesList = {"AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK",
            "DKK", "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK",
            "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR", "EUR"};
    public List<String> currencyArrayList = Arrays.asList(ratesList);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private ExpensesRepository expensesRepository = new ExpensesRepository();

    public MyResp createExpenses(String date, String price, String currency, String productName) {
        Expenses exp = new Expenses();
        try {
            exp.setDate(simpleDateFormat.parse(date));
            exp.setPrice(Math.round(Double.parseDouble(price) * 100.0) / 100.0);
        } catch (ParseException e) {
            return new MyResp(false,"Your date is wrong! " + date);
        } catch (NumberFormatException e) {
            return new MyResp(false,"price is wrong " + price + " please enter the number");
        }
        if (currencyArrayList.contains(currency.toUpperCase()) && exp.getPrice() <= 100000.0) {
            exp.setCurrency(currency.toUpperCase());
        } else {
            return new MyResp(false,"Sorry, I have not found such currency,\n you can view the available currency typing 'currency'\n" +
                    "or your price is too high " + exp.getPrice() + " max price 100000");
        }
        exp.setProductName(productName);
        expensesRepository.addExpenses(exp);
        List<Expenses> list = expensesRepository.getAllExpenses();
        Collections.sort(list, new ExpensesComparator());
        return new MyResp(true, list);
    }

    public MyResp getAllExpenses() {
        List<Expenses> list = expensesRepository.getAllExpenses();
        Collections.sort(list, new ExpensesComparator());
        return new MyResp(true,list);
    }

    public MyResp deleteExpensesByDate(String s) {
        try {
            expensesRepository.deleteByDate(simpleDateFormat.parse(s));
            return new MyResp(true);
        } catch (ParseException e) {
            return new MyResp(false);
        }
    }

    public MyResp total(String s) throws Exception {
        if (currencyArrayList.contains(s.toUpperCase())) {
            Gson gson = new Gson();
            CurrencyService currencyService = RetrofitUtil.getClient(BASE_URL).create(CurrencyService.class);
            List<Expenses> list = expensesRepository.getAllExpenses();
            try {
                Iterator<Expenses> iterator = list.iterator();
                Double count = 0.0;
                while (iterator.hasNext()) {
                    Expenses ex = iterator.next();
                    if (ex.getCurrency().equals(s.toUpperCase())) {
                        count += ex.getPrice();
                    } else {
                        JsonElement data = currencyService.getRates(ex.getCurrency(), s.toUpperCase()).execute().body();
                        JsonElement jsonRates = data.getAsJsonObject().get("rates");
                        Rates rates = gson.fromJson(jsonRates, Rates.class);
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) introspect(rates);
                        count += (ex.getPrice() * (Double) hashMap.get(s.toUpperCase()));
                    }
                }
                count = (Math.round(count * 100.0) / 100.0);
                return new MyResp(true,count.toString() + " " + s.toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
                return new MyResp(false,"some problem with getting Currency from " + BASE_URL);
            }
        }
            return new MyResp(false, "Sorry, I have not found such currency,\n you can view the available currency typing 'currency'");
    }

    public Rates getRatesByBase(String base){
        if (currencyArrayList.contains(base.toUpperCase())) {
            Gson gson = new Gson();
            CurrencyService currencyService = RetrofitUtil.getClient(BASE_URL).create(CurrencyService.class);
            try {
                JsonElement data = currencyService.getRatesByBase(base.toUpperCase()).execute().body();
                JsonElement jsonRates = data.getAsJsonObject().get("rates");
                Rates rates = gson.fromJson(jsonRates, Rates.class);
                return rates;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Sorry, I have not found such currency,\n you can view the available currency typing 'currency'");
            return null;
        }
        return null;
    }

    private Map<String, Object> introspect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null) {
                result.put(pd.getName(), reader.invoke(obj));
            }
        }
        return result;
    }

    public void soutList(List<Expenses> list){
        Iterator iterator = list.iterator();
        String date = "";

        while (iterator.hasNext()) {
            Expenses ex = (Expenses) iterator.next();
            if (date.equals(simpleDateFormat.format(ex.getDate()))) {
            } else {
                date = simpleDateFormat.format(ex.getDate());
                System.out.println(" ");
                System.out.println(simpleDateFormat.format(ex.getDate()));
            }
            System.out.println(ex.getProductName() + " " + ex.getPrice() + " " + ex.getCurrency());
        }
    }
}