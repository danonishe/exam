package com.example.exam
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.databinding.ItemBinding

class RecycleAdapter(private val List : ArrayList<RecycleItem>, val listener : Listener) : RecyclerView.Adapter<RecycleAdapter.ItemHolder>() {

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)
        fun bind(item: RecycleItem, listener : Listener) = with(binding){
            //Заполнение элемента RecycleView
            name.text = item.name
            cost.text = item.cost.toString()
            client.text = item.client
            //Обработка редактирования записи
            button.setOnClickListener{
                listener.OnClick(item)
            }
            //Обработка удаления записи
            button2.setOnClickListener{
                listener.OnClickDelete(item)
            }
            //Функции редактирования и удаления направляются в MainActivity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(List[position], listener)
    }

    interface Listener{
        fun OnClick(recItem : RecycleItem)
        fun OnClickDelete(recItem : RecycleItem)
    }

    open fun update(){
        notifyDataSetChanged()
    }

}