package com.mark.arcsinustestapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mark.arcsinustestapp.R
import com.mark.arcsinustestapp.adapters.CharacterAdapter
import com.mark.arcsinustestapp.adapters.RecyclerViewAdapterBase
import com.mark.arcsinustestapp.databinding.ActivityMainBinding
import com.mark.arcsinustestapp.databinding.DialogCharacterBinding
import com.mark.arcsinustestapp.models.Character
import com.mark.arcsinustestapp.utils.EndlessScrollListener
import com.mark.arcsinustestapp.view_models.MarvelViewModel
import com.mark.arcsinustestapp.view_models.MarvelViewModelFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapterBase.ItemClickListener<Character> {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MarvelViewModel

    private lateinit var adapter: CharacterAdapter
    private lateinit var scrollListener: EndlessScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)

        initViewModel()
        initAdapter()
        initOthers()
    }

    private fun initOthers() {
        viewModel.getItems(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        scrollListener.resetValues()
        viewModel.getItems(1)
        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter() {
        adapter = CharacterAdapter()
        adapter.listener = this
        adapter.items = viewModel.items.value ?: listOf()

        binding.list.adapter = adapter
        GridLayoutManager(this, 2).let {
            binding.list.layoutManager = it
            scrollListener = object : EndlessScrollListener(it){
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    viewModel.getItems(page)
                }
            }
            binding.list.addOnScrollListener(scrollListener)
        }
    }

    private fun initViewModel() {
        val viewModelFactory = MarvelViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MarvelViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.event.observe(this, Observer {
            when(it){
                is MarvelViewModel.Event.OnItemSelected -> showItemDialog(it.item)
                is MarvelViewModel.Event.OnError -> Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.items.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }

    private fun showItemDialog(item: Character) {
        val dialog = Dialog(this)
        val dialogBinding = DialogCharacterBinding.inflate(layoutInflater)
        dialogBinding.item = item
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    override fun onClick(item: Character, position: Int) {
        viewModel.selectItem(item)
    }
}