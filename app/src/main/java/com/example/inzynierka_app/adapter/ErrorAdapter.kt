package com.example.inzynierka_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka_app.databinding.GripperErrorItemBinding
import com.example.inzynierka_app.db.GripperError

class ErrorAdapter : RecyclerView.Adapter<ErrorAdapter.ErrorViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<GripperError>() {
        override fun areItemsTheSame(oldItem: GripperError, newItem: GripperError): Boolean {
            return oldItem.errorId == newItem.errorId
        }

        override fun areContentsTheSame(oldItem: GripperError, newItem: GripperError): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<GripperError>) = differ.submitList(list)

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ErrorViewHolder = ErrorViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    class ErrorViewHolder(private val binding: GripperErrorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ErrorViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GripperErrorItemBinding.inflate(layoutInflater, parent, false)
                return ErrorViewHolder(binding)
            }
        }

        fun bind(item: GripperError) {
            binding.error = item
        }
    }
}