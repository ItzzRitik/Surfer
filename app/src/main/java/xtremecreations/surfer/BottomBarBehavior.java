package xtremecreations.surfer;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
public class BottomBarBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private float defaultDependencyTop = -1f;
    public BottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {return dependency instanceof AppBarLayout;}
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (defaultDependencyTop == -1) {defaultDependencyTop = dependency.getTop();}
        child.setTranslationY((-dependency.getTop() + defaultDependencyTop)*75/70);
        return true;
    }

}