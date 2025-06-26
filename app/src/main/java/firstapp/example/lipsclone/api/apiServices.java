package firstapp.example.lipsclone.api;

import firstapp.example.lipsclone.api.Models.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.FeeRequest;
import firstapp.example.lipsclone.api.Models.FeeResponse;
import firstapp.example.lipsclone.api.Models.FeeTransactionResponse;
import firstapp.example.lipsclone.api.Models.NoticeRequest;
import firstapp.example.lipsclone.api.Models.Notice_Reponse;
import firstapp.example.lipsclone.api.Models.StudentDocument;
import firstapp.example.lipsclone.api.Models.StudentDocumentResponse;
import firstapp.example.lipsclone.api.Models.StudentDownloadRequest;
import firstapp.example.lipsclone.api.Models.StudentDownloadResponse;
import firstapp.example.lipsclone.api.Models.FeeTransactionRequest;
import firstapp.example.lipsclone.api.Models.StudentTimeTableRequest;
import firstapp.example.lipsclone.api.Models.StudentTimeTableResponse;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface apiServices {
    @POST("api/index.php")
    Call<LoginReponse> loginStudent(@Body LoginRequest request);


    @POST("api/index.php")
    Call<AppConfigResponse> getAppConfig(@Body AppConfigRequest request);

    @POST("api/index.php")
    Call<AppConfigResponse> verifyStudent(@Body StudentVerifyRequest request);
    @POST("api/index.php")
    Call<StudentDocumentResponse> getDocuments(@Body StudentDocument request);
    @POST("api/index.php")
    Call<Notice_Reponse> getNotices(@Body NoticeRequest request);
    @POST("api/index.php")
    Call<FeeResponse> getFeeDetails(@Body FeeRequest request);
    @POST("api/index.php")
    Call<StudentDownloadResponse> getStudentDownloads(@Body StudentDownloadRequest request);
    @POST("api/index.php")
    Call<FeeTransactionResponse> getFeeTransaction(@Body FeeTransactionRequest request);
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("api/index.php")
    Call<StudentTimeTableResponse> getStudentTimeTable(@Body StudentTimeTableRequest request);

//@POST("api/index.php")
//Call<ResponseBody> getStudentTimeTable(@Body StudentTimeTableRequest request);














}
