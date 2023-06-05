package com.example.myapplication.base

import com.example.myapplication.view.main.MainActivity


class Event<T> {
    enum class EventType {
        PROGRESS_SHOW, PROGRESS_HIDE, START_ACTIVITY_NEW_TASK, TOKEN_EXPIRED, RESPONSE, SHOW_TOAST
    }

    class Navigation(var activityName: ActivityName) {
        val activityClass: Class<*>
            get() {
                return when (activityName) {
                    ActivityName.MAIN_ACTIVITY -> MainActivity::class.java
                }
                throw IllegalArgumentException("" + activityName.name + " should be added here")
            }
    }

    enum class ActivityName {
        MAIN_ACTIVITY
    }

    var key: EventType
    var value: T? = null
    var parameters: Map<String, T>? = null

    constructor(key: EventType, value: T) {
        this.key = key
        this.value = value
    }

    constructor(key: EventType, value: T, parameters: Map<String, T>?) { //for intent parameters
        this.key = key
        this.value = value
        this.parameters = parameters
    }

    constructor(key: EventType) {
        this.key = key
    }

    class UserMessage {
        var stringMessage: String? = null
        var stringResId: Int = 0

        constructor(stringMessage: String) {
            this.stringMessage = stringMessage
        }

        constructor(stringResId: Int) {
            this.stringResId = stringResId
        }
    }
}