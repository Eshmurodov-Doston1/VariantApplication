package uz.dostonbek.variantapplication.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.dostonbek.variantapplication.ui.mainView.view.chat.ChatListFragment
import uz.dostonbek.variantapplication.ui.mainView.view.settings.SettingsFragment
import uz.dostonbek.variantapplication.ui.mainView.view.statement.StateMentFragment
import uz.dostonbek.variantapplication.utils.AppConstant.ONE
import uz.dostonbek.variantapplication.utils.AppConstant.THREE
import uz.dostonbek.variantapplication.utils.AppConstant.TWO
import uz.dostonbek.variantapplication.utils.AppConstant.ZERO

class MainViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return THREE
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            ZERO->{
                StateMentFragment()
            }
            ONE->{
                ChatListFragment()
            }
            TWO->{
                SettingsFragment()
            }
            else->{
                StateMentFragment()
            }
        }
    }
}