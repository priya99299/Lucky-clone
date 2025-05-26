package firstapp.example.lipsclone.api;

import firstapp.example.lipsclone.api.Models.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.DocumentModel;
import firstapp.example.lipsclone.api.Models.StudentDocument;
import firstapp.example.lipsclone.api.Models.StudentDocumentResponse;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiServices {
    @POST("api/index.php")
    Call<LoginReponse> loginStudent(@Body LoginRequest request);


    @POST("api/index.php")
    Call<AppConfigResponse> getAppConfig(@Body AppConfigRequest request);

    @POST("api/index.php")
    Call<AppConfigResponse> verifyStudent(@Body StudentVerifyRequest request);
    @GET("api/index.php")
    Call<StudentDocumentResponse> getDocuments(
            @Query("action") String action,
            @Query("page") String page,
            @Query("s_id") String studentId,
            @Query("session") String session,
            @Query("college") String college
    );








}
