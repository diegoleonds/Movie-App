package com.example.moviesapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapi.R
import com.example.moviesapi.data.model.Result
import com.example.moviesapi.ui.adapter.AdapterClick
import com.example.moviesapi.ui.adapter.TopRatedMoviesAdapter
import com.example.moviesapi.ui.model.Movie
import com.example.moviesapi.ui.util.ImgLoader
import com.example.moviesapi.ui.viewmodel.MovieListViewModel
import com.example.moviesapi.ui.viewmodel.MovieSharedViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Show a top rated list or a message for http errors with a try again button
 */
class MovieListFragment : Fragment() {

    val activityViewModel: MovieSharedViewModel by activityViewModels()
    val viewModel: MovieListViewModel by viewModel()

    lateinit var moviesList: RecyclerView
    lateinit var searchEditTxt: TextInputEditText
    lateinit var searchIcon: ImageView
    lateinit var adapter: TopRatedMoviesAdapter
    lateinit var errorIncludeView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        initAdapter(view)
        setRecyclerViewAdapter()
        observeViewModelMovies()
        observeViewModelSearchMovies()
        setSearchIconClick()
    }

    private fun inflateViews(view: View) {
        moviesList = view.findViewById(R.id.moviesListRv)
        searchEditTxt = view.findViewById(R.id.moviesListTxtInputTxt)
        searchIcon = view.findViewById(R.id.moviesListSearchIcon)
        errorIncludeView = view.findViewById(R.id.moviesErrorInclude)
    }

    /**
     * init recyclerview adapter and set a click action to it, this action directs the user
     * to MovieInfoFragment passing the clicked movie by safe args
     */
    private fun initAdapter(view: View) {
        adapter = TopRatedMoviesAdapter(
            object : AdapterClick<Movie> {
                override fun simpleClick(movie: Movie) {
                    val action = MovieListFragmentDirections
                        .actionMovieListFragmentToMovieInfoFragment(movie)
                    Navigation.findNavController(view).navigate(action)
                }
            }, ImgLoader(
                glide = Glide.with(view.context)
            )
        )
    }

    private fun setRecyclerViewAdapter() {
        moviesList.adapter = adapter
        moviesList.setHasFixedSize(true)
        moviesList.layoutManager = LinearLayoutManager(context)
    }

    /**
     * observe viewmodel activity movies, updating the views: showing an error
     * message with a try again button or populating the list in case of success
     */
    private fun observeViewModelMovies() {
        activityViewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        adapter.updateData(it.data)
                        setViewAsVisible(moviesList)
                        setViewAsGone(errorIncludeView)
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            context, getString(it.error.messageResource),
                            Toast.LENGTH_SHORT
                        ).show()
                        errorIncludeView.findViewById<TextView>(R.id.moviesErrorTxt)
                            .text = getString(it.error.messageResource)

                        errorIncludeView.findViewById<MaterialButton>(R.id.moviesErrorBtn)
                            .setOnClickListener {
                                CoroutineScope(Dispatchers.IO).launch {
                                    activityViewModel.getTopRatedMovies()
                                }
                            }
                        setViewAsGone(moviesList)
                        setViewAsVisible(errorIncludeView)
                    }
                }
            }
        })
    }

    private fun observeViewModelSearchMovies() {
//        viewModel.searchMovies.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                if (it.isEmpty()) {
//                    Toast.makeText(context, getString(R.string.cloud_network_error),
//                        Toast.LENGTH_SHORT).show()
//
//                    setViewAsGone(moviesList)
//                    //setViewAsVisible()
//                } else {
//                    adapter.moviesSearchList = it
//                    setViewAsVisible(moviesList)
//                    //setViewAsGone(errorMessage)
//                }
//            }
//        })
    }

    private fun setViewAsGone(view: View) {
        if (view.visibility.equals(View.VISIBLE))
            view.visibility = View.GONE
    }

    private fun setViewAsVisible(view: View) {
        if (view.visibility.equals((View.GONE)))
            view.visibility = View.VISIBLE
    }

    /**
     * call the filter for search from recyclerview adapter
     */
    private fun setSearchIconClick() {
        searchIcon.setOnClickListener {
            adapter.getFilter().filter(searchEditTxt.text)
            if (adapter.moviesSearchList.isEmpty()) {
//                CoroutineScope(Dispatchers.IO).launch {
//                    viewModel.searchMovies(searchEditTxt.text.toString())
//                }
            }
        }
    }


}