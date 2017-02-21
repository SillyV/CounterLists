package sillyv.com.counterlists.tools;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vasili.Fedotov on 2/21/2017.
 *
 */
public class ValidationToolsTest {
    @Test public void isStringNumericSuccess() throws Exception {
        Assert.assertEquals(true, ValidationTools.getInstance().isStringNumeric("12334"));
    }

    @Test public void isStringNumericFailure() throws Exception {
        Assert.assertEquals(false, ValidationTools.getInstance().isStringNumeric("AAASDASD"));
    }

    @Test public void isStringNumericUnknown() throws Exception {
        Assert.assertEquals(true, ValidationTools.getInstance().isStringNumeric("121232.2323"));
    }
}