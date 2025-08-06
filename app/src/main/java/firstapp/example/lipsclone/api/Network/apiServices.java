package firstapp.example.lipsclone.api.Network;

import firstapp.example.lipsclone.Msgfromclg.DirectorMsg;
import firstapp.example.lipsclone.api.Login.LoginReponse;
import firstapp.example.lipsclone.api.Login.LoginRequest;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.StudentLectureDetailsRequest;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureDetailResponse;
import firstapp.example.lipsclone.api.Models.Library.LibraryResponse;
import firstapp.example.lipsclone.api.Models.Library.StudentLibraryRequest;
import firstapp.example.lipsclone.api.Models.Login.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.Fees.FeeRequest;
import firstapp.example.lipsclone.api.Models.Fees.FeeResponse;
import firstapp.example.lipsclone.api.Models.Fees.FeeTransactionResponse;
import firstapp.example.lipsclone.api.Models.Lecture.LectureRequest;
import firstapp.example.lipsclone.api.Models.Lecture.LectureResponse;
import firstapp.example.lipsclone.api.Models.Messages.MessageResponse;
import firstapp.example.lipsclone.api.Models.Messages.MessageToDirectorRequest;
import firstapp.example.lipsclone.api.Models.Messages.Messages;
import firstapp.example.lipsclone.api.Models.Notice.NoticeRequest ;
import firstapp.example.lipsclone.api.Models.Notice.Notice_Reponse;
import firstapp.example.lipsclone.api.Models.Documents.StudentDocument;
import firstapp.example.lipsclone.api.Models.Downloads.StudentDocumentResponse;
import firstapp.example.lipsclone.api.Models.Downloads.StudentDownloadRequest;
import firstapp.example.lipsclone.api.Models.Downloads.StudentDownloadResponse;
import firstapp.example.lipsclone.api.Models.Fees.FeeTransactionRequest;
import firstapp.example.lipsclone.api.Models.Time_table.StudentTimeTableRequest;
import firstapp.example.lipsclone.api.Models.Time_table.StudentTimeTableResponse;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import firstapp.example.lipsclone.api.Models.attendence.AttendanceRequest;
import firstapp.example.lipsclone.api.Models.attendence.AttendanceResponse;
import firstapp.example.lipsclone.api.Models.attendence.LectureAttendanceRequest;
import firstapp.example.lipsclone.api.Models.attendence.LectureAttendanceResponse;
import firstapp.example.lipsclone.api.Models.attendence.LiveAttendanceRequest;
import firstapp.example.lipsclone.api.Models.attendence.LiveAttendanceResponse;
import firstapp.example.lipsclone.api.Models.attendence.SaveAttendanceRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintCategoryRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintCategoryResponse;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintResponse;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintSubmitRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintSubmitResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    @POST("api/index.php")
    Call<LectureResponse> getLectures(@Body LectureRequest request);
    @POST("api/index.php")
    Call<StudentTimeTableResponse> getStudentTimeTable(@Body StudentTimeTableRequest request);
    @POST("api/index.php")
    Call<LectureDetailResponse> getStudentLectureDetails(@Body StudentLectureDetailsRequest request);
    @POST("index.php")
    Call<ResponseBody> getStudentLectureDetailsRaw(@Body StudentLectureDetailsRequest request);

    @POST("api/index.php")
    Call<AttendanceResponse> getMonthlyAttendance(@Body AttendanceRequest request);
    @POST("api/index.php")
    Call<LiveAttendanceResponse> getLiveAttendance(@Body LiveAttendanceRequest request);

    @POST("api/index.php")
    Call<LiveAttendanceResponse> saveAttendance(@Body SaveAttendanceRequest request);
    @POST("api/index.php")
    Call<LectureAttendanceResponse>  getLectureAttendance(@Body LectureAttendanceRequest request);
    @POST("api/index.php")
    Call<LibraryResponse> getStudentLibrary(@Body StudentLibraryRequest request);
    @POST("api/index.php")
    Call<ComplaintResponse> getComplaints(@Body ComplaintRequest request);
    @POST("api/index.php")

    Call<ComplaintSubmitResponse> submitComplaint(@Body ComplaintSubmitRequest request);

    @POST("api/index.php")
    Call<ComplaintCategoryResponse> getComplaintCategories(@Body ComplaintCategoryRequest request);
    @POST("api/index.php")
        Call<MessageResponse> getStudentMessages(@Body Messages request);

    @POST("api/index.php")
    Call<MessageResponse> sendMessageToDirector(@Body MessageToDirectorRequest request);
//    Call<ResponseBody> sendMessageToDirector(@Body MessageToDirectorRequest request);











    // In apiServices.java
























}
