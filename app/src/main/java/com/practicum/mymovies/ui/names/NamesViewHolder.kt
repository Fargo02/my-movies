package com.practicum.mymovies.ui.names

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.mymovies.R
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.Person

class NamesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_name, parent, false)
) {

    private var cover: ImageView = itemView.findViewById(R.id.cover)
    private var title: TextView = itemView.findViewById(R.id.title)
    private var description: TextView = itemView.findViewById(R.id.description)

    fun bind(person: Person) {
        Glide.with(itemView)
            .load(person.image)
            .placeholder(R.drawable.ic_person)
            .circleCrop()
            .into(cover)

        title.text = person.title
        description.text = person.description
    }
}