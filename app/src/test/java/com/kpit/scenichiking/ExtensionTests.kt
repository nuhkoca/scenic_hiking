package com.kpit.scenichiking

import android.location.Location
import com.google.common.truth.Truth
import com.kpit.scenichiking.util.ext.toLatLng
import com.kpit.scenichiking.util.ext.toPoint
import com.mapbox.mapboxsdk.geometry.LatLng
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ExtensionTests {

    @Mock
    lateinit var location: Location

    @Mock
    lateinit var latLng: LatLng

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `location_should_be_returned_to_latlng`() {
        val latlng = location.toLatLng()

        Truth.assertThat(latlng).isNotNull()
    }

    @Test
    fun `latlng_should_be_returned_to_point`() {
        val point = latLng.toPoint()

        Truth.assertThat(point).isNotNull()
    }
}
