package animate.zero.com.animaterepository;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<PageModel> pages = new ArrayList<>();

    {
        //每次新加入一个页面，在这里加入pages中就好了。
        pages.add(new PageModel(R.layout.heart_fly_up, R.string.heart_fly_up));
        pages.add(new PageModel(R.layout.circle_wave, R.string.circle_wave));
        pages.add(new PageModel(R.layout.turntable, R.string.turntable));
        pages.add(new PageModel(R.layout.thumb_up_j, R.string.thumb_up_j));
        pages.add(new PageModel(R.layout.circle_run_xiaomi, R.string.circle_run));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(pages.get(position).layout);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pages.get(position).title);
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    class PageModel {
        @LayoutRes int layout;
        @StringRes int title;

        PageModel (@LayoutRes int layout, @StringRes int title) {
            this.layout = layout;
            this.title = title;
        }
    }
}
