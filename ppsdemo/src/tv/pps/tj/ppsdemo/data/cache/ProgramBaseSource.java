package tv.pps.tj.ppsdemo.data.cache;

import com.xengine.android.data.cache.XBaseAdapterIdDataSource;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-26
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public class ProgramBaseSource extends XBaseAdapterIdDataSource<ProgramBase> {
    @Override
    public String getSourceName() {
        return SourceName.PROGRAM_BASE;
    }

    @Override
    public String getId(ProgramBase programBase) {
        return programBase.getId();
    }
}
