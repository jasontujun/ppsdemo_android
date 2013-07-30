package tv.pps.tj.ppsdemo.data.cache;

import com.xengine.android.data.cache.XBaseAdapterIdDataSource;
import tv.pps.tj.ppsdemo.data.model.ProgramDetail;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-29
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class ProgramDetailSource extends XBaseAdapterIdDataSource<ProgramDetail> {
    @Override
    public String getSourceName() {
        return SourceName.PROGRAM_DETAIL;
    }

    @Override
    public String getId(ProgramDetail programDetail) {
        return programDetail.getId();
    }
}
