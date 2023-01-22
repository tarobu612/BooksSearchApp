package com.tarobu612.bookssearchapp.ui.bookdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tarobu612.bookssearchapp.databinding.BookDetailFragmentBinding

class BookDetailFragment : Fragment(), BookDetailContract.View {
    private var _binding: BookDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: BookDetailPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = BookDetailPresenter(
            output = this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.viewDestroyed()
    }

}
