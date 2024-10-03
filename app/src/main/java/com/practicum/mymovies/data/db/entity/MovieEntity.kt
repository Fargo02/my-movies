package com.practicum.mymovies.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.mymovies.data.db.entity.MovieEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
) {
    companion object {
        const val TABLE_NAME = "movie_table"
    }
}
