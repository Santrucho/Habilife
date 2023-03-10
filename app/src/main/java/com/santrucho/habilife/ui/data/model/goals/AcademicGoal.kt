package com.santrucho.habilife.ui.data.model.goals

data class AcademicGoal(
    var id : String="",
    var userId : String="",
    var title : String="",
    var description : String="",
    var isCompleted : Boolean=false,
    var release_date : String= "",
    var image : String = "",
    var type : String = "Academic",
    //New parameters
    var subject : String? = "",
    var subjectList : List<String>? = emptyList(),
    var subjectApproved : List<String>? = emptyList()
) {
}