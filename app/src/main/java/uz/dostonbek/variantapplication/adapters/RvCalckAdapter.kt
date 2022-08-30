package uz.dostonbek.variantapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.databinding.ItemLockBinding
import uz.gxteam.variant.utils.AppConstant.ELEVENT
import uz.gxteam.variant.utils.AppConstant.NINE

class RvCalckAdapter(var onItemClickListener: OnItemClickListener, var listNumber:List<String>):RecyclerView.Adapter<RvCalckAdapter.Vh>() {
    inner class Vh(var itemLockBinding: ItemLockBinding):RecyclerView.ViewHolder(itemLockBinding.root){
        fun onBind(str:String,position: Int){
            itemLockBinding.name.text = str
            when(position){
                NINE->{
                    itemLockBinding.image.visibility = View.VISIBLE
                    itemLockBinding.cardNumber.visibility = View.GONE
                    itemLockBinding.image.setOnClickListener {
                        onItemClickListener.bioMetrickClick(position)
                    }
                }
                ELEVENT->{
                    itemLockBinding.image.visibility = View.VISIBLE
                    itemLockBinding.cardNumber.visibility = View.GONE
                    itemLockBinding.image.setImageResource(R.drawable.ic_delete)

                    itemLockBinding.image.setOnClickListener {
                        onItemClickListener.onItemClickDelete(position)
                    }
                }
                else->{
                    itemLockBinding.image.visibility = View.GONE
                    itemLockBinding.cardNumber.visibility = View.VISIBLE
                }
            }
            itemLockBinding.cardNumber.setOnClickListener {
                onItemClickListener.onItemClick(str,position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemLockBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listNumber[position],position)
    }

    override fun getItemCount(): Int {
       return listNumber.size
    }

    interface OnItemClickListener{
        fun onItemClick(str:String,position: Int)
        fun onItemClickDelete(position: Int)
        fun bioMetrickClick(position: Int)
    }
}