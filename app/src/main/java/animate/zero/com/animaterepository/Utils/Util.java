package animate.zero.com.animaterepository.Utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by zzj on 17-9-29.
 */

public class Util {
    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
}
