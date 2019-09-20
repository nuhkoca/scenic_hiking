package com.kpit.scenichiking

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.kpit.scenichiking.ui.map.MapViewModel
import com.kpit.scenichiking.util.executor.Executors
import com.kpit.scenichiking.util.location.NavigationRouteProvider
import com.kpit.scenichiking.util.observeOnce
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.DENIED
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.GRANTED
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.RATIONALE_ACCEPTED
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.RATIONALE_DENIED
import com.kpit.scenichiking.util.permission.LocationPermissionDispatcher
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
class MapViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val observer = mock(Observer::class.java) as Observer<PermissionState>

    private lateinit var mapViewModel: MapViewModel
    private val compositeDisposable = CompositeDisposable()

    @Mock
    lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val navigationRouteProvider = mock(NavigationRouteProvider::class.java)
        val executors = mock(Executors::class.java)
        val permissionDispatcher = LocationPermissionDispatcher(context)

        mapViewModel = MapViewModel(
            permissionDispatcher,
            navigationRouteProvider,
            compositeDisposable,
            executors
        )
    }

    @Test
    fun `permission_result_should_be_granted`() {
        mapViewModel.permissionResultLiveData.observeForever(observer)

        mapViewModel.checkPermissionResult()

        mapViewModel.permissionResultLiveData.observeOnce {
            Truth.assertThat(it).isEqualTo(GRANTED)
        }
    }

    @Test
    fun `permission_result_should_not_be_denied`() {
        mapViewModel.permissionResultLiveData.observeForever(observer)

        mapViewModel.checkPermissionResult()

        mapViewModel.permissionResultLiveData.observeOnce {
            Truth.assertThat(it).isNotEqualTo(DENIED)
        }
    }

    @Test
    fun `permission_result_should_not_be_rationale_denied`() {
        mapViewModel.permissionResultLiveData.observeForever(observer)

        mapViewModel.checkPermissionResult()

        mapViewModel.permissionResultLiveData.observeOnce {
            Truth.assertThat(it).isNotEqualTo(RATIONALE_DENIED)
        }
    }

    @Test
    fun `permission_result_should_not_be_rationale_accepted`() {
        mapViewModel.permissionResultLiveData.observeForever(observer)

        mapViewModel.checkPermissionResult()

        mapViewModel.permissionResultLiveData.observeOnce {
            Truth.assertThat(it).isNotEqualTo(RATIONALE_ACCEPTED)
        }
    }
}
