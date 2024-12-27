package com.example.traficlight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traficlight.enum_class.TrafficLightMode
import kotlinx.coroutines.Job

class MainViewModel: ViewModel() {

    var trafficLightMode = MutableLiveData(TrafficLightMode.OFF)

    var currentJob: Job? = null

    fun setTrafficLightOff() { trafficLightMode.value = TrafficLightMode.OFF }
    fun setTrafficLightDay() { trafficLightMode.value = TrafficLightMode.DAY_MODE }
    fun setTrafficLightNight() { trafficLightMode.value = TrafficLightMode.NIGHT_MODE }

    fun cancelJob() = currentJob?.cancel()
}