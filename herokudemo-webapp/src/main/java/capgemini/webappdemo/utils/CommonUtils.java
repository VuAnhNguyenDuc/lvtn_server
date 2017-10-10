package capgemini.webappdemo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");

    public Date convertStringToDate(String src) throws ParseException {
        return dateFormat.parse(src);
    }

    public String convertDateToString(Date date){
        return dateFormat.format(date);
    }
}
