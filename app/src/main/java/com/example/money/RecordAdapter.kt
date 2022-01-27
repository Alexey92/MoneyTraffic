package com.example.money

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.money.databinding.RecordItemBinding
import java.util.ArrayList

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.RecordHolder>() {

    val recordList = ArrayList<Record>()

    class RecordHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = RecordItemBinding.bind(item)
        fun bind(record: Record) = with(binding){
            when(record.category){
               ItemCategory.Car.Value ->  {
                   image.setImageResource(R.drawable.car)
                   textCategory.text = "Авто"
               }
                ItemCategory.Food.Value ->  {
                    image.setImageResource(R.drawable.food)
                    textCategory.text = "Еда"
                }
                ItemCategory.Home.Value ->  {
                    image.setImageResource(R.drawable.home)
                    textCategory.text = "Дом"
                }
                ItemCategory.Income.Value ->  {
                image.setImageResource(R.drawable.income)
                textCategory.text = "Доход"
            }
            }

            textData.text = "27.01.2022"
            textSum.text = record.sum.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)

        return RecordHolder(view)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        holder.bind(recordList[position])
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    fun addRecord (record: Record){
        recordList.add(record)
        notifyDataSetChanged()
    }
}