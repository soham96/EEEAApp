package eeea.eeeaapp;

/**
 * Created by Soham on 07-02-2017.
 */

public class announcement_row {

    private String ancmnt;
    private String date;

    public announcement_row(String ancmnt, String date)
    {
        this.ancmnt=ancmnt;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public announcement_row(){}

    public announcement_row(String ancmnt) {
        this.ancmnt = ancmnt;
    }

    public String getAncmnt() {
        return ancmnt;
    }

    public void setAncmnt(String ancmnt) {
        this.ancmnt = ancmnt;
    }


}
