package com.example.xinhuayipin.http;

import com.example.commons.data.RequestValue;
import com.example.xinhuayipin.data.FingerData;
import com.example.xinhuayipin.data.StudentBean;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.book.Book_resurn;
import com.example.xinhuayipin.data.borrow.BorrowBean;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.data.record.RecordBean;
import com.example.xinhuayipin.data.ScanBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec: api接口
 * @version: ${VERSION}.
 * @Update :
 */
public interface ApiService {

    String ACCESS_TOKEN = "init/token";
    String API_LOGIN = "special/checkStudentByUserAndPwd";
    String API_UPLOAD_FINGERPRINT = "special/machineFinger";
    String API_GET_FINGERDATA = "special/getFingerData";
    String API_CHECK_FINGERDATA = "special/checkStudentFingerData";
    String API_CHECK_STUDENT_ID = "special/checkStudentByStudentID";
    String API_GET_BOOKCASE_INFO = "bookcase/getBookCaseInfo";
    String API_BORROW_BOOK = "special/postStudentBookData";
    String API_RETURN_BOOK = "special/postStudentReturnData";
    String API_LEND_INFO = "special/lendInfo";
    String API_GET_BOOK_BY_ISBN = "special/bookToBookcase";
    String APT_RETURN = "special/ReturnDoorCheck";
    String API_BIND = "bookcase/bindBook";
    String API_CLEAR = "manage/bookclear";

    /**
     * 获取Token
     * @param access_token 访问Token【Token附加参数】
     * @param type 数值，硬件接口传2
     * @param content token申请用途内容描述
     * @return token
     */
    @FormUrlEncoded
    @POST(ACCESS_TOKEN)
    Observable<TokenBean> token(@Field("access_token") String access_token, @Field("type") String type, @Field("content") String content);

    /**
     * 通过密码进行登录
     * @param access_token 访问Token【Token附加参数】
     * @param machid 机器编号
     * @param username 学生班级内学号
     * @param password 学生登录密码
     * @return StudentBean
     */
    @FormUrlEncoded
    @POST(API_LOGIN)
    Observable<StudentBean> login(@Field("access_token") String access_token, @Field("machid") String machid, @Field("username") String username, @Field("password") String password);

    /**
     * 指纹采集录入
     * @param access_token 访问Token【Token附加参数】
     * @param fingerdata 指纹数据
     * @param finger_pic 指纹图片
     * @param student_id 学生唯一id
     * @param school_id 学校唯一id
     * @return RequestValue
     */
    @Multipart
    @POST(API_UPLOAD_FINGERPRINT)
    Observable<RequestValue> uploadFinger(@Part("access_token") RequestBody access_token, @Part("fingerdata") RequestBody fingerdata, @Part MultipartBody.Part finger_pic,
                                          @Part("student_id") RequestBody student_id, @Part("school_id") RequestBody school_id);

    /**
     * 获取漂流柜对应指纹数据
     * @param access_token 访问Token【Token附加参数】
     * @param machid 机器编号
     * @param operate_type 操作类型，0：漂流柜对应全部数据获取；1：获取指定指纹id（多个使用英文逗号分隔）的指纹数据
     * @param finger_ids 党operate_type为1时，传入，对应指纹id（需获取多个时传入指纹id）
     * @return FingerData
     */
    @FormUrlEncoded
    @POST(API_GET_FINGERDATA)
    Observable<FingerData> getFingerData(@Field("access_token") String access_token, @Field("machid") String machid, @Field("operate_type") String operate_type, @Field("finger_ids") String finger_ids);

    /**
     * 学生指纹验证及借书箱数据获取
     * @param access_token 访问Token【Token附加参数】
     * @param machid 机器编号
     * @param finger_id 指纹ID
     * @return FetchBean
     */
    @FormUrlEncoded
    @POST(API_CHECK_FINGERDATA)
    Observable<FetchBean> checkStudentFingerData(@Field("access_token") String access_token, @Field("machid") String machid, @Field("finger_id") String finger_id);

    /**
     * 学生验证及借书箱数据获取（学生id）
     * @param access_token 访问Token【Token附加参数】
     * @param machid 机器编号
     * @param student_id 学生ID
     * @return FetchBean
     */
    @FormUrlEncoded
    @POST(API_CHECK_STUDENT_ID)
    Observable<FetchBean> checkStudentByStudentID(@Field("access_token") String access_token, @Field("machid") String machid, @Field("student_id") String student_id);

    /**
     * 获取柜子内的书籍列表
     * @param access_token 访问Token【Token附加参数】
     * @param bookcase_no 漂流柜编号
     * @return BookBean
     */
    @FormUrlEncoded
    @POST(API_GET_BOOKCASE_INFO)
    Observable<BookBean> getBookCaseInfo(@Field("access_token") String access_token, @Field("bookcase_no") String bookcase_no);

    /**
     * 图书借出(漂流柜)
     * @param access_token 访问Token【Token附加参数】
     * @param machid 机器编号
     * @param doorid 柜门编号
     * @param student_id 学生id
     * @return BorrowBean
     */
    @FormUrlEncoded
    @POST(API_BORROW_BOOK)
    Observable<BorrowBean> borrow(@Field("access_token") String access_token, @Field("machid") String machid, @Field("doorid") String doorid, @Field("student_id") String student_id);

    /**
     * 图书归还(漂流柜)
     * @param access_token 访问Token【Token附加参数】
     * @param machid 柜子编号
     * @param book_id 副本id
     * @return
     */
    @FormUrlEncoded
    @POST(API_RETURN_BOOK)
    Observable<RequestValue> returnBack(@Field("access_token") String access_token, @Field("machid") String machid, @Field("book_id") String book_id);

    /**
     * 学生借阅记录获取
     * @param access_token 访问Token【Token附加参数】
     * @param student_id 学生id
     * @param type 1:当前借阅；2:历史借阅；3：预约历史；4：当前预约
     * @return RecordBean
     */
    @FormUrlEncoded
    @POST(API_LEND_INFO)
    Observable<RecordBean> lendInfo(@Field("access_token") String access_token, @Field("student_id") String student_id, @Field("type") String type);

    /**
     * 判断图书是否属于此柜
     * @param access_token 访问Token【Token附加参数】
     * @param isbn 图书的isbn
     * @param machid 书柜的编号
     * @param student_id 学生的id
     * @return ScanBean
     */
    @FormUrlEncoded
    @POST(API_GET_BOOK_BY_ISBN)
    Observable<ScanBean> getBookByIsbn(@Field("access_token") String access_token, @Field("isbn") String isbn, @Field("machid") String machid, @Field("student_id") String student_id);


    //归还图书
    @FormUrlEncoded
    @POST(API_RETURN_BOOK)
    Observable<Book_resurn> getReturn(@Field("access_token") String access_token,@Field("machid") String machid,@Field("book_id") String book_id);


    //图书上架
    @FormUrlEncoded
    @POST(API_BIND)
    Observable<TokenBean.Data> getBind(@Field("access_token") String access_token,@Field("user_token") String machid,@Field("book_id") String book_id,@Field("bookcase_id") String bookcase_id);

    //图书下架
    @FormUrlEncoded
    @POST(API_CLEAR)
    Observable<TokenBean.Data> getBookClear(@Field("access_token") String access_token,@Field("bookcase_id") String machid,@Field("grid_ids") String book_id,@Field("user_token") String bookcase_id);

}
