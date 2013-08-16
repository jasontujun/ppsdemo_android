package tv.pps.tj.ppsdemo.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-16
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class UnScrollGridView extends GridView {

    public UnScrollGridView(Context context) {
        super(context);
    }

    public UnScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
