package com.example.renty.di

import android.content.Context
import com.example.renty.authentication.data.prefs.PrefsStore
import com.example.renty.authentication.data.remote.AuthenticationApi
import com.example.renty.authentication.data.repository.AuthenticationRepositoryImpl
import com.example.renty.authentication.domain.repository.AuthenticationRepository
import com.example.renty.authentication.domain.use_case.LogInUseCase
import com.example.renty.authentication.domain.use_case.PhoneVerificationUseCase
import com.example.renty.authentication.domain.use_case.RegisterUseCase
import com.example.renty.core.utils.SERVER_URL
import com.example.renty.homeResident.domain.homeResidentRepository
import com.example.renty.homeResident.domain.homeResidentRepositoryImpl
import com.example.renty.homeResident.domain.use_case.GetInformationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object authenticationModule {

    /** DataStore **/
    @Provides
    @Singleton
    fun providePrefsStoreAbstraction(@ApplicationContext appContext: Context) =
        PrefsStore(appContext)

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthenticationRepository) : LogInUseCase {
        return LogInUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideVerifyUseCase(repository: AuthenticationRepository) : PhoneVerificationUseCase {
        return PhoneVerificationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthenticationRepository) : RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetInformationsUseCase(repository: homeResidentRepository) : GetInformationsUseCase {
        return GetInformationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideHomeResidentReposotirt(api : AuthenticationApi) : homeResidentRepository {
        return homeResidentRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api : AuthenticationApi) : AuthenticationRepository {
        return AuthenticationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthenticationApi() : AuthenticationApi{
        return Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthenticationApi::class.java)
    }



}