package com.vcamargo.myplaces.utilities

import android.content.Context
import com.vcamargo.myplaces.viewmodel.ViewModelFactory

object InjectorUtils {

    fun provideVenueDetailViewModelFactory(
        context: Context
        ) : ViewModelFactory {
        return ViewModelFactory.Builder()
            .setContext(context)
            .build()
    }
}