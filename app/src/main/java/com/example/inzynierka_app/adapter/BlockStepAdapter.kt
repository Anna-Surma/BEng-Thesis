package com.example.inzynierka_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka_app.databinding.StepItemBinding
import com.example.inzynierka_app.model.StepItem

class BlockStepAdapter : RecyclerView.Adapter<BlockStepAdapter.StepItemViewHolder>() {
    var data = mutableListOf<StepItem>()
    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : StepItemViewHolder = StepItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: StepItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class StepItemViewHolder(private val binding: StepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): StepItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StepItemBinding.inflate(layoutInflater, parent, false)
                return StepItemViewHolder(binding)
            }
        }

        fun bind(item: StepItem) {
            binding.step = item
        }
    }

    fun clearItems(){
        data.clear()
        notifyDataSetChanged()
    }
}