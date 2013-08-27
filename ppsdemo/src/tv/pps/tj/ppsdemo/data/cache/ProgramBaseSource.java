package tv.pps.tj.ppsdemo.data.cache;

import android.text.TextUtils;
import com.xengine.android.data.cache.XBaseFilteredAdapterIdSource;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-26
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public class ProgramBaseSource extends XBaseFilteredAdapterIdSource<ProgramBase> {

    private Map<String, Integer> alphaIndexer;//存放拼音首字母和与之对应的位置

    @Override
    public String getSourceName() {
        return SourceName.PROGRAM_BASE;
    }

    @Override
    public String getId(ProgramBase programBase) {
        return programBase.getId();
    }

    /**
     * 初始化“字母-位置索引”映射表。
     * 注意：在调用此方法前，需要对数据进行字母排序
     * @see #sort(java.util.Comparator)
     */
    public void initAlphaIndexer(String[] alphabet) {
        if (alphaIndexer == null)
            alphaIndexer = new HashMap<String, Integer>();
        alphaIndexer.clear();

        String lastLetter = null;
        for (int i = 0; i < size(); i++) {
            //当前汉语拼音首字母
            String curLetter = get(i).getFirstLetter().toUpperCase();
            if (!TextUtils.isEmpty(curLetter) && !curLetter.equals(lastLetter)) {
                alphaIndexer.put(curLetter, i);
                lastLetter = curLetter;
            }
        }
    }

    /**
     * 获取“字母-位置索引”映射表。
     * 注意：在调用此方法前，需要初始化映射表，否则会返回null
     * @see #initAlphaIndexer(String[])
     * @return
     */
    public Map<String, Integer> getAlphaIndexer() {
        return alphaIndexer;
    }
}
