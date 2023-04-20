package com.santrucho.habilife.ui.ui.habits.components.calendar

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.analytics.FirebaseAnalytics
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.*
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.Resource
import com.santrucho.habilife.ui.util.typeHelper
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@SuppressLint("RememberReturnType")
@Composable
fun CalendarView(habitViewModel: HabitViewModel) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    LaunchedEffect(Unit) {
        habitViewModel.getHabitsCompletedDates()
    }


    var isMonthCalendar by remember { mutableStateOf(false) }
    var isWeekCalendar by remember { mutableStateOf(true) }


    val selections = remember { mutableStateListOf<CalendarDay>() }
    val selectionsWeek = remember { mutableStateListOf<LocalDate>() }

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                isMonthCalendar = !isMonthCalendar
                isWeekCalendar = !isWeekCalendar
                LogBundle.logBundleAnalytics(firebaseAnalytics,"Calendar Expanded","calendar_expanded_pressed")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val currentDate = remember { LocalDate.now() }
            val currentMonth = remember { YearMonth.now() }
            val startDate =
                remember { currentMonth.minusMonths(100).atStartOfMonth() }
            val endDate =
                remember { currentMonth.plusMonths(100).atEndOfMonth() }
            val firstDayOfWeek =
                remember { firstDayOfWeekFromLocale() }

            val startMonth = remember { currentMonth.minusMonths(100) }
            val endMonth = remember { currentMonth.plusMonths(100) }

            val daysOfWeek = daysOfWeek()

            val stateCalendar = rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = firstDayOfWeek
            )

            val stateWeek = rememberWeekCalendarState(
                startDate = startDate,
                endDate = endDate,
                firstVisibleWeekDate = currentDate,
                firstDayOfWeek = firstDayOfWeek
            )
            CalendarTitle(
                isWeekMode = isWeekCalendar,
                monthState = stateCalendar,
                weekState = stateWeek
            )
            if (isWeekCalendar) {
                WeekCalendar(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.background)
                        .padding(16.dp),
                    state = stateWeek,
                    dayContent = { day ->
                        Day(day.date, habitViewModel) { date ->
                            if (day.date != LocalDate.now()) {
                                selectionsWeek.remove(date)
                            } else {
                                selectionsWeek.add(date)
                            }
                        }
                    }
                )
            }

            if (isMonthCalendar) {
                HorizontalCalendar(
                    state = stateCalendar,
                    dayContent = { day ->
                        DayCalendar(day, habitViewModel) { day ->
                            if (day.date != LocalDate.now()) {
                                selections.remove(day)
                            } else {
                                selections.add(day)
                            }
                        }
                    },
                    monthHeader = { MonthHeader(daysOfWeek = daysOfWeek) }
                )
            }
        }
    }
}


@Composable
private fun Day(
    date: LocalDate,
    habitViewModel: HabitViewModel,
    onClick: (LocalDate) -> Unit
) {
    val daysCompleted = habitViewModel.daysCompleted.value ?: emptyList()
    val dateFormatter = DateTimeFormatter.ofPattern("dd")

    val isDateComplete =
        (daysCompleted.any { it == date.toString() } && LocalDate.parse((date.toString())) <= LocalDate.now())
    val habits = habitViewModel.habitState.collectAsState().value.let { resource ->
        when (resource) {
            is Resource.Success -> resource.data
            else -> {
                emptyList()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(date) }
            .background(if (LocalDate.now() == date) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES")),
                fontSize = 14.sp,
                color = if (LocalDate.now() == date) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = if (LocalDate.now() == date) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold,
            )
            if (isDateComplete) {
                val habitsWithDate = habits.filter { it.daysCompleted.contains(date.toString()) }
                Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.TopCenter) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        habitsWithDate.forEach { habit ->
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .height(4.dp)
                                    .width(4.dp)
                                    .background(typeHelper(habitType = habit.type))
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun DayCalendar(
    day: CalendarDay,
    habitViewModel: HabitViewModel,
    onClick: (CalendarDay) -> Unit
) {
    val daysCompleted = habitViewModel.daysCompleted.value ?: emptyList()
    Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",daysCompleted.toString())
    val isCompleted = (daysCompleted.any { it == day.date.toString() } && LocalDate.parse((day.date.toString())) <= LocalDate.now())
    val habits = habitViewModel.habitState.collectAsState().value.let { resource ->
        when (resource) {
            is Resource.Success -> resource.data
            else -> {
                emptyList()
            }
        }
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RectangleShape)
            .background(
                color = if (LocalDate.now() == day.date) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) {
                if (LocalDate.now() == day.date) MaterialTheme.colors.primary
                else MaterialTheme.colors.primaryVariant
            } else Color.Gray,
            fontWeight = FontWeight.Medium
        )
        if (isCompleted) {
            val habitsWithDate = habits.filter { it.daysCompleted.contains(day.date.toString()) }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    habitsWithDate.forEach { habit ->
                        Box(
                            modifier = Modifier
                                .padding(top = 1.dp)
                                .height(5.dp)
                                .fillMaxWidth()
                                .background(typeHelper(habitType = habit.type))
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES")),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: Month,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            icon = Icons.Outlined.ArrowBack,
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = currentMonth.getDisplayName(TextStyle.FULL,Locale("es","ES")),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.primaryVariant
        )
        CalendarNavigationIcon(
            icon = Icons.Outlined.ArrowForward,
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        imageVector = icon,
        contentDescription = contentDescription,
        tint = MaterialTheme.colors.primary
    )
}

@Composable
private fun CalendarTitle(
    isWeekMode: Boolean,
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(weekState)
    MonthAndWeekCalendarTitle(
        isWeekMode = isWeekMode,
        currentMonth = if (isWeekMode) visibleWeek.days.first().date.yearMonth.month else visibleMonth.yearMonth.month,
        monthState = monthState,
        weekState = weekState,
    )
}

@Composable
fun MonthAndWeekCalendarTitle(
    isWeekMode: Boolean,
    currentMonth: Month,
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val coroutineScope = rememberCoroutineScope()
    SimpleCalendarTitle(
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
        currentMonth = currentMonth,
        goToPrevious = {
            coroutineScope.launch {
                if (isWeekMode) {
                    val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
                    weekState.animateScrollToWeek(targetDate)
                } else {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                    monthState.animateScrollToMonth(targetMonth)
                }
            }
        },
        goToNext = {
            coroutineScope.launch {
                if (isWeekMode) {
                    val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
                    weekState.animateScrollToWeek(targetDate)
                } else {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                    monthState.animateScrollToMonth(targetMonth)
                }
            }
        },
    )
}