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
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "",
    var amount : Double? = null,
    var subject : String = "",
    var timesAWeek : Int? = null,
    var kilometers: Int? = null,
    ) : Parcelable{
}