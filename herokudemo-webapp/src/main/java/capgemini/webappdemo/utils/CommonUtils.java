package capgemini.webappdemo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
    private DateFormat dateWithSec = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    public Date convertStringToDate(String src) throws ParseException {
        return dateFormat.parse(src);
    }

    public String convertDateToString(Date date){
        return dateFormat.format(date);
    }

    public Date convertStringToDateSec(String src) throws ParseException {
        return dateWithSec.parse(src);
    }

    public String convertDateToStringSec(Date date){
        return dateWithSec.format(date);
    }

    public long getSeconds(Date d1,Date d2){
        return (d2.getTime() - d1.getTime())/1000;
    }

    public int[] convertStringToArray(String arr){
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            };
        }
        return results;
    }
}
