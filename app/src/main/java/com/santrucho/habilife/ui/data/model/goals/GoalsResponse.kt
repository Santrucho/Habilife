package com.santrucho.habilife.ui.data.model.goals

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
open class GoalsResponse(
    open var id : String="",
    open var userId : String="",
    open var title : String="",
    open var description : String="",
    open var completed : Boolean=false,
    open var release_date : String= "",
    open var image : String = "",
    open var type : String = "",
    open var amount : Int? = 0,
    open var amountGoal : Int? = 0,
    open var subject: String = "",
    open var subjectList: List<String> = emptyList(),
    open var subjectApproved: List<String> = emptyList(),
    open var kilometers: Int? = 0,
    open var kilometersGoal: Int? = 0,
    open var timesAWeek: Int? = 0,
    ) : Parcelable {
    }