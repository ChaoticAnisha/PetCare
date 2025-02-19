package com.an1ee.petcare.viewmodel

import com.an1ee.petcare.model.UserModel
import com.an1ee.petcare.repository.UserRepository
import com.an1ee.petcare.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo :UserRepositoryImpl){
    fun login(email:String,password:String,callback:(Boolean,String) -> Unit){
        return repo.login(email,password,callback)
    }

    fun signup(email: String,password: String,callback: (Boolean,String,String) -> Unit){
        return repo.signup(email,password,callback)
    }

    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit){
        return repo.forgetPassword(email,callback)
    }

    fun addUserToDatabase(userId:String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        return repo.addUserToDatabase(userId,userModel,callback)
    }

    fun getCurrentUser() : FirebaseUser?{
        return repo.getCurrentUser()
    }

    fun getUserFromDatabase(userId:String,
                            callback: (UserModel?, Boolean, String)
                            -> Unit){
        return repo.getUserFromDatabase(userId,callback)
    }

    fun logout(callback: (Boolean, String) -> Unit){
        return repo.logout(callback)
    }

    fun editProfile(userId: String,data:MutableMap<String,Any>,
                    callback: (Boolean, String) -> Unit){
        return repo.editProfile(userId,data,callback)
    }
}