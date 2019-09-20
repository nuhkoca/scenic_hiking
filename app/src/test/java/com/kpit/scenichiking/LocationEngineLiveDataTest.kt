package com.kpit.scenichiking

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.kpit.scenichiking.util.location.LocationEngineLiveData
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Failure
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Success
import com.kpit.scenichiking.util.observeOnce
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
class LocationEngineLiveDataTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var locationEngine: LocationEngine

    @Mock
    lateinit var locationEngineRequest: LocationEngineRequest

    @Mock
    lateinit var location: Location

    @Mock
    lateinit var exception: Exception

    private val observer = mock(Observer::class.java) as Observer<LocationState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `location_should_return_success_for_location_result`() {
        val locationLiveData = LocationEngineLiveData(locationEngine, locationEngineRequest)

        locationLiveData.observeForever(observer)

        locationLiveData.observeOnce {
            Truth.assertThat(it).isEqualTo(Success(location))
        }
    }

    @Test
    fun `location_should_return_failure_for_location_result`() {
        val locationLiveData = LocationEngineLiveData(locationEngine, locationEngineRequest)

        locationLiveData.observeForever(observer)

        locationLiveData.observeOnce {
            Truth.assertThat(it).isEqualTo(Failure(exception))
        }
    }
}
