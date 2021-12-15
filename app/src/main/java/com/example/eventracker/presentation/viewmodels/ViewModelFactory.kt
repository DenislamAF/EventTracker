package com.example.eventracker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.domain.usecases.*

class ViewModelFactory: ViewModelProvider.Factory{

    private val generalRepository by lazy {
        GeneralRepositoryImpl()
    }

    private val createEventUseCase by lazy {
        CreateEventUseCase(generalRepository = generalRepository)
    }

    private val deleteEventUseCase by lazy {
        DeleteEventUseCase(generalRepository = generalRepository)
    }

    private val getFirebaseInfoUseCase by lazy {
        GetFirebaseInfoUseCase(generalRepository = generalRepository)
    }

    private val getUserAccUseCase by lazy {
        GetUserAccUseCase(generalRepository = generalRepository)
    }

    private val getUserDatabaseUseCase by lazy {
        GetUserDatabaseUseCase(generalRepository = generalRepository)
    }

    private val loginUseCase by lazy {
        LogInUseCase(generalRepository = generalRepository)
    }

    private val registrationUseCase by lazy {
        RegistrationUseCase(generalRepository = generalRepository)
    }

    private val logOutUseCase by lazy {
        LogOutUseCase(generalRepository = generalRepository)
    }

    private val getEventByKeyUseCase by lazy {
        GetEventByKeyUseCase(generalRepository = generalRepository)
    }

    private val deleteInviteUseCase by lazy {
        DeleteInviteUseCase(generalRepository = generalRepository)
    }

    private val addInviteToEventsUseCase by lazy {
        AddInviteToEventsUseCase(generalRepository = generalRepository)
    }

    private val sendInviteUseCase by lazy {
        SendInviteUseCase(generalRepository = generalRepository)
    }

    private val searchUseCase by lazy {
        SearchUseCase(generalRepository = generalRepository)
    }



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)){
            return LoginFragmentViewModel(
                logInUseCase = loginUseCase,
                getUserAccUseCase = getUserAccUseCase,
                getFirebaseInfoUseCase = getFirebaseInfoUseCase) as T
        }
        if (modelClass.isAssignableFrom(AddEventFragmentViewModel::class.java)){
            return AddEventFragmentViewModel(
                createEventUseCase = createEventUseCase,
                getFirebaseInfoUseCase = getFirebaseInfoUseCase) as T
        }
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)){
            return MainFragmentViewModel(
                getUserDatabaseUseCase = getUserDatabaseUseCase,
                deleteEventUseCase = deleteEventUseCase,
                getUserAccUseCase = getUserAccUseCase,
                logOutUseCase = logOutUseCase) as T
        }
        if (modelClass.isAssignableFrom(RegistrationFragmentViewModel::class.java)){
            return RegistrationFragmentViewModel(
                registrationUseCase = registrationUseCase,
                getFirebaseInfoUseCase = getFirebaseInfoUseCase,
                getUserAccUseCase = getUserAccUseCase) as T
        }
        if (modelClass.isAssignableFrom(DetailedEventFragmentViewModel::class.java)){
            return DetailedEventFragmentViewModel(
                getUserDatabaseUseCase = getUserDatabaseUseCase,
                getEventByKeyUseCase = getEventByKeyUseCase) as T
        }
        if (modelClass.isAssignableFrom(InvitationFragmentViewModel::class.java)) {
            return InvitationFragmentViewModel(
                getUserDatabaseUseCase = getUserDatabaseUseCase,
                getUserAccUseCase = getUserAccUseCase,
                logOutUseCase = logOutUseCase,
                deleteInviteUseCase = deleteInviteUseCase,
                addInviteToEventsUseCase = addInviteToEventsUseCase
            ) as T
        }
        if (modelClass.isAssignableFrom(MapFragmentViewModel::class.java)){
            return MapFragmentViewModel(
                createEventUseCase = createEventUseCase,
                getFirebaseInfoUseCase = getFirebaseInfoUseCase) as T
        }
        if (modelClass.isAssignableFrom(SendInviteFragmentViewModel::class.java)) {
            return SendInviteFragmentViewModel(
                sendInviteUseCase = sendInviteUseCase,
                searchUseCase = searchUseCase,
                getUserDatabaseUseCase = getUserDatabaseUseCase,
                getEventByKeyUseCase = getEventByKeyUseCase
            ) as T
        }
        throw IllegalAccessException("ViewModel class is not found")
    }
}