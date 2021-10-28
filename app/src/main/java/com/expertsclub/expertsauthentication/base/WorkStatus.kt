package com.expertsclub.expertsauthentication.base

sealed class WorkStatus {
    object WorkStarted : WorkStatus()
    object WorkSuccess : WorkStatus()
    class WorkError(val throwable: Throwable) : WorkStatus()
}