package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.santrucho.habilife.ui.util.Resource


@Composable
fun HandleFilterState(
    flow: State<Resource<List<Any>>?>,

    filteredList: List<Any>,
    emptyText: String,
    cardUI: Unit
) {

    //Make the logic to call a list of Goals which coincide with the current day
    flow.value.let { result ->
        when (result) {
            is Resource.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val filterList = filteredList
                //val filteredList = result.data.filter { it.release_date.contains("1") }
                if (filterList.isEmpty()) {
                    EmptyMessage(emptyText)
                } else {
                    cardUI
                }
            }
            is Resource.Failure -> {
                LaunchedEffect(flow.value) {
                    result.exception.message.toString()
                }
            }
            else -> {
                IllegalAccessException()
            }
        }
    }
}

