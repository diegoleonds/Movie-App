package com.example.moviesapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.moviesapi.R
import com.example.moviesapi.ui.viewmodel.MovieSharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A splash screen fragment, it loads data and when finish it move on to MovieListFragment
 */
class SplashScreenFragment: Fragment() {

    val activityViewModel: MovieSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.movies.observe(viewLifecycleOwner, Observer {
            Navigation.findNavController(view)
                .navigate(R.id.action_splashScreenFragment_to_movieListFragment)
        })

        CoroutineScope(Dispatchers.IO).launch {
            activityViewModel.getTopRatedMovies()
        }
    }
}