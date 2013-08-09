package tv.pps.tj.ppsdemo.session;

/**
 * Created by 赵之韵.
 * Date: 12-2-29
 * Time: 下午6:50
 */
public class StatusCode {
    public static final int SUCCESS = 200;

    // session 通信错误码
    public static final int FAIL = 4000;
    public static final int HTTP_EXCEPTION = 4004;
    public static final int BAD_REQUEST = 4100;
    public static final int NOT_AUTHORIZED = 4200;
    public static final int NOT_FOUND = 4300;
    public static final int DATA_FAILED = 4400;
    public static final int CREATE_FAILED = 4500;
    public static final int GET_FAILED = 4600;
    public static final int UPDATE_FAILED = 4700;
    public static final int DELETE_FAILED = 4800;
    public static final int ILLEGAL_DATE_FAILED = 4900;

    // local 本地错误码
    public static final int LOCAL_FILE_ERROR = 7000;
    public static final int LOCAL_FILE_NOT_FOUND_ERROR = 7001;
    public static final int LOCAL_FILE_UNZIP_ERROR = 7002;
    public static final int LOCAL_XML_PARSE_ERROR = 7010;


    public static boolean isSuccess(int status) {
            return status == 200;
    }
}
