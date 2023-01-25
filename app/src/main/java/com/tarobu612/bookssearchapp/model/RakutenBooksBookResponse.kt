package com.tarobu612.bookssearchapp.model

import com.squareup.moshi.Json

// 公式を詳細に見ていないのでnonNull, nullableは適当
data class RakutenBooksBookResponse(
    @Json(name = "GenreInformation")
    val GenreInformation: List<String>?,

    @field:Json(name = "Items")
    val items: List<ItemInfo>?,

    @Json(name = "carrier")
    val carrier: Int?,

    @Json(name = "count")
    val count: Int?,

    @Json(name = "first")
    val first: Int?,

    @Json(name = "hits")
    val hits: Int?,

    @Json(name = "last")
    val last: Int?,

    @Json(name = "page")
    val page: Int?,

    @Json(name = "pageCount")
    val pageCount: Int?
) {
    data class ItemInfo(
        @field:Json(name = "Item")
        val item: Item?
    )

    data class Item(
        @Json(name = "title")
        val title: String?,

        @Json(name = "titleKana")
        val titleKana: String?,

        @Json(name = "subTitle")
        val subTitle: String?,

        @Json(name = "subTitleKana")
        val subTitleKana: String?,

        @Json(name = "seriesName")
        val seriesName: String?,

        @Json(name = "seriesNameKana")
        val seriesNameKana: String?,

        @Json(name = "contents")
        val contents: String?,

        @Json(name = "author")
        val author: String?,

        @Json(name = "authorKana")
        val authorKana: String?,

        @Json(name = "publisherName")
        val publisherName: String?,

        @Json(name = "size")
        val size: String?,

        @Json(name = "isbn")
        val isbn: String?,

        @Json(name = "itemCaption")
        val itemCaption: String?,

        @Json(name = "salesDate")
        val salesDate: String?,

        @Json(name = "itemPrice")
        val itemPrice: Int?,

        @Json(name = "listPrice")
        val listPrice: Int?,

        @Json(name = "discountRate")
        val discountRate: Int?,

        @Json(name = "discountPrice")
        val discountPrice: Int?,

        @Json(name = "itemUrl")
        val itemUrl: String?,

        @Json(name = "affiliateUrl")
        val affiliateUrl: String?,

        @Json(name = "smallImageUrl")
        val smallImageUrl: String?,

        @Json(name = "mediumImageUrl")
        val mediumImageUrl: String?,

        @Json(name = "largeImageUrl")
        val largeImageUrl: String?,

        @Json(name = "chirayomiUrl")
        val chirayomiUrl: String?,

        @Json(name = "availability")
        val availability: String?,

        @Json(name = "postageFlag")
        val postageFlag: Int?,

        @Json(name = "limitedFlag")
        val limitedFlag: Int?,

        @Json(name = "reviewCount")
        val reviewCount: Int?,

        @Json(name = "reviewAverage")
        val reviewAverage: String?,

        @Json(name = "booksGenreId")
        val booksGenreId: String?
    )
}