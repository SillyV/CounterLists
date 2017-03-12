package sillyv.com.counterlists.tools;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by Vasili on 2/18/2017.
 *
 */

public class ValidationTools {
    private static final ValidationTools ourInstance = new ValidationTools();

    public static ValidationTools getInstance() {
        return ourInstance;
    }

    private ValidationTools() {
    }


    public boolean isStringNumeric(String str) {
        try {
            NumberFormat.getInstance().parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
