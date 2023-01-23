package com.tarobu612.bookssearchapp.ui.bookssearchlist

import com.tarobu612.bookssearchapp.model.GoogleBooksResponse
import com.tarobu612.bookssearchapp.model.RakutenBooksBookResponse
import com.tarobu612.bookssearchapp.model.ResponseResult
import com.tarobu612.bookssearchapp.repository.GoogleBooksRepository
import com.tarobu612.bookssearchapp.repository.RakutenBooksBookRepository
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.BookLanguage
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.PrintType
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchTabType
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BooksSearchListPresenter(
    private val output: BooksSearchListContract.View,
    private val googleBooksRepository: GoogleBooksRepository,
    private val rakutenBooksBookRepository: RakutenBooksBookRepository,
    private val job: Job = Job()
) : BooksSearchListContract.Presenter, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun start() {
        // empty
    }

    override fun viewDestroyed() {
        coroutineContext.cancel()
        output.hideLoading()
    }

    override fun startSearch(word: String, currentTab: SearchTabType) = launch {
        output.showLoading()

        val response = when (currentTab) {
            SearchTabType.GOOGLE -> googleBooksRepository.getBooksByKeyword(word)
            SearchTabType.RAKUTEN -> rakutenBooksBookRepository.getBooksByTitle(word)
        }

        if (response is ResponseResult.Failure) {
            output.showNoBooksList()
            output.hideLoading()
            return@launch
        }

        val successResponse = response as ResponseResult.Success
        val list = when (val body = successResponse.value) {
            is GoogleBooksResponse -> {
                body.items?.map { item ->
                    getSearchListItemFromGoogleResponse(item.volumeInfo)
                }
            }
            is RakutenBooksBookResponse -> {
                body.items?.mapNotNull {
                    if (it.item != null) getSearchListItemFromRakutenResponse(it.item) else null
                }
            }
            else -> throw Exception("not expected case")
        }

        if (list.isNullOrEmpty()) {
            output.showNoBooksList()
        } else {
            output.showBooksList(list)
        }

        output.hideLoading()
    }

    private fun getSearchListItemFromGoogleResponse(volume: GoogleBooksResponse.VolumeInfo) =
        SearchListItem(
            title = volume.title ?: "no title",
            subtitle = volume.subtitle ?: "no subtitle",
            authors = volume.authors ?: listOf("no authors"),
            publishedDate = volume.publishedDate ?: "no publishedDate",
            description = volume.description ?: "no description",
            pageCount = volume.pageCount,
            printType = when (volume.printType) {
                "BOOK" -> PrintType.BOOK
                else -> PrintType.ELSE  // TODO 仮
            },
            reviewAverage = volume.averageRating,
            reviewCount = volume.ratingsCount,
            thumbnail = volume.imageLinks?.thumbnail
                ?: "https://www.shoshinsha-design.com/wp-content/uploads/2020/05/noimage-760x460.png",
            language = when (volume.language) {
                "ja" -> BookLanguage.JAPANESE
                else -> BookLanguage.ENGLISH  // TODO 仮
            },
            previewLink = volume.previewLink ?: "no previewLink",
            infoLink = volume.infoLink ?: "no infoLink"
        )

    private fun getSearchListItemFromRakutenResponse(item: RakutenBooksBookResponse.Item) =
        SearchListItem(
            title = item.title ?: "no title",
            subtitle = item.subTitle ?: "no subtitle",
            authors = listOf(item.author ?: "-"),   // TODO 仮
            publishedDate = item.salesDate ?: "no publishedDate",
            description = item.contents ?: "no description",
            pageCount = 0,  // TODO ない
            reviewAverage = item.reviewAverage?.toLongOrNull() ?: 0,
            reviewCount = item.reviewCount ?: 0,
            thumbnail = item.mediumImageUrl
                ?: "https://www.shoshinsha-design.com/wp-content/uploads/2020/05/noimage-760x460.png",
            infoLink = item.itemUrl ?: "no infoLink"
        )

}
