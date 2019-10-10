package com.contacts.memo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtility {

    public static String getCurrentTime(){

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy");
            return simpleDateFormat.format(new Date());

        }catch (Exception x){
            return null;
        }
    }

    public static String getMonthFromNumber(String monthNumber){

        switch (monthNumber){
            case "01":
               return "jan";
            case "02":
                return "feb";
                case "03":
              return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "july";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
                default:
                    return "error";
        }
    }
}
