package com.helloumi.weatherapplication.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

/**
 * Base class to be extended by ViewModel unit tests.
 *
 * To test the view-model of a feature named *Sample*:
 *
 * ```
 * import com.helloumi.weatherapplication.feature.sample.SampleContract.State
 *
 * class SampleViewModelTest : BaseViewModelTest<State>() {
 *
 *   override val testModule = module {
 *     factory { SampleViewModel() }
 *   }
 *
 *   private val viewModel: SampleViewModel by inject()
 *
 *   @MockK lateinit var cityForSearchEntity: CityForSearchEntity
 *
 *   @Test
 *   fun nameUpdated() {
 *     // GIVEN
 *     setupState(viewModel, State("InitialName"))
 *     every { cityForSearchEntity.name } returns "TestCityForSearchEntity"
 *
 *     // WHEN
 *     viewModel.updateCityForSearchEntity(cityForSearchEntity)
 *
 *     // THEN
 *     verify { stateObserver.onChanged(State("TestCityForSearchEntity")) }
 *   }
 *
 * }
 * ```
 */
abstract class BaseStateViewModelTest<S : BaseState> : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    /**
     * Mocked observer on the view-model's state.
     */
    @MockK
    lateinit var stateObserver: Observer<BaseState>

    abstract val testModule: Module

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxed = true)
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    /**
     * Sets the state of the view-models & registers the stateObserver to listen for changes.
     */
    fun setupState(viewModel: BaseStateViewModel<S, BaseEvent>, state: S) {
        viewModel.updateState { state }
        viewModel.state.observeForever(stateObserver)
    }
}
