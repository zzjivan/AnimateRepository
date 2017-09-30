package animate.zero.com.animaterepository.Evalulators;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by zzj on 17-9-30.
 */

public class ThreeStepBesselEvalulator implements TypeEvaluator<PointF> {
    //控制点
    PointF p1;
    PointF p2;

    public ThreeStepBesselEvalulator(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float fraction, PointF start, PointF end) {
        PointF ret = new PointF();
        ret.x = (1-fraction) * (1-fraction) * (1-fraction) * (start.x)
                + 3 * (1-fraction) * (1-fraction) * fraction * (p1.x)
                + 3 * (1-fraction) * fraction * fraction * (p2.x)
                + fraction * fraction * fraction * (end.x);

        ret.y = (1-fraction) * (1-fraction) * (1-fraction) * (start.y)
                + 3 * (1-fraction) * (1-fraction) * fraction * (p1.y)
                + 3 * (1-fraction) * fraction * fraction * (p2.y)
                + fraction * fraction * fraction * (end.y);

        return ret;
    }
}
