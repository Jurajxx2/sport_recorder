package com.example.sportrecorder.screens.main.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseFragment
import com.example.sportrecorder.databinding.FragmentRecordNewBinding
import com.example.sportrecorder.model.SportRecord
import com.example.sportrecorder.screens.main.MainViewModel
import com.google.android.gms.location.*
import com.pixplicity.easyprefs.library.Prefs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController


class RecordNewFragment: BaseFragment<MainViewModel, FragmentRecordNewBinding>() {

    override val layout = R.layout.fragment_record_new
    override val viewModel: MainViewModel by sharedViewModel()

    companion object {
        const val ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE = 1234
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance().load(context, Prefs.getPreferences())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setup() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupLocation()

        viewModel.addRecordError.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        binding.map.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> binding.scrollHolder.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> binding.scrollHolder.requestDisallowInterceptTouchEvent(
                    false
                )
            }
            binding.map.onTouchEvent(event)
        }

        binding.goToMyPosition.setOnClickListener {
            val startPoint =
                viewModel.currentLocation.value?.let { location -> GeoPoint(location.first,location.second) }
            binding.map.controller.animateTo(startPoint, 13.0, 10)
        }

        viewModel.addNewSportPlaceLatLng.observe(this, {
            viewModel.getLocationFromCoordinates(it.first, it.second)
        })

        viewModel.addNewSportStep.observe(this, {
            binding.sportNameContent.visibility = if (it == 0) View.VISIBLE else View.GONE
            binding.sportLocationContent.visibility = if (it == 1) View.VISIBLE else View.GONE
            binding.sportDurationContent.visibility = if (it == 2) View.VISIBLE else View.GONE
            binding.sportStorageTypeContent.visibility = if (it == 3) View.VISIBLE else View.GONE

            binding.sportNameIcon.rotation = if (it == 0) 180f else 0f
            binding.sportLocationIcon.rotation = if (it == 1) 180f else 0f
            binding.sportDurationIcon.rotation = if (it == 2) 180f else 0f
            binding.sportStorageTypeIcon.rotation = if (it == 3) 180f else 0f
        })

        binding.sportStorageTypeContent.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.local -> { viewModel.addNewSportStorageType.value = SportRecord.StorageType.Local }
                R.id.remote -> {viewModel.addNewSportStorageType.value = SportRecord.StorageType.Remote }
            }
        }
        binding.sportStorageTypeContent.check(R.id.local)
        setupDurationPicker()

        viewModel.addNewSportDuration.observe(this, {
            if (it.isEmpty()) {
                binding.local.isChecked = true
                binding.remote.isChecked = false
                binding.hours.value = 1
                binding.minutes.value = 0
                binding.seconds.value = 0
                viewModel.addNewSportDuration.value = "1h 0m 0s"
                Toast.makeText(context, "Record was successfuly created!", Toast.LENGTH_SHORT).show()
            } else {
                val content = it.split(" ")
                val hours = content[0].replace("h","")
                val minutes = content[1].replace("m","")
                val seconds = content[2].replace("s","")

                binding.hours.value = hours.toInt()
                binding.minutes.value = minutes.toInt()
                binding.seconds.value = seconds.toInt()
            }
        })
    }

    private fun setupDurationPicker() {
        val minH = 0
        val maxH = 23
        val steppedListH = (minH..maxH).toList()
        binding.hours.displayedValues = steppedListH.map { it.toString() }.toTypedArray()
        binding.hours.minValue = 0
        binding.hours.maxValue = steppedListH.size - 1
        binding.hours.value = 1

        val minM = 0
        val maxM = 59
        val steppedListM = (minM..maxM).toList()
        binding.minutes.displayedValues = steppedListM.map { it.toString() }.toTypedArray()
        binding.minutes.minValue = 0
        binding.minutes.maxValue = steppedListM.size - 1
        binding.minutes.value = 0

        val minS = 0
        val maxS = 59
        val steppedListS = (minS..maxS).toList()
        binding.seconds.displayedValues = steppedListS.map { it.toString() }.toTypedArray()
        binding.seconds.minValue = 0
        binding.seconds.maxValue = steppedListS.size - 1
        binding.seconds.value = 0

        binding.hours.setOnValueChangedListener { _, _, _ ->
            refreshDurationValue()
        }
        binding.minutes.setOnValueChangedListener { _, _, _ ->
            refreshDurationValue()
        }
        binding.seconds.setOnValueChangedListener { _, _, _ ->
            refreshDurationValue()
        }
    }

    private fun refreshDurationValue() {
        viewModel.addNewSportDuration.value = "${binding.hours.value}h ${binding.minutes.value}m ${binding.seconds.value}s"
    }

    private fun setupLocation() {
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        val startPoint = GeoPoint(viewModel.addNewSportPlaceLatLng.value?.first?:50.0819889,viewModel.addNewSportPlaceLatLng.value?.second?:14.4128241)
        binding.map.controller.animateTo(startPoint, 13.0, 10)
        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        binding.map.setMultiTouchControls(true)

        binding.map.addMapListener(DelayedMapListener(object: MapListener{
            override fun onScroll(event: ScrollEvent?): Boolean {
                viewModel.addNewSportPlaceLatLng.value = binding.map.boundingBox.centerLatitude to binding.map.boundingBox.centerLongitude
                return true
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                return true
            }
        }, 1000))
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            binding.goToMyPosition.visibility = View.VISIBLE
            val request = LocationRequest.create()
            request.interval = 50
            request.fastestInterval = 10
            request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        if (viewModel.currentLocation.value == null) {
                            viewModel.addNewSportPlaceLatLng.value = latitude to longitude
                        }
                        viewModel.currentLocation.value = latitude to longitude
                    }
                }
            }, Looper.getMainLooper())
        } else {
            binding.goToMyPosition.visibility = View.INVISIBLE
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION) {
            setupLocation()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }
}