package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AdapterDataItemBinding
import com.example.myapplication.model.DataModel

/**
 * Created by Deshraj Sharma on 11-03-2023.
 */
class DataAdapter(private val data: List<DataModel>?, private val listener: (DataModel?) -> Unit) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterDataItemBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data?.get(position))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    inner class DataViewHolder(private val binding: AdapterDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DataModel?) {
            binding.item = model
            binding.root.setOnClickListener { listener(model) }
            binding.executePendingBindings()
        }
    }
}