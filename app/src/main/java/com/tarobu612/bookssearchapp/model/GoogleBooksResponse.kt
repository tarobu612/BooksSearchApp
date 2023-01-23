package com.tarobu612.bookssearchapp.model

import com.squareup.moshi.Json

// 公式を詳細に見ていないのでnonNull, nullableは適当
data class GoogleBooksResponse(
    @Json(name = "kind")
    val kind: String,

    @Json(name = "items")
    val items: List<Item>?,

    @Json(name = "total_items")
    val totalItems: Int
) {
    data class Item(
        @Json(name = "kind")
        val kind: String,

        @Json(name = "id")
        val id: String,

        @Json(name = "etag")
        val eTag: String?,

        @Json(name = "selfLink")
        val selfLink: String,

        @Json(name = "volumeInfo")
        val volumeInfo: VolumeInfo
    )

    data class VolumeInfo(
        @Json(name = "title")
        val title: String?,

        @Json(name = "subtitle")
        val subtitle: String?,

        @Json(name = "authors")
        val authors: List<String>?,

        @Json(name = "publishedDate")
        val publishedDate: String?,

        @Json(name = "description")
        val description: String?,

        @Json(name = "pageCount")
        val pageCount: Int,

        @Json(name = "printType")
        val printType: String?,

        @Json(name = "averageRating")
        val averageRating: Long,

        @Json(name = "ratingsCount")
        val ratingsCount: Int,

        @Json(name = "imageLinks")
        val imageLinks: ImageLinks?,

        @Json(name = "language")
        val language: String?,

        @Json(name = "previewLink")
        val previewLink: String?,

        @Json(name = "infoLink")
        val infoLink: String?
    )

    // 画像urlがhttpなのでManifestでhttp通信許可している
    // android:usesCleartextTraffic="true"
    data class ImageLinks(
        @Json(name = "smallThumbnail")
        val smallThumbnail: String?,

        @Json(name = "thumbnail")
        val thumbnail: String?
    )

}
