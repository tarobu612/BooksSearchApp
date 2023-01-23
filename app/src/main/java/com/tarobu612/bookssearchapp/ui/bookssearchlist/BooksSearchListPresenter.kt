package com.tarobu612.bookssearchapp.ui.bookssearchlist

import com.tarobu612.bookssearchapp.model.GoogleBooksResponse
import com.tarobu612.bookssearchapp.model.ResponseResult
import com.tarobu612.bookssearchapp.repository.GoogleBooksRepository
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.BookLanguage
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.PrintType
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BooksSearchListPresenter(
    private val output: BooksSearchListContract.View,
    private val googleBooksRepository: GoogleBooksRepository,
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

    override fun startSearch(word: String) = launch {
        output.showLoading()

        when (val response = googleBooksRepository.getBooksByKeyword(word)) {
            is ResponseResult.Success -> {
                val list = response.value.items?.map { item ->
                    getSearchListItems(item.volumeInfo)
                } ?: return@launch

                output.showBooksList(list)
            }
            is ResponseResult.Failure -> {
                output.showNoBooksList()
            }
        }

        output.hideLoading()
    }

    private fun getSearchListItems(volume: GoogleBooksResponse.VolumeInfo) = SearchListItem(
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

}
