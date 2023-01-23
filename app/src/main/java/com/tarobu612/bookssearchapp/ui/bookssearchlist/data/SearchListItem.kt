package com.tarobu612.bookssearchapp.ui.bookssearchlist.data

data class SearchListItem(
    val title: String,
    val subtitle: String,
    val authors: List<String>,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val printType: PrintType,
    val reviewAverage: Long,
    val reviewCount: Int,
    val thumbnail: String,
    val language: BookLanguage,
    val previewLink: String,
    val infoLink: String
)
