package uz.dostonbek.variantapplication.adapters.stateMentAdapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.databinding.ItemMainBinding
import uz.dostonbek.variantapplication.models.getApplications.DataApplication

class StatementAdapter(var context:Context,var onItemClickListener: OnItemClickListener):ListAdapter<DataApplication, StatementAdapter.Vh>(
    MyDiffUtil()
) {
    inner class Vh(var itemMainBinding: ItemMainBinding):RecyclerView.ViewHolder(itemMainBinding.root){
        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(dataApplication: DataApplication, position: Int){
            itemMainBinding.name.text = dataApplication.full_name
            itemMainBinding.number.text = dataApplication.contract_number
            if (dataApplication.status_title?.isNotEmpty() == true){
            itemMainBinding.categoryName.text = dataApplication.status_title
            }else{
                itemMainBinding.cardBtn.visibility = View.GONE
            }

            when(dataApplication.photo_status){
                1L->{
                    itemMainBinding.cardBtn.setCardBackgroundColor(context.getColor(R.color.status0))
                }
                2L->{
                    itemMainBinding.cardBtn.setCardBackgroundColor(context.getColor(R.color.status1))
                }
                3L->{
                    itemMainBinding.cardBtn.setCardBackgroundColor(context.getColor(R.color.status2))
                }
            }

            itemMainBinding.card.setOnClickListener {
                onItemClickListener.onItemClick(dataApplication,position)
            }
        }
    }

    class MyDiffUtil:DiffUtil.ItemCallback<DataApplication>(){
        override fun areItemsTheSame(oldItem: DataApplication, newItem: DataApplication): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DataApplication,
            newItem: DataApplication
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    interface OnItemClickListener{
        fun onItemClick(dataApplication: DataApplication,position: Int)
    }
}