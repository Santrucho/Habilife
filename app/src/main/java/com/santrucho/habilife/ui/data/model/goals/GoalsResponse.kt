package com.santrucho.habilife.ui.data.model.goals

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class GoalsResponse(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var completed : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "",
    var amount : Int? = 0,
    var amountGoal : Int? = 0,
    var subject : String = "",
    var subjectList : List<String>? = emptyList(),
    var subjectApproved : List<String> = emptyList(),
    var timesAWeek : Int? = 0,
    var kilometers: Int? = 0,
    var kilometersGoal : Int? = 0
    ) : Parcelable{
}