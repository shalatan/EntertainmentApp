package com.shalatan.entertainmentapp.ui.moviedetail.videos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.shalatan.entertainmentapp.databinding.FragmentVideosBinding
import com.shalatan.entertainmentapp.model.Result
import timber.log.Timber


class VideosFragment : Fragment() {

    private lateinit var binding: FragmentVideosBinding
    private lateinit var video: Result
    private lateinit var youTubePlayerView: YouTubePlayerView

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0f

    private val youtubeUrl = "https://www.youtube.com/watch?v="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        video = VideosFragmentArgs.fromBundle(requireArguments()).selectedVideo
        Timber.tag("ABCD GOT Video : ").d(video.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideosBinding.inflate(inflater)
        youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = video.key!!
                youTubePlayer.loadVideo(videoId, playbackPosition)
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.youtubePlayerView
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onStop() {
        super.onStop()
        youTubePlayerView.release()
    }
}