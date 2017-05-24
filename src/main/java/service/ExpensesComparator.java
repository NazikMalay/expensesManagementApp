package service;

import entity.Expenses;

import java.util.Comparator;

/**
 * Created by nazar on 23.05.17.
 */
public class ExpensesComparator implements Comparator<Expenses>{

    @Override
    public int compare(Expenses o1, Expenses o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
