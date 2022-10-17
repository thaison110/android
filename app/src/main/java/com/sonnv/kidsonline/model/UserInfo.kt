package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("class")
    val className: String,
    @SerializedName("nickname")
    val nickName: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("parent_name")
    val parentName: String,
    @SerializedName("parent_birthday")
    val parentBirthDay: String,
    @SerializedName("address")
    val addess: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("tel")
    val telePhone: String
): Serializable
