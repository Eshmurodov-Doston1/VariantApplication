package uz.dostonbek.variantapplication.adapters.imageViewPagerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.dostonbek.variantapplication.BuildConfig.BASE_URL
import uz.dostonbek.variantapplication.databinding.ItemViewPagerBinding
import uz.dostonbek.variantapplication.models.uploadPhotos.UploadPhotos


class ViewPagerAdapter(var onItemClickLIstener: OnItemClickListener, var list: List<UploadPhotos>):RecyclerView.Adapter<ViewPagerAdapter.Vh>() {
    inner class Vh(var itemViewPagerBinding: ItemViewPagerBinding):RecyclerView.ViewHolder(itemViewPagerBinding.root){
        fun onBind(uploadPhotos: UploadPhotos,position: Int){
            itemViewPagerBinding.image.load("$BASE_URL/${uploadPhotos.file_link}")
            itemViewPagerBinding.update.setOnClickListener {
                onItemClickLIstener.onItemClickUpdate(uploadPhotos,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClickUpdate(uploadPhotos: UploadPhotos,position: Int)
    }
}