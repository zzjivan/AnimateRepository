package animate.zero.com.animaterepository;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zzj on 17-9-29.
 */

public class PageFragment extends Fragment{
    @LayoutRes int layout;

    public static PageFragment newInstance(@LayoutRes int layout) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("layout", layout);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            layout = arguments.getInt("layout");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layout, container, false);
        return view;
    }
}
