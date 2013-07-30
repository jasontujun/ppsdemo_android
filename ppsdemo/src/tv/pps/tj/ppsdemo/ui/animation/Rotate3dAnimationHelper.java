package tv.pps.tj.ppsdemo.ui.animation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-26
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public class Rotate3dAnimationHelper {

    private ViewGroup mContainer;
    private View mFrontView;
    private View mBackView;
    private Rotate3dAnimationListener mListener;

    public Rotate3dAnimationHelper(ViewGroup container, View frontView, View backView) {
        mContainer = container;
        mFrontView = frontView;
        mBackView = backView;

        // Since we are caching large views, we want to keep their cache
        // between each animation
        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

    public void setListener(Rotate3dAnimationListener listener) {
        mListener = listener;
    }

    /**
     * 开始翻转。
     * @param toBack 是否翻找到背面。
     */
    public void rotate3d(boolean toBack) {
        if (toBack)
            applyRotation(toBack, 0, 90);
        else
            applyRotation(toBack, 180, 270);
    }

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param rotateToBack the rotate direction, true means rotate to back
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotation(boolean rotateToBack, float start, float end) {
        // Find the center of the container
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(300);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(rotateToBack));

        mContainer.startAnimation(rotation);
    }

    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final boolean mRotateToBack;

        private DisplayNextView(boolean rotateToBack) {
            mRotateToBack = rotateToBack;
        }

        public void onAnimationStart(Animation animation) {
            if (mListener != null)
                mListener.rotateStart(mRotateToBack);
        }

        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mRotateToBack));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * This class is responsible for swapping the views
     * and start the second half of the animation.
     */
    private final class SwapViews implements Runnable {
        private final boolean mRotateToBack;

        public SwapViews(boolean rotateToBack) {
            mRotateToBack = rotateToBack;
        }

        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;

            if (mRotateToBack) {
                mFrontView.setVisibility(View.GONE);
                mBackView.setVisibility(View.VISIBLE);
                mBackView.requestFocus();

                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                mBackView.setVisibility(View.GONE);
                mFrontView.setVisibility(View.VISIBLE);
                mFrontView.requestFocus();

                rotation = new Rotate3dAnimation(270, 360, centerX, centerY, 310.0f, false);
            }


            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            rotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mListener != null)
                        mListener.rotateFinish(mRotateToBack);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            mContainer.startAnimation(rotation);
        }
    }

    public interface Rotate3dAnimationListener {

        void rotateStart(boolean rotateToBack);

        void rotateFinish(boolean rotateToBack);
    }
}
