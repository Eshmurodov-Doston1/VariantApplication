package uz.dostonbek.variantapplication.adapters.uploadAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import uz.dostonbek.variantapplication.BuildConfig.BASE_URL
import uz.dostonbek.variantapplication.databinding.ItemUploadBinding
import uz.dostonbek.variantapplication.models.uploadPhotos.UploadPhotos

class UploadAdapter(var onItemLongClick: OnIemLongClick):ListAdapter<UploadPhotos, UploadAdapter.Vh>(
    MyDiffUtil()
) {
    inner class Vh(var itemUploadBinding: ItemUploadBinding):RecyclerView.ViewHolder(itemUploadBinding.root){
        fun onBind(uploadPhotos: UploadPhotos,position: Int){
            Log.e("LinkData", "$BASE_URL/${uploadPhotos.file_link}" )
            itemUploadBinding.image.load("$BASE_URL/${uploadPhotos.file_link}"){
                crossfade(true)
                crossfade(400)
                transformations(RoundedCornersTransformation(30f))
            }
            itemUploadBinding.image.setOnClickListener {
                onItemLongClick.onUploadClick(uploadPhotos,position)
            }
        }
    }


    class MyDiffUtil:DiffUtil.ItemCallback<UploadPhotos>(){
        override fun areItemsTheSame(oldItem: UploadPhotos, newItem: UploadPhotos): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UploadPhotos, newItem: UploadPhotos): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUploadBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    interface OnIemLongClick{
        fun onUploadClick(uploadPhotos: UploadPhotos,position: Int)
    }
}