package androidx.viewpager2.integration.testapp;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.PageTransformer;

public final class TestMarginPageTransformer implements PageTransformer {
    private final int mMarginPx;

    public TestMarginPageTransformer(@Px int marginPx) {
        checkArgumentNonnegative(marginPx, "Margin must be non-negative");
        this.mMarginPx = marginPx;
    }

    float mPosition = 0;

    public void transformPage(@NonNull View page, float position) {
        ViewPager2 viewPager = this.requireViewPager(page);
        float offset = (float)this.mMarginPx * position;


        if (position  > 0){
            //viewpager 向 右滑，

        }else if (position < 0){
            //viewpager 向 左滑，
        }

        if (Math.abs(position - mPosition) > 0.1){
            mPosition = position;
            Log.e("fyg","position  : " +position );
        }


//        Log.e("fyg","         offset : "+offset);
//        Log.e("fyg","         ");

        if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//            page.setTranslationX(viewPager.isRtl() ? -offset : offset);
            page.setTranslationX(offset);
        } else {
            page.setTranslationY(offset);
        }

    }

    private ViewPager2 requireViewPager(@NonNull View page) {
        ViewParent parent = page.getParent();
        ViewParent parentParent = parent.getParent();
        if (parent instanceof RecyclerView && parentParent instanceof ViewPager2) {
            return (ViewPager2)parentParent;
        } else {
            throw new IllegalStateException("Expected the page view to be managed by a ViewPager2 instance.");
        }
    }


    public static int checkArgumentNonnegative(int value, @Nullable String errorMessage) {
        if (value < 0) {
            throw new IllegalArgumentException(errorMessage);
        } else {
            return value;
        }
    }
}
