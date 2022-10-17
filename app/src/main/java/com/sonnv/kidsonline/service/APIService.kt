package com.sonnv.kidsonline.service

import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.model.response.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

open interface APIService {
    @FormUrlEncoded
    @POST("api-account/login")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<LoginRSP>

    @FormUrlEncoded
    @POST("api-account/activate")
    fun getActivate(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("date") date: String = ""
    ): Call<ActivateRSP>

    // change student info
    @FormUrlEncoded
    @POST("api-account/change-info-student")
    fun changeStudentInfo(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("name") name: String,
        @Field("nickname") nickName: String,
        @Field("birthday") birthday: String, // format YYYY-MM-dd
        @Field("gender") gender: Int,
        @Field("image") image: String,
    ): Call<ChangeUserInfoRSP>

    // change Parent info
    @FormUrlEncoded
    @POST("api-account/change-info-parent") // đoạn này confirn hình như api sai
    fun changeParentInfo(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("parent_name") name: String,
        @Field("address") nickName: String,
        @Field("parent_birthday") birthday: String,
        @Field("email") email: String,
        @Field("tel") phone: String,
    ): Call<ChangeUserInfoRSP>

    // change password
    @FormUrlEncoded
    @POST("api-account/change-password")
    fun changePassword(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("password") name: String,
        @Field("password_new") nickName: String,
        @Field("password_new_retype") birthday: String
    ): Call<BaseResponse>

    // health
    @FormUrlEncoded
    @POST("api-account/health")
    fun getHealthInfo(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("year") year: Int
    ): Call<HealthRSP>

    @FormUrlEncoded
    @POST("api-message/get-list-teacher")
    fun getTeacherList(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<ConversationRSP>

    @FormUrlEncoded
    @POST("api-message/history")
    fun getChatHistory(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("teacher_id") teacherId: Int
    ): Call<ConversationDetailRSP>

    @FormUrlEncoded
    @POST("api-message/send")
    fun sendMessage(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("message") message: String,
        @Field("teacher_id") teacherId: Int
    ): Call<ConversationDetailRSP>

    @FormUrlEncoded
    @POST("api-absence/send")
    fun sendAbsence(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("note") note: String,
        @Field("dates") dates: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("api-absence/history")
    fun historyAbsence(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
    ): Call<AbsenceHistoryRSP>

    // Thêm người đón
    @FormUrlEncoded
    @POST("api-homie/create")
    fun createHomie(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("note") note: String,
        @Field("name") name: String,
        @Field("tel") telephone: String,
        @Field("image") image: String,
        @Field("relationship") relationship: String,
    ): Call<CreateHomieRSP>

    // Thêm người đón
    @FormUrlEncoded
    @POST("api-homie/edit-homie")
    fun updateHomie(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("note") note: String,
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("tel") telephone: String,
        @Field("image") image: String,
        @Field("relationship") relationship: String,
    ): Call<CreateHomieRSP>

    // Danh sách người đón
    @FormUrlEncoded
    @POST("api-homie/get-list")
    fun getListHomie(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<HomieListRSP>

    // Xóa người đón
    @FormUrlEncoded
    @POST("api-homie/delete-homie")
    fun deleteHomie(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("note") note: String,
        @Field("id") id: String,
    ): Call<BaseResponse>

    // đón về
    @FormUrlEncoded
    @POST("api-homie/get-list-history")
    fun getHistoryHomie(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
    ): Call<DonveHistoryRSP>

    // thêm phiếu đón về
    @FormUrlEncoded
    @POST("api-homie/send-pickup")
    fun sendPickUp(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("note") note: String,
        @Field("type") type: Int,
        @Field("date") date: String,
        @Field("hour") hour: String,
        @Field("homie_id") homieId: String,
    ): Call<DonvePickupRSP>

    // Dặn thuốc
    @FormUrlEncoded
    @POST("api-medicine/history")
    fun historyMedicine(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<MedicineRSP>

    @FormUrlEncoded
    @POST("api-medicine/send")
    fun sendMedicine(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("note") note: String,
        @Field("date_start") startDate: String,
        @Field("date_end") endDate: String,
        @Field("images") images: String,
        @Field("detail") detail: String,
    ): Call<MedicineRSP>

    // Xóa phiếu gửi thuốc
    @FormUrlEncoded
    @POST("api-medicine/delete")
    fun deleteMedicine(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("note") note: String,
        @Field("id") id: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("/api-notify/get-list")
    fun getListNotify(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<NotificationRSP>

    //seen all notify
    @FormUrlEncoded
    @POST("/api-notify/seem-all")
    fun seenAllNotify(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("/api-news/get-list")
    fun getListNews(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long
    ): Call<NewsRSP>

    @FormUrlEncoded
    @POST("/api-news/detail")
    fun getNewDetail(
        @Field("token") token: String,
        @Field("checksum") checksum: String,
        @Field("time") time: Long,
        @Field("id") id: Int
    ): Call<NewDetailRSP>
}