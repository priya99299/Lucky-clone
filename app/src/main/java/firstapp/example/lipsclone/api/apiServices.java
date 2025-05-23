package firstapp.example.lipsclone.api;

import java.util.Map;

import firstapp.example.lipsclone.api.Models.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.DocumentResponse;
import firstapp.example.lipsclone.api.Models.StudentDocumentRequest;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;

import firstapp.example.lipsclone.api.Models.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;

public interface apiServices {
    @POST("api/index.php")
    Call<LoginReponse> loginStudent(@Body LoginRequest request);


    @POST("api/index.php")
    Call<AppConfigResponse> getAppConfig(@Body AppConfigRequest request);

    @POST("api/index.php")
    Call<AppConfigResponse> verifyStudent(@Body StudentVerifyRequest request);
    @POST("api/index.php")
    Call<DocumentResponse> getDocuments(@Body StudentDocumentRequest request);






}
