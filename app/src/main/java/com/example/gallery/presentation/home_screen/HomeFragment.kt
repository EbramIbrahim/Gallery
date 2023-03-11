package com.example.gallery.presentation.home_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gallery.R
import com.example.gallery.databinding.FragmentHomeBinding
import com.example.gallery.models.UnSplashImage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), UnSplashImageAdapter.OnItemClickListener {
    private val viewModel by viewModels<UnSplashImageViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val imageAdapter = UnSplashImageAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.btnRetry.setOnClickListener {
            imageAdapter.retry()
        }
        setUpRecyclerView()

        viewModel.searchImages.observe(viewLifecycleOwner,{
            imageAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        handleLoadState()


        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_item_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.searchGallery -> {
                        val searchView = menuItem.actionView as SearchView
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null){
                                    binding.rvImage.scrollToPosition(0)
                                    viewModel.updateSearchQuery(query)
                                    searchView.clearFocus()
                                }
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                return true
                            }
                        })
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.STARTED)

        return binding.root
    }




    private fun handleLoadState() {
        imageAdapter.addLoadStateListener { loadState ->
            binding.apply {
                imagePb.isVisible = loadState.source.refresh is LoadState.Loading
                imagePb.isVisible = loadState.source.append is LoadState.Loading
                rvImage.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
            }
            when(loadState.source.refresh){
                is LoadState.Error ->{
                    Snackbar.make(
                        binding.root,
                        "Something went wrong!...Please check the internet connection..",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }


    private fun setUpRecyclerView() {
        binding.rvImage.apply {
            setHasFixedSize(true)
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClick(image: UnSplashImage) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://unsplash.com/@${image.user.username}?utm_source=DemoApp&utm_medium=referral")
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}