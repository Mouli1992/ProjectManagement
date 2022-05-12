package com.example.projectmanagement

import com.example.projectmanagement.utils.Constants.Companion.CONTENT_TYPE
import com.example.projectmanagement.utils.Constants.Companion.SERVER_KEY
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import okhttp3.ResponseBody as ResponseBody

interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY","Content-Type: $CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification:PushNotification
    ): Response
}