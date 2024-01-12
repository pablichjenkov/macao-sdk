package com.macaosoftware.plugin.account

import com.macaosoftware.util.MacaoResult
import kotlin.native.ObjCName

/**
 * An empty implementation for those platforms that don't have Firebase.
 * */
@ObjCName(name = "MacaoAccountPluginEmpty", exact = true)
class AccountPluginEmpty : AccountPlugin {
    override suspend fun initialize(): Boolean {
        println(" AuthPluginEmpty::initialize() has been called")
        return true
    }

    override suspend fun createUserWithEmailAndPassword(signUpRequest: SignUpRequest): MacaoResult<MacaoUser> {
        println(" AuthPluginEmpty::createUserWithEmailAndPassword() has been called")
        return MacaoResult.Success(
            MacaoUser("test@gmail.com")
        )
    }

    override suspend fun signInWithEmailAndPassword(signInRequest: SignInRequest): MacaoResult<MacaoUser> {
        println(" AuthPluginEmpty::signInWithEmailAndPassword() has been called")
        return MacaoResult.Success(
            MacaoUser("test@gmail.com")
        )
    }

    override suspend fun signInWithEmailLink(signInRequest: SignInRequestForEmailLink): MacaoResult<MacaoUser> {
        println(" AuthPluginEmpty::signInWithEmailLink() has been called")
        return MacaoResult.Success(MacaoUser("empty@gmail.com"))
    }

    override suspend fun sendSignInLinkToEmail(emailLinkData: EmailLinkData): MacaoResult<Unit> {
        println(" AuthPluginEmpty::sendSignInLinkToEmail() has been called")
        return MacaoResult.Success(Unit)
    }

    override suspend fun getCurrentUser(): MacaoResult<MacaoUser> {
        println(" AuthPluginEmpty::getCurrentUser() has been called")
        return MacaoResult.Success(MacaoUser("empty@gmail.com"))
    }

    override suspend fun getProviderData(): MacaoResult<ProviderData> {
        println(" AuthPluginEmpty::getProviderData() has been called")
        return MacaoResult.Success(
            ProviderData("", "", "", "")
        )
    }

    override suspend fun updateProfile(
        displayName: String,
        photoUrl: String
    ): MacaoResult<MacaoUser> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFullProfile(
        displayName: String?,
        country: String?,
        photoUrl: String?,
        phoneNo: String?,
        facebookLink: String?,
        linkedIn: String?,
        github: String?
    ): MacaoResult<UserData> {
        TODO("Not yet implemented")
    }


    override suspend fun updateEmail(newEmail: String): MacaoResult<MacaoUser> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(newPassword: String): MacaoResult<MacaoUser> {
        TODO("Not yet implemented")
    }

    override suspend fun sendEmailVerification(): MacaoResult<MacaoUser> {
        TODO("Not yet implemented")
    }

    override suspend fun sendPasswordReset(): MacaoResult<MacaoUser> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(): MacaoResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserData(): MacaoResult<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun checkAndFetchUserData(): MacaoResult<UserData> {
        println(" AuthPluginEmpty::checkAndFetchUserData() has been called")
        return MacaoResult.Success(
            UserData()
        )
    }

    override suspend fun signOut(): MacaoResult<Unit> {
        println(" AuthPluginEmpty::logoutUser() has been called")
        return MacaoResult.Success(Unit)
    }

}