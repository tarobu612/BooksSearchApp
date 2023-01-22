package com.tarobu612.bookssearchapp.ui.bookdetail

import com.tarobu612.bookssearchapp.ui.BasePresenter
import com.tarobu612.bookssearchapp.ui.BaseView

interface BookDetailContract {
    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }
}