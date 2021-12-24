package com.wenitech.cashdaily.di

import com.wenitech.cashdaily.domain.repositories.*
import com.wenitech.cashdaily.domain.usecases.auth.*
import com.wenitech.cashdaily.domain.usecases.caja.GetRecentMovementsUseCase
import com.wenitech.cashdaily.domain.usecases.caja.GetUserBoxUseCase
import com.wenitech.cashdaily.domain.usecases.caja.RemoveMoneyOnBoxUseCase
import com.wenitech.cashdaily.domain.usecases.caja.SaveMoneyOnBoxUseCase
import com.wenitech.cashdaily.domain.usecases.client.GetAllClientsPagingUseCase
import com.wenitech.cashdaily.domain.usecases.client.GetClientById
import com.wenitech.cashdaily.domain.usecases.client.SaveClientUseCase
import com.wenitech.cashdaily.domain.usecases.credit.GetCreditClientUseCase
import com.wenitech.cashdaily.domain.usecases.credit.GetQuotasUseCase
import com.wenitech.cashdaily.domain.usecases.credit.SaveNewCreditUseCase
import com.wenitech.cashdaily.domain.usecases.credit.SaveQuotaOfCreditClientUseCase
import com.wenitech.cashdaily.domain.usecases.route.GetRoutesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {
    // Binds interfaces con implementation
    @Provides
    fun provideGetUserBoxUseCase(boxRepository: BoxRepository): GetUserBoxUseCase {
        return GetUserBoxUseCase(boxRepository)
    }

    @Provides
    fun provideRecentMovementsUseCase(boxRepository: BoxRepository): GetRecentMovementsUseCase {
        return GetRecentMovementsUseCase(boxRepository)
    }

    @Provides
    fun provideSaveMoneyOnBoxUseCase(boxRepository: BoxRepository): SaveMoneyOnBoxUseCase {
        return SaveMoneyOnBoxUseCase(boxRepository)
    }

    @Provides
    fun provideRemoveMoneyOnBoxUseCase(boxRepository: BoxRepository): RemoveMoneyOnBoxUseCase {
        return RemoveMoneyOnBoxUseCase(boxRepository)
    }

    @Provides
    fun provideGetAllClientsPagingUseCase(dataRepository: DataRepository): GetAllClientsPagingUseCase {
        return GetAllClientsPagingUseCase(dataRepository)
    }

    @Provides
    fun provideSaveClientUserCase(dataRepository: DataRepository): SaveClientUseCase {
        return SaveClientUseCase(dataRepository)
    }

    @Provides
    fun provideSaveNewCreditUserCase(dataRepository: DataRepository): SaveNewCreditUseCase {
        return SaveNewCreditUseCase(dataRepository)
    }

    @Provides
    fun provideGetClientById(dataRepository: DataRepository): GetClientById {
        return GetClientById(dataRepository)
    }

    @Provides
    fun provideGetRoutesUseCase(routesRepository: RoutesRepository): GetRoutesUseCase {
        return GetRoutesUseCase(routesRepository)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginEmailUseCase {
        return LoginEmailUseCase(authRepository)
    }

    @Provides
    fun provideGetProfileUserAppUseCase(userRepository: UserRepository): GetProfileUserUseCase {
        return GetProfileUserUseCase(userRepository)
    }

    @Provides
    fun provideRecoverPasswordUseCase(authRepository: AuthRepository): RecoverPasswordUseCase {
        return RecoverPasswordUseCase(authRepository)
    }

    @Provides
    fun provideSignInUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): SignInEmailUseCase {
        return SignInEmailUseCase(authRepository, userRepository)
    }

    @Provides
    fun provideGetCreditClientUseCase(dataRepository: DataRepository): GetCreditClientUseCase {
        return GetCreditClientUseCase(dataRepository)
    }

    @Provides
    fun provideGetQuotasUseCase(dataRepository: DataRepository): GetQuotasUseCase {
        return GetQuotasUseCase(dataRepository)
    }

    @Provides
    fun provideSaveQuotaOfCreditClientUseCase(dataRepository: DataRepository): SaveQuotaOfCreditClientUseCase {
        return SaveQuotaOfCreditClientUseCase(dataRepository)
    }

    // User case Authentication
    @Provides
    fun provideGetFirebaseAuth(authRepository: AuthRepository): GetAuthState {
        return GetAuthState(authRepository)
    }

    @Provides
    fun provideIsUserAuthUseCase(authRepository: AuthRepository): IsUserAuthenticatedUseCase {
        return IsUserAuthenticatedUseCase(authRepository)
    }

    @Provides
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository)
    }

    @Provides
    fun providesSingInAnonymouslyUseCase(authRepository: AuthRepository): SingInAnonymouslyUseCase {
        return SingInAnonymouslyUseCase(authRepository)
    }
}