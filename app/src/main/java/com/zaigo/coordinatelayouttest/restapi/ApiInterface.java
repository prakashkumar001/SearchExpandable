package com.zaigo.coordinatelayouttest.restapi;




import com.zaigo.coordinatelayouttest.model.ChildStoreList;
import com.zaigo.coordinatelayouttest.model.CouponCodeModel;
import com.zaigo.coordinatelayouttest.model.ForgetPasswordModel;
import com.zaigo.coordinatelayouttest.model.LoginModel;
import com.zaigo.coordinatelayouttest.model.OrderDeatilsModel;
import com.zaigo.coordinatelayouttest.model.OrderHistoryModel;
import com.zaigo.coordinatelayouttest.model.PlaceOrderModel;
import com.zaigo.coordinatelayouttest.model.ProfileInformationModel;
import com.zaigo.coordinatelayouttest.model.RegisterModel;
import com.zaigo.coordinatelayouttest.model.StoreCategoryModel;
import com.zaigo.coordinatelayouttest.model.StoreChildModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {


   /* {
        "status_code": "200",
            "message": "Order Placed Successfully",
            "result": {
        "order_id": 73
    }
    }


    [{"id":"13","order_item_name":"Chicken Breast","qty":"6","total":"127.5"},{"id":"14","order_item_name":"Chicken 65","qty":"2","total":"50.0"}]


*/
    @FormUrlEncoded
    @POST("registration.php")
    Call<RegisterModel> getRegister(@Field("user_name") String user_name,@Field("_billing_first_name") String _billing_first_name ,@Field("mobile_no") String mobile_no ,@Field("user_pass") String user_pass, @Field("user_email") String user_email, @Field("user_country") String user_country, @Field("user_state") String user_state, @Field("nw_lat") String nw_lat, @Field("nw_long") String nw_long, @Field("nw_area") String nw_area, @Field("nw_street") String nw_street, @Field("nw_build") String nw_build);


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> getLogin(@Field("user_login") String user_login, @Field("user_password") String user_password, @Field("device_id") String device_id, @Field("device_type") String device_type);
    //user_email
  //  forgot_password.php

    @FormUrlEncoded
    @POST("forgot_password.php")
    Call<ForgetPasswordModel> getForgetPassword(@Field("user_email") String user_email);

    @FormUrlEncoded
    @POST("userbilling_history.php")
    Call<ProfileInformationModel> getProfileData(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("profile_update.php")
    Call<ForgetPasswordModel> getProfileUpdate(@Field("user_id") String user_id,@Field("user_mobile") String user_mobile,@Field("user_country") String user_country,@Field("user_state") String user_state,@Field("nw_area") String nw_area,@Field("nw_street") String nw_street,@Field("nw_build") String nw_build,@Field("nw_lat") String nw_lat,@Field("nw_long") String nw_long);

    @FormUrlEncoded
    @POST("order_history.php")
    Call<OrderHistoryModel> getOrderHistoryData(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("order_details.php")
    Call<OrderDeatilsModel> getOrderHistoryDeatilsData(@Field("order_id") String order_id);


    @FormUrlEncoded
    @POST("coupon_code.php")
    Call<CouponCodeModel> getCuponCode(@Field("user_id") String user_id,@Field("coup_code") String coup_code);


    @POST("store_category.php")
    Call<StoreCategoryModel> getStoreCategory();
    @FormUrlEncoded
    @POST("store_product.php")
    Call<StoreChildModel> getStoreChildList(@Field("cat_id") String cat_id);

// addtocart

 @FormUrlEncoded
 @POST("addtocart.php")
 Call<PlaceOrderModel> getPlaceOrder(@Field("user_id") String user_id,@Field("json_string") String json_string,@Field("coup_code") String coup_code,@Field("coup_act_amt") String coup_act_amt,@Field("_billing_first_name") String _billing_first_name,@Field("_billing_phone") String _billing_phone,@Field("nw_lat") String nw_lat,@Field("nw_long") String nw_long,@Field("_billing_state") String _billing_state,@Field("_billing_country") String _billing_country,@Field("_billing_email") String _billing_email,@Field("_order_total") String _order_total,@Field("shipping_charge") String shipping_charge,@Field("nw_area") String nw_area,@Field("nw_street") String nw_street,@Field("nw_build") String nw_build,@Field("comment_content") String comment_content,@Field("ship_opt_shop") String ship_opt_shop,@Field("ship_opt_date") String ship_opt_date,@Field("ship_opt_time") String ship_opt_time,@Field("ship_opt") String ship_opt,@Field("payment_method") String payment_method);
     /* user_id: Current User Id
                    user_mobile : Mobile Number
                    user_country : Country
                    user_state : State
                    nw_area : Area
                    nw_lat : Latitude
                    nw_long : Longitude
                    nw_street : Street
                    nw_build : Building
*/
    /*

    @FormUrlEncoded
    @POST("user_registration")
    Call<LoginModel> getLogin(@Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("employee")
    Call<AttendanceSubmitModel> getSubmitDeatils(@Field("id") String id, @Field("company_id") String company_id);


    @Multipart
    @POST("employee_enroll")
    Call<EnrollMaodel> getSubmitEnroll(@Part MultipartBody.Part file, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("dob") RequestBody dob, @Part("aadhar_number") RequestBody aadhar_number, @Part("driving_licence") RequestBody driving_licence, @Part("mobile_number") RequestBody mobile_number, @Part("department_id") RequestBody department_id, @Part("joining_date") RequestBody joining_date, @Part("education") RequestBody education, @Part("location_id") RequestBody location_id, @Part("city") RequestBody city, @Part("address") RequestBody address, @Part("pin") RequestBody pin, @Part("state") RequestBody state, @Part("fingerprint1") RequestBody fingerprint1, @Part("fingerprint2") RequestBody fingerprint2, @Part("fingerprint1_image") RequestBody fingerprint1_image, @Part("fingerprint2_image") RequestBody fingerprint2_image, @Part("modelnumber") RequestBody modelnumber, @Part("company_id") RequestBody company_id);




    @Multipart
    @POST("employee_enroll")
    Call<EnrollMaodel> getSubmitEnrollWithoutImage(@Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("dob") RequestBody dob, @Part("aadhar_number") RequestBody aadhar_number, @Part("driving_licence") RequestBody driving_licence, @Part("mobile_number") RequestBody mobile_number, @Part("department_id") RequestBody department_id, @Part("joining_date") RequestBody joining_date, @Part("education") RequestBody education, @Part("location_id") RequestBody location_id, @Part("city") RequestBody city, @Part("address") RequestBody address, @Part("pin") RequestBody pin, @Part("state") RequestBody state, @Part("nationality") RequestBody nationality, @Part("fingerprint1") RequestBody fingerprint1, @Part("fingerprint2") RequestBody fingerprint2, @Part("fingerprint1_image") RequestBody fingerprint1_image, @Part("fingerprint2_image") RequestBody fingerprint2_image);




    @POST("site")
    Call<List<EnrollDropDown>> getDropDown();

    @FormUrlEncoded
    @POST("vehicletypes")
    Call<List<VehicleDailyStatusDropDownModel>> getVehicleStatusDropDown(@Field("id") String id);

    @FormUrlEncoded
    @POST("vehicledailystatus")
    Call<VehicleCurrentStatusModel> getVehicleCurrentStatus(@Field("company_id") String company_id, @Field("siteId") String siteId);


    @Multipart
    @POST("employeedocument")
    Call<DocumentUploadModel> getDocumentUploads(@Part MultipartBody.Part file, @Part("empdoc_id") RequestBody empdoc_id, @Part("emp_id") RequestBody emp_id);


    @Multipart
    @POST("documentlogs")
    Call<AddNewModel> getUploadLogsAddNew(@Part MultipartBody.Part file, @Part("log_file_id") RequestBody log_file_id, @Part("description") RequestBody description);




    @POST("logtypes")
    Call<LogTypeModel> getLogTypes();


    @FormUrlEncoded
    @POST("forgetpwd")
    Call<ForgetPasswordModel> getForgetPassword(@Field("email") String email);


    @Multipart
    @POST("logs")
    Call<UploadLogsModel> getUploadsLogs(@Part MultipartBody.Part file, @Part("logtype_id") RequestBody logtype_id, @Part("location_id") RequestBody location_id, @Part("month") RequestBody month, @Part("year") RequestBody year, @Part("company_id") RequestBody company_id);




    @Multipart
    @POST("logs")
    Call<UploadLogsModel> getUploadsLogsWithOutImage(@Part MultipartBody.Part file, @Part("logtype_id") RequestBody logtype_id, @Part("location_id") RequestBody location_id, @Part("month") RequestBody month, @Part("year") RequestBody year);




    @FormUrlEncoded
    @POST("remarks")
    Call<EmpolyeeRemarksModel> getRemarksUpdate(@Field("remarks_post_user_id") String remarks_post_user_id, @Field("user_id") String user_id, @Field("remarks") String remarks);

    @FormUrlEncoded
    @POST("empattendance_in")
    Call<EmployeeInTimeModel> getEmployeeInTime(@Field("location_id") String location_id, @Field("user_id") String user_id, @Field("attendance_post_userid") String attendance_post_userid);



    @FormUrlEncoded
    @POST("empattendance_out")
    Call<OutTimeModel> getEmployeeOutTime(@Field("location_id") String location_id, @Field("user_id") String user_id, @Field("attendance_post_userid") String attendance_post_userid);


    @FormUrlEncoded
    @POST("dailystatusstore")
    Call<OutTimeModel> getVehicleDailyStatus(@Field("vehicle_id") String vehicle_id, @Field("driver_id") String driver_id, @Field("site_id") String site_id, @Field("vehicletype_id") String vehicletype_id, @Field("shift") String shift, @Field("dailystatus_date") String dailystatus_date, @Field("re_kmstart") String re_kmstart, @Field("re_kmclose") String re_kmclose, @Field("re_kmtotal") String re_kmtotal, @Field("tkmstart") String tkmstart, @Field("tkmclose") String tkmclose, @Field("tkmtotal") String tkmtotal, @Field("bpkmstart") String bpkmstart, @Field("bpkmclose") String bpkmclose, @Field("bpkmtotal") String bpkmtotal, @Field("cpkmstart") String cpkmstart, @Field("cpkmclose") String cpkmclose, @Field("cpkmtotal") String cpkmtotal, @Field("bkmstart") String bkmstart, @Field("bkmclose") String bkmclose, @Field("bkmtotal") String bkmtotal, @Field("bphrs_start") String bphrs_start, @Field("bphrs_close") String bphrs_close, @Field("bphrs_total") String bphrs_total, @Field("bophrs_start") String bophrs_start, @Field("bophrs_close") String bophrs_close, @Field("bophrs_total") String bophrs_total, @Field("aPHhrs_start") String aPHhrs_start, @Field("aPHhrs_close") String aPHhrs_close, @Field("aPHhrs_total") String aPHhrs_total, @Field("PH3hrs_start") String PH3hrs_start, @Field("PH3hrs_close") String PH3hrs_close, @Field("PH3hrs_total") String PH3hrs_total, @Field("batchplant_type") String batchplant_type, @Field("pto_start") String pto_start, @Field("pto_close") String pto_close, @Field("pto_total") String pto_total, @Field("ckmstart") String ckmstart, @Field("ckmclose") String ckmclose, @Field("ckmtotal") String ckmtotal, @Field("lkmstart") String lkmstart, @Field("lkmclose") String lkmclose, @Field("lkmtotal") String lkmtotal, @Field("diesel_ltrs") String diesel_ltrs);

    @FormUrlEncoded
    @POST("runningdetails")
    Call<OutTimeModel> getVehicleRunningStatus(@Field("site_id") String site_id, @Field("date") String date, @Field("shift") String shift, @Field("vehicletype_id") String vehicletype_id, @Field("vehicleworking") String vehicleworking, @Field("vehiclebreakdown") String vehiclebreakdown);



    @FormUrlEncoded
    @POST("company")
    Call<List<ListOfCompany>> getListOfCompany(@Field("role_id") String role_id);

    @FormUrlEncoded
    @POST("docsdelete")
    Call<OutTimeModel> getDocumentDelete(@Field("id") String id);



    @FormUrlEncoded
    @POST("employeeattendance")
    Call<ViewAttendanceModel> getEmployeeViewAttendance(@Field("user_id") String user_id, @Field("month") String month, @Field("year") String year);

    @FormUrlEncoded
    @POST("password")
    Call<PassWordUpdateModel> getEmployeePasswordUpdate(@Field("id") String id, @Field("password") String password, @Field("conpassword") String conpassword, @Field("currentpassword") String currentpassword);
*/


    /*@FormUrlEncoded
    @POST("register")
    Call<RegisterModel> getRegister(@Field("email") String email, @Field("password") String password, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("hcp_registration_number") String hcp_registration_number, @Field("hospital_or_practice") String hospital_or_practice, @Field("marketing") String marketing);




    @GET("options/get")
    Call<CategoryResponse> getCategorySample(@HeaderMap Map<String, String> headers);


    @GET("getHospitals")
    Call<List<HospitalModel>> getHospitals(@HeaderMap Map<String, String> headers);



    @POST("orders/orders/")
    Call<OrdersResponse> getOrderSample(@HeaderMap Map<String, String> headers, @Body OrdersGivenResponse body);

    @GET("resources/resources/")
    Call<List<OrdersLiteratureResponse>> getOrderLiterature(@HeaderMap Map<String, String> headers);

    @GET("user/orders/")
    Call<YourOrders> getYourOrder(@HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("user/update")
    Call<EditDetailsModel> getEditDeatils(@HeaderMap Map<String, String> headers, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Field("password") String password);
*/
    /*@GET("products/products")
    Call<List<ProductResponse>> getProducts(@HeaderMap Map<String, String> headers);*/
    /*@POST("htc/product_search")
    Call<List<TestExample>> getUserProduct_Search(@Query("qry_string") String qry_string);*/
}