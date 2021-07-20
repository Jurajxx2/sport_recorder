package com.example.sportrecorder.screens.main

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.*
import com.example.sportrecorder.helpers.EncryptedPrefs
import com.example.sportrecorder.helpers.Status
import com.example.sportrecorder.model.SportRecord
import com.example.sportrecorder.repository.SportRecordRepository
import com.example.sportrecorder.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.location.GeocoderNominatim
import org.threeten.bp.LocalDate
import java.util.*

class MainViewModel(private val userRepository: UserRepository, private val sportRecordRepository: SportRecordRepository): ViewModel() {

    val currentLocation = MutableLiveData<Pair<Double, Double>>()
    val user = liveData {
        userRepository.getUser().catch {

        }.collect {
            emit(it)
        }
    }

    val addRecordError = MutableLiveData<String>()
    val getRecordsError = MutableLiveData<String>()

    val addNewSportName = MutableLiveData("Enter sport name")
    val addNewSportPlaceName = MutableLiveData("")
    val addNewSportPlaceLatLng = MutableLiveData<Pair<Double,Double>>()
    val addNewSportDuration = MutableLiveData<String>("1h 0m 0s")
    val addNewSportStorageType = MutableLiveData<SportRecord.StorageType>(SportRecord.StorageType.Local)

    val addNewSportStep = MutableLiveData(0)
    val recordList = MutableLiveData<List<SportRecord>?>(emptyList())
    val filterType = MutableLiveData<SportRecord.StorageType?>(null)

    val data = MediatorLiveData<Pair<List<SportRecord>?, SportRecord.StorageType?>>().apply {
        addSource(recordList) {
            value = Pair(it, filterType.value)
        }

        addSource(filterType) {
            value = Pair(recordList.value, it)
        }
    }

    val loading = MutableLiveData(false)

    fun getLocationFromCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = GeocoderNominatim(System.getProperty("http.agent")).getFromLocation(latitude, longitude, 1)
            var address = ""
            address += if (addresses.firstOrNull()?.thoroughfare.isNullOrEmpty()) "Unknown street" else addresses.firstOrNull()?.thoroughfare
            address += if (addresses.firstOrNull()?.subThoroughfare.isNullOrEmpty()) "" else " ${addresses.firstOrNull()?.subThoroughfare}"
            address += if (addresses.firstOrNull()?.adminArea.isNullOrEmpty()) "" else ", ${addresses.firstOrNull()?.adminArea}"
            addNewSportPlaceName.postValue(address)
        }
    }

    fun getRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            sportRecordRepository.getSportRecords().catch {
                getRecordsError.postValue("There was an error while getting your sport records. Please try again later or contact our support.")
                Log.d("RECORDERX", " test1")
            }.collect {
                when(it.status) {
                    Status.LOADING -> loading.postValue(true)
                    Status.ERROR -> {getRecordsError.postValue("There was an error while getting your sport records. Please try again later or contact our support.")
                        Log.d("RECORDERX", " test2: ${it.error?.stackTraceToString()}")
                        loading.postValue(false)
                    }
                    Status.SUCCESS -> {
                        recordList.postValue(it.data)
                        loading.postValue(false)
                    }
                }
            }
        }
    }

    fun savePlace() {
        viewModelScope.launch(Dispatchers.IO) {
            val newSportRecord = SportRecord(
                UUID.randomUUID().toString(),
                addNewSportName.value,
                SportRecord.Place(
                    addNewSportPlaceName.value,
                    addNewSportPlaceLatLng.value?.first,
                    addNewSportPlaceLatLng.value?.second
                ),
                addNewSportDuration.value,
                addNewSportStorageType.value
            )
            sportRecordRepository.createSportRecord(newSportRecord).catch {
                addRecordError.postValue("There was an error while creating your sport record. Please try again later or contact our support.")
            }.collect {
                when(it.status) {
                    Status.LOADING -> loading.postValue(true)
                    Status.ERROR -> {
                        addRecordError.postValue("There was an error while creating your sport record. Please try again later or contact our support.")
                        loading.postValue(false)}
                    Status.SUCCESS -> {
                        addNewSportName.postValue("Enter sport name")
                        addNewSportPlaceName.postValue("")
                        addNewSportPlaceLatLng.postValue(50.0819889 to 14.4128241)
                        addNewSportDuration.postValue("")
                        addNewSportStorageType.postValue(SportRecord.StorageType.Local)
                        loading.postValue(false)
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            EncryptedPrefs.encryptedPrefs.edit().clear().apply()
            sportRecordRepository.deleteAll()
            userRepository.deleteUser()
        }
    }

    fun changeAddNewSportStep(step: Int) {
        if (addNewSportStep.value == step) {
            if (step == 3) {
                addNewSportStep.value = 0
            } else {
                addNewSportStep.value = step + 1
            }
        } else {
            addNewSportStep.value = step
        }
    }
}