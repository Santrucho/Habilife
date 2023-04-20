package com.santrucho.habilife.ui.data.model.goals

data class AcademicGoal(
    override var id: String = "",
    override var userId: String = "",
    override var title: String = "",
    override var description: String = "",
    override var completed: Boolean = false,
    override var release_date: String = "",
    override var image: String = "",
    override var type: String = "Academic",
    override var subject: String = "",
    override var subjectList: List<String> = emptyList(),
    override var subjectApproved: List<String> = emptyList(),
) : GoalsResponse()
