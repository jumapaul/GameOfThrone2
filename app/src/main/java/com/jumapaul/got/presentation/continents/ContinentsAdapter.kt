package com.jerimkaura.got.presentation.continents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.got.R
import com.jerimkaura.got.data.local.entities.Continent
import com.jerimkaura.got.databinding.ContinentItemBinding
import com.squareup.picasso.Picasso

class ContinentsAdapter: ListAdapter<Continent, ContinentsAdapter.ContinentViewHolder>(ContinentComparator()) {

     private val continentImages = listOf(
        R.drawable.continent10,
        R.drawable.continent1,
        R.drawable.continent3,
        R.drawable.continent4,
        R.drawable.continent5,
        R.drawable.continent12,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContinentViewHolder {
        val binding = ContinentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContinentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContinentViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, continentImages)
        }
    }

    class ContinentViewHolder(private val binding: ContinentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(continent: Continent, continentImages: List<Int> ) {
            binding.apply {
                val imageId = (Math.random() * continentImages.size).toInt()
                continentName.text = continent.name
                Picasso.get().load(continentImages[imageId]).into(binding.continentImage)
            }
        }
    }

    class ContinentComparator : DiffUtil.ItemCallback<Continent>() {
        override fun areItemsTheSame(oldItem: Continent, newItem: Continent) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Continent, newItem: Continent) =
            oldItem == newItem
    }
}