package com.tarobu612.bookssearchapp.ui.bookssearchlist

import com.tarobu612.bookssearchapp.ui.BasePresenter
import com.tarobu612.bookssearchapp.ui.BaseView
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchTabType
import kotlinx.coroutines.Job

interface BooksSearchListContract {
    interface Presenter : BasePresenter {
        fun startSearch(word: String, currentTab: SearchTabType): Job
    }

    interface View : BaseView<Presenter> {
        fun showBooksList(data: List<SearchListItem>)

        fun showNoBooksList()

        fun showLoading()

        fun hideLoading()
    }

}
