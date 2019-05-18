package com.vcamargo.myplaces.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Handler
import android.util.Log
import com.vcamargo.myplaces.BuildConfig
import com.vcamargo.myplaces.converter.VenuesConverterFactory
import com.vcamargo.myplaces.repository.IRepository
import com.vcamargo.myplaces.repository.MockRepository
import com.vcamargo.myplaces.repository.VenuesRepository
import com.vcamargo.myplaces.webservice.Webservice
import retrofit2.Retrofit

class ViewModelFactory (val repository: IRepository) : ViewModelProvider.Factory {

    companion object {
        val LOG_TAG = ViewModelFactory::class.java.simpleName
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenuesListViewModel::class.java)) {
            return VenuesListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(VenueDetailsViewModel::class.java)) {
            return VenueDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    class Builder {
        private lateinit var repository: IRepository
        private var retrofit: Retrofit? = null
        private var mContext : Context? = null

        fun setContext(mContext : Context) = apply {
            this.mContext = mContext
        }

        private fun asProdApi() = apply {
            try {
                retrofit = Retrofit.Builder()
                    .baseUrl(Webservice.BASE_URL)
                    .addConverterFactory(VenuesConverterFactory())
                    .build()
            } catch (ex : IllegalArgumentException) {
                Log.e(LOG_TAG, ex.localizedMessage)
                //failsafe for lateinit var is going to be the mock repository
                asMock()
            }

            retrofit?.let {
                //Webservice
                val webservice = it.create(Webservice::class.java)
                repository = VenuesRepository(webservice)
            }
        }

        private fun asMock() = apply {
            mContext?.let {
                repository = MockRepository(Handler(), it)
            }
        }

        fun build() : ViewModelFactory {
            when (BuildConfig.BUILD_TYPE) {
                "debug_mock" -> asMock()
                else -> asProdApi()
            }
            return ViewModelFactory(repository)
        }
    }
}