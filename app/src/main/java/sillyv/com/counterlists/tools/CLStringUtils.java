package sillyv.com.counterlists.tools;

import android.content.Context;
import android.support.annotation.NonNull;

import org.joda.time.Period;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sillyv.com.counterlists.R;

/**
 * Created by Vasili.Fedotov on 3/11/2017.
 *
 */

public class CLStringUtils {


    public static String getDifferenceBetweenDates(Date oldDate, Date nowDate, boolean ago) {
        Period period = new Period(oldDate.getTime(), nowDate.getTime());
        if (period.getHours() > 18) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            return formatter.format(oldDate);
        } else {
            String hours = getTimeUnits(period.getHours(), "H", false);
            String minutes = getTimeUnits(period.getMinutes(), "M", !hours.equals(""));
            String seconds = getTimeUnits(period.getSeconds(), "S", !(hours+minutes).equals(""));
            return hours + minutes + seconds + getAgo(ago);
        }
    }

    @NonNull private static String getTimeUnits(int period, String s, boolean extend) {
        String zero = "";
        if (period > 0 || extend) {
            if (extend && period < 10) {
                zero = "0";
            }
            return zero + period + s + " ";
        }
        return "";
    }

    private static String getAgo(boolean ago) {
        if (ago) {
            return " ago.";
        }
        return "";
    }

    public static String getDeleteMessage(Context context, String name, int count) {
        String startOfString = context.getString(R.string.are_you_sure_you_want_to_delete) + " ";
        if (count == 1) {
            return startOfString + context.getString(R.string.the_counter) + name + "'";
        } else {
            return startOfString + count + " " + context.getString(R.string.counters);
        }
    }

    public static String getResetMessage(Context context, String name, int count, boolean selectionMode) {
        String startOfString = context.getString(R.string.are_you_sure_you_want_to_reset) + " ";
        if (count == 1) {
            return startOfString + context.getString(R.string.the_counter) + name + "'";
        } else if (!selectionMode) {
            return startOfString + context.getString(R.string.all_counters);
        } else {
            return startOfString + count + " " + context.getString(R.string.counters);
        }
    }
}
