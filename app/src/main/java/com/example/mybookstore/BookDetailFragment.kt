package com.example.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class BookDetailFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(BookRepository(BookDatabase.getInstance(requireContext()).bookDao()))
    }
    private var bookId: Int? = null

    companion object {
        private const val ARG_BOOK_ID = "book_id"

        fun newInstance(bookId: Int?): BookDetailFragment {
            val fragment = BookDetailFragment()
            val args = Bundle()
            args.putInt(ARG_BOOK_ID, bookId ?: -1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_detail, container, false)
        titleEditText = view.findViewById(R.id.titleEditText)
        authorEditText = view.findViewById(R.id.authorEditText)
        bookId = arguments?.getInt(ARG_BOOK_ID)?.takeIf { it != -1 }

        viewLifecycleOwner.lifecycleScope.launch {
            bookId?.let { id ->
                bookViewModel.loadBook(id)
            }
        }

        bookViewModel.book.observe(viewLifecycleOwner, Observer { book ->
            book?.let {
                titleEditText.setText(it.title)
                authorEditText.setText(it.author)
            }
        })

        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveBook()
        }

        return view
    }

    private fun saveBook() {
        val title = titleEditText.text.toString()
        val author = authorEditText.text.toString()

        if (bookId == null) {
            val newBook = Book(0, title, author)
            bookViewModel.addBook(newBook)
        } else {
            val book = bookViewModel.book.value
            book?.let {
                val updatedBook = it.copy(title = title, author = author)
                bookViewModel.updateBook(updatedBook)
            }
        }

        (activity as MainActivity).replaceFragment(BookListFragment())
    }
}
