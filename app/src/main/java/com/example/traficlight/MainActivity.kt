package com.example.traficlight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.traficlight.databinding.ActivityMainBinding
import com.example.traficlight.enum_class.TrafficLightMode
import com.example.traficlight.viewmodel.MainViewModel
import com.example.traficlight.viewmodel.MainViewModelFactory
import kotlinx.coroutines.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val tag = "MainActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.stopButton.setOnClickListener {
            when (viewModel.trafficLightMode.value) {
                null -> Log.e(tag, "trafficLightMode inside MainViewModel is null")
                TrafficLightMode.OFF -> {
                    viewModel.setTrafficLightDay()
                    changeIsEnabledPlayPauseButton(true)
                }

                else -> viewModel.setTrafficLightOff()
            }
        }

        binding.pauseButton.setOnClickListener {
            if (viewModel.trafficLightMode.value != TrafficLightMode.NIGHT_MODE)
                viewModel.setTrafficLightNight()
        }
        binding.playButton.setOnClickListener {
            if (viewModel.trafficLightMode.value != TrafficLightMode.DAY_MODE)
                viewModel.setTrafficLightDay()
        }

        viewModel.trafficLightMode.observe(this) {
            when (it) {
                TrafficLightMode.OFF -> {
                    viewModel.cancelJob()
                    turnOffLight()
                    changeIsEnabledPlayPauseButton(false)
                }

                TrafficLightMode.DAY_MODE -> {
                    viewModel.cancelJob()
                    turnOffLight()
                    viewModel.currentJob = GlobalScope.launch(Dispatchers.IO) { tlDayMode() }
                }

                TrafficLightMode.NIGHT_MODE -> {
                    viewModel.cancelJob()
                    turnOffLight()
                    viewModel.currentJob = GlobalScope.launch(Dispatchers.IO) { tlNightMode() }
                }

                else -> Log.e(tag, "trafficLightMode inside MainViewModel changed to null")
            }
        }
    }

    private fun turnOffLight() {
        val color = resources.getColor(R.color.grey, applicationContext.theme)
        binding.redLight.setColorFilter(color)
        binding.orangeLight.setColorFilter(color)
        binding.greenLight.setColorFilter(color)
    }

    private fun changeIsEnabledPlayPauseButton(isEnabled: Boolean) {
        binding.pauseButton.isEnabled = isEnabled
        binding.playButton.isEnabled = isEnabled
    }

    private suspend fun tlDayMode() {
        while (true) {
            withContext(Dispatchers.Main) {
                binding.redLight.setColorFilter(
                    resources.getColor(
                        R.color.red,
                        applicationContext.theme
                    )
                )
                binding.orangeLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
                binding.greenLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
            }
            delay(5.toDuration(DurationUnit.SECONDS))

            withContext(Dispatchers.Main) {
                binding.redLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
                binding.orangeLight.setColorFilter(
                    resources.getColor(
                        R.color.orange,
                        applicationContext.theme
                    )
                )
                binding.greenLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
            }
            delay(2.toDuration(DurationUnit.SECONDS))

            withContext(Dispatchers.Main) {
                binding.redLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
                binding.orangeLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
                binding.greenLight.setColorFilter(
                    resources.getColor(
                        R.color.green,
                        applicationContext.theme
                    )
                )
            }
            delay(5.toDuration(DurationUnit.SECONDS))

            withContext(Dispatchers.Main) {
                binding.redLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
                binding.orangeLight.setColorFilter(
                    resources.getColor(
                        R.color.orange,
                        applicationContext.theme
                    )
                )
                binding.greenLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
            }
            delay(5.toDuration(DurationUnit.SECONDS))
        }
    }


    private suspend fun tlNightMode() {
        while (true) {
            withContext(Dispatchers.Main) {
                binding.orangeLight.setColorFilter(
                    resources.getColor(
                        R.color.orange,
                        applicationContext.theme
                    )
                )
            }
            delay(1.toDuration(DurationUnit.SECONDS))

            withContext(Dispatchers.Main) {
                binding.orangeLight.setColorFilter(
                    resources.getColor(
                        R.color.grey,
                        applicationContext.theme
                    )
                )
            }
            delay(1.toDuration(DurationUnit.SECONDS))
        }
    }
}