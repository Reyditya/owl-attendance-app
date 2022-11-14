package id.owl.com.owlliveattendanceapp.networking

object ApiServices {

    fun getAttendanceServices(): AttendanceServices{
        return ApiClient
            .getClient()
            .create(AttendanceServices::class.java)
    }
}