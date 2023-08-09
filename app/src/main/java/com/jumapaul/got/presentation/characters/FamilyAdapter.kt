package com.jerimkaura.got.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.got.databinding.FamilyItemBinding

class FamilyAdapter : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {
    private val families = listOf(
        "Sparrow",
        "Viper",
        "Lorath",
        "Free Folk",
        "House Targaryen",
        "House Tarly",
        "House Stark",
        "House Baratheon",
        "House Lannister",
        "House Lannister",
        "House Clegane"
    )

    inner class FamilyViewHolder(private val binding: FamilyItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(item: String) {
                binding.tvFamilyName.text = item
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val binding = FamilyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FamilyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val family = families[position]
        holder.bind(family)
    }

    override fun getItemCount(): Int {
        return families.size
    }
}