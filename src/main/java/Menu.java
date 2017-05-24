import org.hibernate.Session;
import service.ExpensesService;
import service.HibernateUtil;

import java.util.Scanner;

/**
 * Created by nazar on 21.05.17.
 */
public class Menu {

    public static void main(String[] args) {

        ExpensesService expensesService = new ExpensesService();
        Session session= (Session) HibernateUtil.getSessionFactory().openSession().close();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String myString = scanner.nextLine();
            String[] list = myString.split(" ");

            switch (list[0]) {
                case "add": {
                    if (list.length == 5) {
                        if(expensesService.createExpenses(list[1],list[2],list[3],list[4])){
                            System.out.println("Succes");
                        }else {
                            System.out.println("Some problem with saving");
                        }
                    } else {
                        System.out.println("Please enter expense by this example: \n'add 2017-04-25 12 USD Jogurt' \nwhere 2017-04-25 — is the date when expense occurred\n" +
                                "12 — is an amount of money spent\n" +
                                "USD — the currency in which expense occurred\n" +
                                "Jogurt — is the name of product purchased");
                    }
                    break;
                }
                case "list": {
                        expensesService.getAllExpenses();
                    break;
                }
                case "clear":{
                    if (list.length == 2){

                        if (expensesService.deleteExpensesByDate(list[1])){
                            System.out.println("Deleted!");
                        }else {
                            System.out.println("Incorrect date\nPlease try again\nyyyy-MM-dd");
                        }
                    }
                    break;
                }
                case "total":{
                    if (list.length == 2){
                        try {
                            expensesService.total(list[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
//                case "clear":{
//                    for(int clear = 0; clear < 100; clear++) {
//                        System.out.println(" ") ;
//                    }
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
//                    break;
//                }
                case "currency":{
                    System.out.println(expensesService.currencyArrayList.toString());
                    break;
                }
                case "exit": {
                    System.exit(0);
                    break;
                }
                default:{
                    System.out.println("unknown command \nPlease try again");
                }
            }


        }

    }
}
