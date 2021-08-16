package com.wenitech.cashdaily.framework.commons

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestMviViewModel :
    BaseViewModel<TestContract.Event, TestContract.State, TestContract.Effect>() {

    override fun createInitialState(): TestContract.State {
        return TestContract.State(
            TestContract.RandomNumberState.Idle
        )
    }

    override fun handleEvent(event: TestContract.Event) {
        when (event) {
            TestContract.Event.OnRandomNumberClicked -> {
                generateRandomNumber()
            }
            TestContract.Event.OnShowToastClicked -> {
                setEffect { TestContract.Effect.ShowToast }
            }
        }
    }

    private fun generateRandomNumber() {
        viewModelScope.launch {
            // set Loading
            setState {
                copy(randomNumberState = TestContract.RandomNumberState.Loading)
            }

            try {
                // Add delay for simulate network call
                delay(5000)
                val random = (0..10).random()
                if (random % 2 == 0) {
                    // If error happens set state to Idle
                    // If you want create a Error State and use it
                    setState { copy(randomNumberState = TestContract.RandomNumberState.Idle) }
                    throw RuntimeException("Number is even")
                }
                // Update state
                setState { copy(randomNumberState = TestContract.RandomNumberState.Success(number = random)) }
            } catch (exception: Exception) {
                // Show error
                setEffect { TestContract.Effect.ShowToast }
            }
        }
    }

}