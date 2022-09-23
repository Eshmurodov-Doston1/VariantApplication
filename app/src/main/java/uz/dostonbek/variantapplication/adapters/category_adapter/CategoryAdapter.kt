package uz.dostonbek.variantapplication.adapters.category_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.dostonbek.variantapplication.databinding.ItemCreateRequestBinding
import uz.dostonbek.variantapplication.models.uploaCategory.UploadCategoryItem
import uz.dostonbek.variantapplication.models.uploadPhotos.UploadPhotos
import uz.dostonbek.variantapplication.utils.visible

class CategoryAdapter(
    var listCategory:ArrayList<UploadCategoryItem>,
    private val onItemClickListener: OnItemClickListener)
    :RecyclerView.Adapter<CategoryAdapter.Vh>() {
    inner class Vh(var itemCreateRequestBinding: ItemCreateRequestBinding):RecyclerView.ViewHolder(itemCreateRequestBinding.root){
        fun onBind(uploadCategoryItem: UploadCategoryItem, position: Int){
            itemCreateRequestBinding.title.text = uploadCategoryItem.title
            if(uploadCategoryItem.is_uploaded==1){
                itemCreateRequestBinding.checkIcon.visible()
            }
            itemCreateRequestBinding.card.setOnClickListener {
                if (uploadCategoryItem.is_uploaded!=1){
                    onItemClickListener.onItemClick(uploadCategoryItem,position)
                }else{
                    onItemClickListener.onItemClick(UploadCategoryItem(-2,-2,1,"Этот раздел уже загружен"),position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCreateRequestBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listCategory[position],position)
    }

    override fun getItemCount(): Int {
     return listCategory.size
    }


    interface OnItemClickListener{
        fun onItemClick(item: UploadCategoryItem, position:Int)
    }
}