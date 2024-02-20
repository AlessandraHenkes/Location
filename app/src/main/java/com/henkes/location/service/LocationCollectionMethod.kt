package com.henkes.location.service

import java.util.Locale

enum class LocationCollectionMethod {

    ALARM,
    JOB,
    WORKER;

    override fun toString(): String {
        return super.toString()
            .lowercase()
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
    }

}
