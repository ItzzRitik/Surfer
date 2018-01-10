package xtremecreations.surfer;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class NestedWebView extends WebView implements NestedScrollingChild {
    private int mLastY;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private int mNestedOffsetY;
    private NestedScrollingChildHelper mChildHelper;

    public NestedWebView(Context context) {
        this(context, null);
        getSettings().setSupportZoom(false);
    }

    public NestedWebView(Context context, AttributeSet attrs) {this(context, attrs, android.R.attr.webViewStyle);}
    public NestedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);mChildHelper = new NestedScrollingChildHelper(this);setNestedScrollingEnabled(true);}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean returnValue = false;

        MotionEvent event = MotionEvent.obtain(ev);
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {mNestedOffsetY = 0;}
        int eventY = (int) event.getY();
        event.offsetLocation(0, mNestedOffsetY);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = mLastY - eventY;
                setLongClickable(false);
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset))
                {
                    setScrollEnabled(false);
                    deltaY -= mScrollConsumed[1];
                    mLastY = eventY - mScrollOffset[1];
                    mNestedOffsetY += mScrollOffset[1];
                    event.offsetLocation(0, -mScrollOffset[1]);
                    //ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
                    //p.bottomMargin=mNestedOffsetY;
                    //this.setLayoutParams(p);
                }
                else{setScrollEnabled(true);}
                returnValue = super.onTouchEvent(event);

                if (dispatchNestedScroll(0, mScrollOffset[1], 0, deltaY, mScrollOffset))
                {
                    event.offsetLocation(0, mScrollOffset[1]);
                    mNestedOffsetY += mScrollOffset[1];
                    mLastY -= mScrollOffset[1];
                }
                else{setScrollEnabled(true);}
                break;
            case MotionEvent.ACTION_DOWN:
                returnValue = super.onTouchEvent(event);mLastY = eventY;startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                returnValue = super.onTouchEvent(event);setLongClickable(true);setScrollEnabled(true);stopNestedScroll();break;
        }
        return returnValue;
    }
    public int getScrollHeight() {return(computeVerticalScrollRange());}
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {mChildHelper.setNestedScrollingEnabled(enabled);}
    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }
    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }
    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }
    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow)
    {return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);}
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow)
    {return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);}
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed)
    {return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);}
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY){return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);}


    private OnScrollChangedCallback mOnScrollChangedCallback;
    public OnScrollChangedCallback getOnScrollChangedCallback() {return mOnScrollChangedCallback;}
    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {mOnScrollChangedCallback = onScrollChangedCallback;}
    public static interface OnScrollChangedCallback {public void onScroll(int l, int t);}
    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt)
    {super.onScrollChanged(l, t, oldl, oldt);if(mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);}

    private boolean scrollEnabled = true;

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        if (scrollEnabled) {
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                    scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        }
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (scrollEnabled) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (scrollEnabled) {
            super.computeScroll();
        }
    }

    public boolean checkIfScrollable() {
        return computeVerticalScrollRange() > getHeight()
                || computeHorizontalScrollRange() > getWidth();
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.scrollEnabled = scrollEnabled;
    }

    public boolean isScrollEnabled() {
        return scrollEnabled;
    }
}