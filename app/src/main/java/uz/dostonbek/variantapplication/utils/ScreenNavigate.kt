package uz.dostonbek.variantapplication.utils

import androidx.navigation.NavController
import uz.dostonbek.variantapplication.R

class ScreenNavigate(
    private val navController: NavController
) {
    fun createLockView(){
        navController.navigate(R.id.lockFragment)
    }
}