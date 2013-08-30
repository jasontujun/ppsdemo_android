package tv.pps.tj.ppsdemo.util;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-29
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class ClickUtil {
    private static final int CLICK_THRESHOLD  = 800;// 点击间隔的临界值
    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        final long currentClickTime = System.currentTimeMillis();
        final long delta = currentClickTime - lastClickTime;
        lastClickTime = currentClickTime;
        if (delta < CLICK_THRESHOLD)
            return true;
        else
            return false;
    }
}
