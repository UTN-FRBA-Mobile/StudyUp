package com.studyup.classes.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studyup.databinding.CardCellBinding

class CardAdapter(private var teams: List<Team>, private val onItemClick: (Team) -> Unit): RecyclerView.Adapter<CardViewHolder>() {
    override fun getItemCount(): Int = teams.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)

        return CardViewHolder(binding) {
            onItemClick(teams[it])
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindTeam(teams[position])
    }

    fun filterList(filteredList: List<Team>) {
        teams = filteredList
        notifyDataSetChanged()
    }
}