package com.example.storeapplication.view


import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_kotlin_retrofit_livedata.customView.MyRecycleView
import com.example.storeapplication.adapter.CategoriesAdapter
import com.example.storeapplication.adapter.ProductListAdapter
import com.example.storeapplication.entities.CategoriesEntity
import com.example.storeapplication.entities.ProductListEntity
import com.example.storeapplication.entities.RatingsEntity
import com.example.storeapplication.listener.StoreItemClicklistener
import com.example.storeapplication.model.CategoryModel
import com.example.storeapplication.model.ProductListModel
import com.example.storeapplication.viewModel.CategoryListViewModel
import com.example.storeapplication.viewModel.ProductListViewModel
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_view.view.*


class MainActivity : AppCompatActivity(), StoreItemClicklistener {

    val activity = this
    var searchResultHandleList: ArrayList<ProductListModel> = ArrayList()
    var productList: ArrayList<ProductListModel> = ArrayList()
    var categoryList: ArrayList<CategoryModel> = ArrayList()
    var productListViewModel: ProductListViewModel? = null
    var categoryListViewModel: CategoryListViewModel? = null
    var adapter: ProductListAdapter? = null
    var categoryListAdapter: CategoriesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Home Screen"
        super.onCreate(savedInstanceState)
        setContentView(com.example.storeapplication.R.layout.activity_main)

        initializationOfCategory()
        initializationOfProducts()
        getAllCategories()
        getProductList()

        errorView.btnRetry.setOnClickListener {
            getProductList()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(com.example.storeapplication.R.menu.menu, menu)
        // Retrieve the SearchView and plug it into SearchManager
        val searchView: SearchView =
            MenuItemCompat.getActionView(menu!!.findItem(com.example.storeapplication.R.id.action_search)) as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {


                if (newText.isEmpty() && searchResultHandleList.isNotEmpty()) {
                    resetSearch()
                }

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
//                textView.setText(query)

                if (searchResultHandleList.isNullOrEmpty()) {
                    searchResultHandleList.addAll(productList)
                }

                performSearch(query)
                return true
            }
        })
        return true


    }

    private fun resetSearch() {
        if (searchResultHandleList.isNotEmpty()) {
            productList.clear()
            productList.addAll(searchResultHandleList)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun performSearch(query: String) {

        if (query.length > 2) {
            productList.clear()
            productList.addAll(searchResultHandleList.filter {
                it.title?.lowercase()!!.contains(query.lowercase())
            })
        } else {
            resetSearch()
        }
        Log.e("System out", "Size of main search : ${searchResultHandleList.size}")
        adapter?.notifyDataSetChanged()
    }

    private fun initializationOfCategory() {


        categoriesRv.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL,
            false
        )
        // ProduceList adapter
        categoryListAdapter = CategoriesAdapter(activity, categoryList, this)
        categoriesRv.adapter = categoryListAdapter
        categoriesRv.setHasFixedSize(true)

        // View Model
        categoryListViewModel =
            ViewModelProviders.of(activity).get(CategoryListViewModel::class.java)

    }

    private fun initializationOfProducts() {

        recycleView.layoutManager = LinearLayoutManager(activity)

        // ProduceList adapter
        adapter = ProductListAdapter(activity, productList, this)
        recycleView.adapter = adapter
        recycleView.setHasFixedSize(true)
        recycleView.loadingStateView = progressbar
        recycleView.errorStateView = errorView


        // View Model
        productListViewModel = ViewModelProviders.of(activity).get(ProductListViewModel::class.java)

    }

    private fun getAllCategories() {


        categoryListViewModel?.getCategoryListResponseLiveData()?.observe(activity, Observer {
            if (!it.isNullOrEmpty()) {

                categoryList.clear()
                for (i in 0 until it.size) {
                    val obj = CategoryModel()
                    obj.catName = "${it[i]}"
                    categoryList.add(obj)
                    CategoriesEntity(it[i]).save()

                }
                categoryListAdapter?.notifyDataSetChanged()
            } else {
                categoriesRv.visibility = View.GONE
                Toast.makeText(activity, "Oops! Something went wrong", Toast.LENGTH_LONG).show()

            }

        })
    }

    private fun getProductList() {


        recycleView.stateViewState = MyRecycleView.RecyclerViewStateEnum.LOADING


        productListViewModel?.getProductListResponseLiveData()?.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                productList.addAll(it)
                recycleView.stateViewState = MyRecycleView.RecyclerViewStateEnum.NORMAL
                adapter?.notifyDataSetChanged()

                performInsertDataOperation()
            } else {
                recycleView.stateViewState = MyRecycleView.RecyclerViewStateEnum.ERROR

            }

        })
    }

    private fun performInsertDataOperation() {

        if (productList.isNotEmpty()) {
            SugarRecord.deleteAll(ProductListEntity::class.java)
            SugarRecord.deleteAll(CategoriesEntity::class.java)
        }

        for (i in 0 until productList.size) {
            val entityObj = ProductListEntity()
            val ratingObj = RatingsEntity()
            entityObj.id = productList[i].id
            entityObj.title = productList[i].title
            entityObj.description = productList[i].description
            entityObj.image = productList[i].image
            entityObj.price = productList[i].price
            entityObj.title = productList[i].title

            ratingObj.productId = productList[i].id!!
            ratingObj.count = productList[i].rating?.count
            ratingObj.rate = productList[i].rating?.rate

            // Insert records to the database
            entityObj.save()
            ratingObj.save()
        }
    }

    override fun onProductItemClick(index: Int) {
        val i = Intent(activity, ProductDetailActivity::class.java)
        i.putExtra("productDetail", productList[index])
        startActivity(i)
    }

    override fun onCategoryItemClick(index: Int) {

        for (i in 0 until categoryList.size) {
            categoryList[i].isSelected = i == index
        }

        categoryListAdapter?.notifyDataSetChanged()
    }

}