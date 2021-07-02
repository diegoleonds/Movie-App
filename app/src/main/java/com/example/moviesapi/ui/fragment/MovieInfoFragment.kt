package com.example.moviesapi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
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
import com.example.moviesapi.ui.viewmodel.MovieInfoViewModel
import com.example.moviesapi.ui.viewmodel.MovieSharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Show movie infos and a top rated list
 */
class MovieInfoFragment : Fragment() {
    val activityViewModel: MovieSharedViewModel by activityViewModels()
    val viewModel: MovieInfoViewModel by viewModel()

    lateinit var movie: Movie

    lateinit var movieInfoBackIcon: ImageView
    lateinit var movieInfoImg: ImageView
    lateinit var movieInfoTopIcon: ImageView

    lateinit var movieInfoTitle: TextView
    lateinit var movieInfoDescription: TextView
    lateinit var movieInfoRatingCount: TextView
    lateinit var movieInfoTop: TextView
    lateinit var movieInfoData: TextView

    lateinit var movieInfoRatingBar: RatingBar

    lateinit var movieInfoRv: RecyclerView
    lateinit var adapter: TopRatedMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = MovieInfoFragmentArgs.fromBundle(it).movie
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        populateFields(view)
        setBackBtnClick(view)
        initAdapter(view)
        setRecyclerViewAdapter()
        observeViewModelMovies()

        activityViewModel.movies.value?.let {
            when (it) {
                is Result.Success -> {
                    viewModel.getMoviesCopyWithoutAnItem(movie, it.data)
                }
                is Result.Error -> {
                    Toast.makeText(
                        context, getString(it.error.messageResource),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * get reference for the views
     */
    private fun inflateViews(view: View) {
        movieInfoBackIcon = view.findViewById(R.id.movieInfoBackIcon)
        movieInfoImg = view.findViewById(R.id.movieInfoImg)
        movieInfoTitle = view.findViewById(R.id.movieInfoTitle)
        movieInfoDescription = view.findViewById(R.id.movieInfoDescription)
        movieInfoRatingBar = view.findViewById(R.id.movieInfoRatingBar)
        movieInfoRatingCount = view.findViewById(R.id.movieInfoRatingCount)
        movieInfoTop = view.findViewById(R.id.movieInfoTop)
        movieInfoTopIcon = view.findViewById(R.id.movieInfoTopIcon)
        movieInfoData = view.findViewById(R.id.movieInfoData)
        movieInfoRv = view.findViewById(R.id.movieInfoRv)
    }

    /**
     * populate the fields
     */
    private fun populateFields(view: View){
        val imgLoader = ImgLoader(Glide.with(view.context))
        imgLoader.loadImage(
            movie.movieImgUrl,
            movieInfoImg
        )
        movieInfoTitle.text = movie.title
        movieInfoDescription.text = movie.overview
        movieInfoRatingBar.rating = movie.rating.toFloat()
        movieInfoData.text = movie.getExtraData()

        movieInfoRatingCount.text = movie.rating.toString()

        if (movie.topRated) {
            movieInfoTop.visibility = View.VISIBLE
            movieInfoTopIcon.visibility = View.VISIBLE
        }
    }

    private fun initAdapter(view: View) {
        adapter = TopRatedMoviesAdapter(object : AdapterClick<Movie> {
            override fun simpleClick(movie: Movie) {
                val action = MovieInfoFragmentDirections
                    .actionMovieInfoFragmentSelf(movie)

                Navigation.findNavController(view).navigate(action)
            }
        }, ImgLoader(Glide.with(view.context)))
    }

    private fun setRecyclerViewAdapter() {
        movieInfoRv.adapter = adapter
        movieInfoRv.setHasFixedSize(true)
        movieInfoRv.layoutManager = LinearLayoutManager(context)
    }

    /**
     * observe movies from viewmodel and update the view with it and handle errors
     */
    private fun observeViewModelMovies() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isEmpty()) {
                    Toast.makeText(context, getString(R.string.cloud_network_error),
                        Toast.LENGTH_SHORT).show()
                } else {
                    adapter.updateData(it)
                }
            }
        })
    }

    /**
     * set default back action to the click
     */
    private fun setBackBtnClick(view: View) {
        movieInfoBackIcon.setOnClickListener {
            activity?.let {
                it.onBackPressed()
            }
        }
    }
}