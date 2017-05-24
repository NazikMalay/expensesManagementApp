package service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import data.Rates;
import entity.Expenses;
import repository.ExpensesRepository;

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

    private String[] ratesList = {"AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK",
            "DKK", "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK",
            "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR", "EUR"};
    public List<String> currencyArrayList = Arrays.asList(ratesList);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private ExpensesRepository expensesRepository = new ExpensesRepository();

    public Boolean createExpenses(String date, String price, String currency, String productName) {
        Expenses exp = new Expenses();
        try {
            exp.setDate(simpleDateFormat.parse(date));
            exp.setPrice(Math.round(Double.parseDouble(price) * 100.0) / 100.0);
        } catch (ParseException e) {
            System.out.println("Your date is wrong!" + date);
            return false;

        } catch (NumberFormatException e) {
            System.out.println("price is wrong " + price + " please enter the number");
            return false;
        }
        if (currencyArrayList.contains(currency.toUpperCase())) {
            exp.setCurrency(currency.toUpperCase());
        } else {
            System.out.println("Sorry, I have not found such currency,\n you can view the available currency typing 'currency'");
            return false;
        }
        exp.setProductName(productName);
        expensesRepository.addExpenses(exp);
        return true;
    }

    public void getAllExpenses() {
        List<Expenses> list = expensesRepository.getAllExpenses();
        Collections.sort(list, new ExpensesComparator());
        Iterator<Expenses> iterator = list.iterator();
        String date = "";

        while (iterator.hasNext()) {
            Expenses ex = iterator.next();
            if (date.equals(simpleDateFormat.format(ex.getDate()))) {
            } else {
                date = simpleDateFormat.format(ex.getDate());
                System.out.println(" ");
                System.out.println(simpleDateFormat.format(ex.getDate()));
            }
            System.out.println(ex.getProductName() + " " + ex.getPrice() + " " + ex.getCurrency());
        }
    }

    public Boolean deleteExpensesByDate(String s) {
        try {
            expensesRepository.deleteByDate(simpleDateFormat.parse(s));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void total(String s) throws Exception {
        if (currencyArrayList.contains(s.toUpperCase())) {
            Gson gson = new Gson();
            List<Expenses> list = expensesRepository.getAllExpenses();
            CurrencyService currencyService = RetrofitUtil.getClient(BASE_URL).create(CurrencyService.class);
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
                System.out.println(count + " " + s.toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("some problem with getting Currency from " + BASE_URL);
            }


        } else {
            System.out.println("Sorry, I have not found such currency,\n you can view the available currency typing 'currency'");
        }
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

}