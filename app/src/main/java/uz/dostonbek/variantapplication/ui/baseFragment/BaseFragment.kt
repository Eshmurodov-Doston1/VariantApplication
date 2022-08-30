package uz.dostonbek.variantapplication.ui.baseFragment

import android.content.Context
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import uz.dostonbek.variantapplication.ListenerActivity
import uz.dostonbek.variantapplication.MainActivity
import uz.dostonbek.variantapplication.utils.container.AppCompositionRoot
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment(layout_id:Int) : Fragment(layout_id),CoroutineScope {
    lateinit var listenerActivity: ListenerActivity
    val compositionRoot: AppCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerActivity = activity as ListenerActivity
    }


    fun imageList():List<String>{
        var loadImage = ArrayList<String>()
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        loadImage.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Uzbek_passport.svg/1442px-Uzbek_passport.svg.png")
        return loadImage
    }

}