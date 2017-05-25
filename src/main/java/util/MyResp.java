package util;

import entity.Expenses;

import java.util.List;

/**
 * Created by nazar on 25.05.17.
 */
public class MyResp {

    private boolean state;

    private List<Expenses> list;

    private  String massage;

    public MyResp() {
    }

    public MyResp(boolean state) {
        this.state = state;
    }

    public MyResp(boolean state, List<Expenses> list) {
        this.state = state;
        this.list = list;
    }

    public MyResp(boolean state, String massage) {
        this.state = state;
        this.massage = massage;
    }

    public MyResp(boolean state, List<Expenses> list, String massage) {
        this.state = state;
        this.list = list;
        this.massage = massage;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<Expenses> getList() {
        return list;
    }

    public void setList(List<Expenses> list) {
        this.list = list;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public String toString() {
        return "MyResp{" +
                "state=" + state +
                ", list=" + list +
                ", massage='" + massage + '\'' +
                '}';
    }
}
