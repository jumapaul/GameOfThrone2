package com.jerimkaura.got.presentation.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.got.data.local.entities.Character
import com.jerimkaura.got.databinding.CharacterItemBinding
import com.squareup.picasso.Picasso

class CharactersAdapter :
    ListAdapter<Character, CharactersAdapter.CharactersViewHolder>(CharacterComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CharactersViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                Picasso.get().load(character.imageUrl).into(binding.characterImage)
                characterName.text = character.firstName
                tvCharacterFullName.text = character.fullName
                root.setOnClickListener { view ->
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToSingleCharacterFragment(character.id)
                    view.findNavController().navigate(action)
                }
            }

        }
    }

    class CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }
}