import org.hibernate.Session;
import service.ExpensesService;
import util.HibernateUtil;
import util.MyResp;

import java.util.Scanner;

/**
 * Created by nazar on 21.05.17.
 */
public class Menu {

    public static void main(String[] args) {
        ExpensesService expensesService = new ExpensesService();
        Session session = (Session) HibernateUtil.getSessionFactory().openSession().close();
        System.out.println("\nHello!");
        System.out.println("enter 'help' to see menu\n");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String myString = scanner.nextLine();
            String[] list = myString.split(" ");

            switch (list[0]) {
                case "add": {
                    if (list.length == 5) {
                        MyResp resp = expensesService.createExpenses(list[1], list[2], list[3], list[4]);
                        if (resp.getState()) {
                            expensesService.soutList(resp.getList());
                        } else {
                            System.out.println("Some problem with saving\n " + resp.getMassage());
                        }
                    } else {
                        System.out.println("Please enter expense by this example:\n'add 2017-04-25 12 USD Jogurt'\n" +
                                "where 2017-04-25 — is the date when expense occurred\n" +
                                "12 — is an amount of money spent\n" +
                                "USD — the currency in which expense occurred\n" +
                                "Jogurt — is the name of product purchased");
                    }
                    break;
                }
                case "list": {
                    MyResp resp = expensesService.getAllExpenses();
                    expensesService.soutList(resp.getList());
                    break;
                }
                case "clear": {
                    if (list.length == 2) {
                        if (expensesService.deleteExpensesByDate(list[1]).getState()) {
                            expensesService.soutList(expensesService.getAllExpenses().getList());
                        } else {
                            System.out.println("Incorrect date\nPlease try again\nyyyy-MM-dd");
                        }
                    }else {
                        System.out.println("Please enter 'clear' by this example:\n'clear [yyyy-MM-dd]' ");
                    }
                    break;
                }
                case "total": {
                    if (list.length == 2) {
                        try {
                            MyResp resp = expensesService.total(list[1]);
                            if (resp.getState()){
                                System.out.println(resp.getMassage());
                            }else {
                                System.out.println(resp.getMassage());
                            }
                        } catch (Exception e) {
                            System.err.println("some problem with getting Currency from //api.fixer.io/");
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("Please enter 'total' by this example:\n'total [currency]' ");
                    }
                    break;
                }
                case "help": {
                    System.out.println("'add [yyyy-MM-dd price currency productName]' — adds expense entry to the list\n" +
                            "'list' — shows the list of all expenses\n" +
                            "'clear [yyyy-MM-dd]'  — removes all expenses for specified date\n" +
                            "'total [currency]' - calculate the total amount of money spent in specified currency\n" +
                            "'currency' - to see the available currencies" +
                            "'clr' - to clear the console\n" +
                            "'exit' - to quit");
                    break;
                }
                case "clr": {
                    for (int clear = 0; clear < 100; clear++) {
                        System.out.println(" ");
                    }
//                    final String operatingSystem = System.getProperty("os.name");
//                    System.out.println(operatingSystem);
//                    try {
//                        if (operatingSystem .contains("Windows")) {
//                            Runtime.getRuntime().exec("cls");
//                        }
//                        else {
//                            Runtime.getRuntime().exec("clear");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    break;
                }
                case "currency": {
                    System.out.println(expensesService.currencyArrayList.toString());
                    break;
                }
                case "exit": {
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("unknown command \nPlease try again or\n enter 'help' to see menu");
                    break;
                }
            }
        }
    }
}

