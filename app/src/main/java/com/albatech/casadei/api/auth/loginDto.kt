package com.albatech.casadei.api.auth
import retrofit2.http.Body
import retrofit2.http.POST
data class LoginRequestDto(
    val email: String,
    val password: String
)

data class LoginResponseDto(
    val data: UserDto,
)
data class UserDto(
   val access_token: String,
)

interface AuthApi {
    @POST("auth/token") // <= ini relative path
    suspend fun login(
        @Body body: LoginRequestDto
    ): LoginResponseDto
}