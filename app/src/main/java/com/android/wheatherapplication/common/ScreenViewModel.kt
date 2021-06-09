/*
 *      Copyright (C) 2020 Parrot Drones SAS
 *
 *      Redistribution and use in source and binary forms, with or without
 *      modification, are permitted provided that the following conditions
 *      are met:
 *      * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in
 *         the documentation and/or other materials provided with the
 *         distribution.
 *      * Neither the name of the Parrot Company nor the names
 *        of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written
 *        permission.
 *
 *      THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *      "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *      LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *      FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *      PARROT COMPANY BE LIABLE FOR ANY DIRECT, INDIRECT,
 *      INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *      BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 *      OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 *      AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *      OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *      OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *      SUCH DAMAGE.
 */

package com.android.wheatherapplication.common

/**
 * Screen view-model class extends [BaseStateViewModel] class.
 *
 * It extends [BaseStateViewModel] to add screen related state properties in [ScreenBaseState].
 *
 * @param initialState initial state of the viewmodel (mainly for testing purpose).
 */
abstract class ScreenViewModel<S : ScreenBaseState, E : BaseEvent>(
        initialState: S
) : BaseStateViewModel<S, E>(initialState)

/**
 * Base state including screen related properties to be extended by view-model states.
 */
interface ScreenBaseState : BaseState {

    /**
     * Whether or not we should close the screen.
     */
    val shouldClose: Boolean
}
