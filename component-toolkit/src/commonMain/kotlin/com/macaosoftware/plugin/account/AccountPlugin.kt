package com.macaosoftware.plugin.account

import com.macaosoftware.plugin.MacaoPlugin
import com.macaosoftware.util.MacaoError
import com.macaosoftware.util.MacaoResult
import kotlin.native.ObjCName

@ObjCName(name = "AccountPlugin", exact = true)
interface AccountPlugin : MacaoPlugin {
    suspend fun initialize(): Boolean
    suspend fun createUserWithEmailAndPassword(signUpRequest: SignUpRequest): MacaoResult<MacaoUser>
    suspend fun signInWithEmailAndPassword(signInRequest: SignInRequest): MacaoResult<MacaoUser>
    suspend fun signInWithEmailLink(signInRequest: SignInRequestForEmailLink): MacaoResult<MacaoUser>
    suspend fun sendSignInLinkToEmail(emailLinkData: EmailLinkData): MacaoResult<Unit>
    suspend fun getCurrentUser(): MacaoResult<MacaoUser>

    suspend fun getProviderData(): MacaoResult<ProviderData>
    suspend fun updateProfile(displayName: String, photoUrl: String): MacaoResult<MacaoUser>
    suspend fun updateFullProfile(displayName: String?, country: String?, photoUrl: String?, phoneNo: String?, facebookLink: String?, linkedIn: String?, github: String?): MacaoResult<UserData>
    suspend fun updateEmail(newEmail: String): MacaoResult<MacaoUser>
    suspend fun updatePassword(newPassword: String): MacaoResult<MacaoUser>
    suspend fun sendEmailVerification(): MacaoResult<MacaoUser>
    suspend fun sendPasswordReset(): MacaoResult<MacaoUser>
    suspend fun deleteUser(): MacaoResult<Unit>
    suspend fun fetchUserData(): MacaoResult<UserData>
    suspend fun checkAndFetchUserData(): MacaoResult<UserData>
    suspend fun signOut(): MacaoResult<Unit>
}

data class ProviderData(
    val email: String?,
    val displayName: String?,
    val phoneNumber: String?,
    val photoUrl: String?,
    // Add more fields as needed
)

data class UserData(
    val uid: String? = "",
    val email: String? = "",
    val displayName: String? = "",
    val password: String? = "",
    val photoUrl: String? = "",
    val country: String? = "",
    val phoneNo: String? = "",
    val facebookLink: String? = "",
    val linkedIn: String? = "",
    val github: String? = ""
)

@ObjCName(name = "MacaoSignUpRequest", exact = true)
data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
    val phoneNo: String
)

@ObjCName(name = "MacaoSignInRequest", exact = true)
data class SignInRequest(
    val email: String,
    val password: String
)

@ObjCName(name = "MacaoSignInRequestForEmailLink", exact = true)
data class SignInRequestForEmailLink(
    val email: String,
    val magicLink: String
)

@ObjCName(name = "MacaoEmailLinkData", exact = true)
data class EmailLinkData(
    val email: String
)

@ObjCName(name = "MacaoUser", exact = true)
data class MacaoUser(
    val email: String
)

data class SignupError(
    val instanceId: Long = -1L,
    val errorCode: Int = 1,
    val errorDescription: String = "Signup Failed"
) : MacaoError

data class LoginError(
    val instanceId: Long = -1L,
    val errorCode: Int = 2,
    val errorDescription: String = "Login Failed"
) : MacaoError
