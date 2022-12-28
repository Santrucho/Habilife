package com.santrucho.habilife.ui.data.remote.goals

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DefaultGoalsRepository @Inject constructor(private val firestore:FirebaseFirestore) : GoalsRepository {
}