package sillyv.com.counterlists.tools;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by vasil on 2/18/2017.
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
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
