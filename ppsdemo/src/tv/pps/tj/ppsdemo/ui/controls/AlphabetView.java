package tv.pps.tj.ppsdemo.ui.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.xengine.android.utils.XLog;
import tv.pps.tj.ppsdemo.R;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-26
 * Time: 下午5:31
 * To change this template use File | Settings | File Templates.
 */
public class AlphabetView extends View {
    private static final String TAG = AlphabetView.class.getSimpleName();
    public static final String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    // 自定义属性
    private int mTextSize;
    private int mTextColor;
    private int mSelectedTextColor;
    private int mPressTip;

    private float mCurrentY;// 当前触摸的Y坐标
    private int mChooseIndex;// 当前选择的索引
    private Paint mPaint;// 画笔
    private OnLetterChangedListener mListener;// 监听者

    public AlphabetView(Context context) {
        super(context);
        init(context, null);
    }

    public AlphabetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AlphabetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mCurrentY = -1;
        mChooseIndex = -1;
        mPaint = new Paint();

        // 自定义属性
        mTextSize = 15;
        mTextColor = 0xff000000;
        mSelectedTextColor = 0xff000000;
        mPressTip = -1;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlphabetView);
            mTextSize = a.getDimensionPixelSize(R.styleable.AlphabetView_letterSize, 15);
            mTextColor = a.getColor(R.styleable.AlphabetView_letterColor, 0xff000000);
            mSelectedTextColor = a.getColor(R.styleable.AlphabetView_selectedLetterColor, 0xff000000);
            mPressTip = a.getResourceId(R.styleable.AlphabetView_pressTip, -1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (mPressTip != -1) {
            Bitmap tmp = BitmapFactory.decodeResource(getResources(), mPressTip);
            canvas.drawBitmap(tmp, null,
                    new Rect(0, (int)mCurrentY, width, (int)(mCurrentY + tmp.getHeight())),
                    mPaint);
        }

        int singleHeight = height / ALPHABET.length;
        for (int i = 0; i < ALPHABET.length; i++) {
            mPaint.setColor(mTextColor);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(mTextSize);
            if (i == mChooseIndex) {
                mPaint.setColor(mSelectedTextColor);
                mPaint.setFakeBoldText(true);
            }
            float xPos = width / 2  - mPaint.measureText(ALPHABET[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(ALPHABET[i], xPos, yPos, mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        XLog.d(TAG, "dispatchTouchEvent");
        return true;// 返回true，表明View本身是操作目标，来处理触摸事件
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        XLog.d(TAG, "onTouchEvent");
        mCurrentY = event.getY();
        final int oldIndex = mChooseIndex;
        final int newIndex = (int) (mCurrentY / getHeight() * ALPHABET.length);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                XLog.d(TAG, "onTouchEvent. ACTION_DOWN");
                setPressed(true);
                if (mListener != null)
                    mListener.onTouchDown();

                if (oldIndex != newIndex && 0 <= newIndex && newIndex < ALPHABET.length) {
                    mChooseIndex = newIndex;
                    if (mListener != null)
                        mListener.onTouchLetterChanged(ALPHABET[mChooseIndex]);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                XLog.d(TAG, "onTouchEvent. ACTION_MOVE");
                if (oldIndex != newIndex && 0 <= newIndex && newIndex < ALPHABET.length) {
                    mChooseIndex = newIndex;
                    if (mListener != null)
                        mListener.onTouchLetterChanged(ALPHABET[mChooseIndex]);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                XLog.d(TAG, "onTouchEvent. ACTION_UP");
                setPressed(false);
                mChooseIndex = -1;
                invalidate();

                if (mListener != null)
                    mListener.onTouchUp();
                break;

            case MotionEvent.ACTION_CANCEL:
                XLog.d(TAG, "onTouchEvent. ACTION_CANCEL");
                setPressed(false);
                mChooseIndex = -1;
                invalidate();

                if (mListener != null)
                    mListener.onTouchUp();
                break;
        }

        return super.onTouchEvent(event);
    }


    public void setOnLetterChangedListener(OnLetterChangedListener listener) {
        this.mListener = listener;
    }

    /**
     * 对字母列表控件的监听
     */
    public interface OnLetterChangedListener {
        /**
         * 手指按下字母列表
         */
        void onTouchDown();

        /**
         * 手指离开字母列表
         */
        void onTouchUp();

        /**
         * 触摸的字母发生变化
         * @param s
         */
        void onTouchLetterChanged(String s);
    }
}
