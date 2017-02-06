package io.github.muhrifqii.maos.viewmodels

import io.github.muhrifqii.maos.libs.ActivityViewModel
import io.github.muhrifqii.maos.libs.ViewModelParams
import io.github.muhrifqii.maos.ui.activities.MainActivity
import io.github.muhrifqii.maos.viewmodels.events.MainViewModelEvent

/**
 * Created on   : 04/02/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
class MainViewModel(params: ViewModelParams) : ActivityViewModel<MainActivity>(params),
    MainViewModelEvent.Cause, MainViewModelEvent.Result {


}