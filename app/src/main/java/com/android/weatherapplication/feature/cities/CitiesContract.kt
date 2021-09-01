package com.android.weatherapplication.feature.cities

import com.android.weatherapplication.common.BaseState

/**
 * Cities contract.
 */
interface CitiesContract {

    interface ViewModel {
    }

    /**
     * @property textError displayed a general text error resource.
     * @property textNameError displayed a text name error resource.
     * @property displayParentalConsentError displayed or not a parental consent error.
     * @property canContinue whether the user can continue registration.
     * @property email email user.
     * @property password password user.
     * @property name user name.
     * @property firstName user firstName.
     * @property parentalConsent user parental consent.
     */
    data class State(
            var textError: Int? = null,
            val textNameError: Int? = null,
            val displayParentalConsentError: Boolean = false,
            val canContinue: Boolean = false,
            val email: String = "",
            val password: String = "",
            val name: String = "",
            val firstName: String = "",
            val parentalConsent: Boolean = false
    ) : BaseState
}
