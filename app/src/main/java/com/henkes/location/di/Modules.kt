package com.henkes.location.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.henkes.location.data.AppDatabase
import com.henkes.location.data.dao.LocationDao
import com.henkes.location.service.LocationUtil
import com.henkes.location.service.alarm.AlarmScheduler
import com.henkes.location.service.alarm.LocationAlarmScheduler
import com.henkes.location.service.job.LocationJobScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Dispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Scope

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Dispatcher
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Scope
    fun providesScope(
        @Dispatcher coroutineDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(coroutineDispatcher + SupervisorJob())

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext app: Context
    ) = AppDatabase.Builder(app).build()

    @Provides
    @Singleton
    fun providesLocationDao(
        database: AppDatabase
    ) = database.getLocationDao()

    @Provides
    @Singleton
    fun providesLocationProvider(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesLocationUtil(
        locationProvider: FusedLocationProviderClient,
        locationDao: LocationDao,
        @ApplicationContext context: Context,
        @Scope coroutineScope: CoroutineScope,
        @Dispatcher coroutineDispatcher: CoroutineDispatcher,
    ) = LocationUtil(
        locationProvider = locationProvider,
        locationDao = locationDao,
        context = context,
        coroutineScope = coroutineScope,
        coroutineDispatcher = coroutineDispatcher,
    )

    @Provides
    @Singleton
    fun providesAlarmScheduler(
        @ApplicationContext context: Context
    ): AlarmScheduler = LocationAlarmScheduler(context)

    @Provides
    @Singleton
    fun providesJobScheduler(
        @ApplicationContext context: Context
    ) = LocationJobScheduler(context)

}
