package com.practicum.mymovies.ui.names

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.Person

class NamesAdapter() :
    RecyclerView.Adapter<NamesViewHolder>() {

    var people = ArrayList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder =
        NamesViewHolder(parent)

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        holder.bind(people[position])
    }

    override fun getItemCount(): Int = people.size

}