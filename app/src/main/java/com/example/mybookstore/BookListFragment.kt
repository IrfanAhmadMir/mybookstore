package com.example.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter

    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(BookRepository(BookDatabase.getInstance(requireContext()).bookDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = BookAdapter(mutableListOf(), { book ->
            val fragment = BookDetailFragment.newInstance(book.bookId)
            (activity as MainActivity).replaceFragment(fragment)
        }, bookViewModel)
        recyclerView.adapter = adapter

        view.findViewById<ImageView>(R.id.icon_one).setOnClickListener {
            val fragment = BookDetailFragment.newInstance(null)
            (activity as MainActivity).replaceFragment(fragment)
        }

        bookViewModel.books.observe(viewLifecycleOwner, Observer { books ->
            books?.let {
                adapter.updateBooks(it)
            }
        })

        return view
    }
}
