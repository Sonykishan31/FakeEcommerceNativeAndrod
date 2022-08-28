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
import com.example.storeapplication.R
import com.example.storeapplication.adapter.CategoriesAdapter
import com.example.storeapplication.adapter.ProductListAdapter
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.entities.CategoriesEntity
import com.example.storeapplication.entities.MyCart
import com.example.storeapplication.entities.ProductListEntity
import com.example.storeapplication.entities.RatingsEntity
import com.example.storeapplication.listener.StoreItemClicklistener
import com.example.storeapplication.model.ProductListModel
import com.example.storeapplication.viewModel.CategoryListViewModel
import com.example.storeapplication.viewModel.ProductListViewModel
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_view.view.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), StoreItemClicklistener {

    val activity = this
    var searchResultAndFilterHandleList: ArrayList<ProductListEntity> = ArrayList()
    var productList: ArrayList<ProductListEntity> = ArrayList()
    var categoryList: ArrayList<CategoriesEntity> = ArrayList()
    var productListViewModel: ProductListViewModel? = null
    var categoryListViewModel: CategoryListViewModel? = null
    var adapter: ProductListAdapter? = null
    var categoryListAdapter: CategoriesAdapter? = null
    var selectedCategory = ""
    val ALL  = "ALL"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleText.text = resources.getString(R.string.app_name)
        toolbar.setNavigationIcon(null)

        initializationOfCategory()
        initializationOfProducts()
        getAllCategories()
        getProductList()

        errorView.btnRetry.setOnClickListener {
            getProductList()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        // Retrieve the SearchView and plug it into SearchManager
        val searchView: SearchView =
            MenuItemCompat.getActionView(menu!!.findItem(R.id.action_search)) as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {


                if (newText.isEmpty() && searchResultAndFilterHandleList.isNotEmpty()) {
                    resetSearch()
                }

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
//                textView.setText(query)

                if (searchResultAndFilterHandleList.isNullOrEmpty()) {
                    searchResultAndFilterHandleList.addAll(productList)
                }

                performSearch(query)
                return true
            }
        })
        return true


    }

    private fun resetSearch() {
        if (searchResultAndFilterHandleList.isNotEmpty()) {
            productList.clear()
            productList.addAll(searchResultAndFilterHandleList)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun performSearch(query: String) {

        if (query.length > 2) {
            productList.clear()
            productList.addAll(searchResultAndFilterHandleList.filter {
                it.title?.lowercase()!!.contains(query.lowercase())
            })
        } else {
            resetSearch()
        }
        Log.e("System out", "Size of main search : ${searchResultAndFilterHandleList.size}")
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
        adapter = ProductListAdapter(productList, this)
        recycleView.adapter = adapter
        recycleView.setHasFixedSize(true)
        recycleView.loadingStateView = progressbar
        recycleView.errorStateView = errorView


        // View Model
        productListViewModel = ViewModelProviders.of(activity).get(ProductListViewModel::class.java)

    }

    private fun getAllCategories() {

        val localCategoryData = SugarRecord.listAll(CategoriesEntity::class.java)
        selectedCategory = ALL
        if (localCategoryData.isNotEmpty()){
            categoryList.clear()
            categoryList.add(CategoriesEntity(categoryName = ALL, isSelected = true))
            categoryList.addAll(localCategoryData)
            handleTotalView()
            categoryListAdapter?.notifyDataSetChanged()
            return
        }

        categoryListViewModel?.getCategoryListResponseLiveData()?.observe(activity, Observer {
            if (!it.isNullOrEmpty()) {

                categoryList.clear()
                categoryList.add(CategoriesEntity(categoryName = ALL, isSelected = true))
                SugarRecord.deleteAll(CategoriesEntity::class.java)
                for (i in 0 until it.size) {
                    val obj = CategoriesEntity()
                    obj.categoryName = "${it[i]}"
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


        // If data is available in local database, Get and fill the product list from local
        val localProductListData = SugarRecord.listAll(ProductListEntity::class.java)
        if (localProductListData.isNotEmpty()){

            productList.clear()
            productList.addAll(localProductListData)
            searchResultAndFilterHandleList.clear()
            for (i in 0 until  productList.size){

                val ratingObj= SugarRecord.find(RatingsEntity::class.java, "product_id = ?", "${localProductListData[i].getId()}").get(0)
                if (ratingObj!=null){
                    productList[i].rating = ratingObj
                }
            }
            searchResultAndFilterHandleList.addAll(productList)
            adapter?.notifyDataSetChanged()
            return
        }

        recycleView.stateViewState = MyRecycleView.RecyclerViewStateEnum.LOADING


        productListViewModel?.getProductListResponseLiveData()?.observe(this, Observer {
            if (!it.isNullOrEmpty()) {

                recycleView.stateViewState = MyRecycleView.RecyclerViewStateEnum.NORMAL
                performInsertDataOperation(it)



            } else {
                recycleView.stateViewState = MyRecycleView.RecyclerViewStateEnum.ERROR

            }

        })
    }

    private fun performInsertDataOperation(list: List<ProductListModel>) {

        SugarRecord.deleteAll(ProductListEntity::class.java)
        productList.clear()
        for (i in 0 until list.size){
            val prodEntity = ProductListEntity()
            val ratingsEntity = RatingsEntity()

            prodEntity.id=list[i].id
            prodEntity.image=list[i].image
            prodEntity.title=list[i].title
            prodEntity.category=list[i].category
            prodEntity.description=list[i].description
            prodEntity.price=list[i].price
            ratingsEntity.rate = list[i].rating?.rate
            ratingsEntity.count = list[i].rating?.count
            ratingsEntity.productId = list[i].id!!
            prodEntity.rating = ratingsEntity
            prodEntity.save()
            ratingsEntity.save()
            productList.add(prodEntity)
        }

        adapter?.notifyDataSetChanged()
    }

    override fun onProductItemClick(index: Int) {
        val i = Intent(activity, ProductDetailActivity::class.java)
        i.putExtra("productId", productList[index].getId())
        startActivity(i)
    }

    override fun onCategoryItemClick(index: Int) {

        for (i in 0 until categoryList.size) {
            categoryList[i].isSelected = i == index
        }
        selectedCategory = categoryList.filter { it.isSelected }[0].categoryName
        productList.clear()
        if (selectedCategory.equals(ALL,true)){
            productList.addAll(searchResultAndFilterHandleList)
        }
        else {
            productList.addAll(searchResultAndFilterHandleList.filter { it.category.equals(selectedCategory) })
        }

        adapter?.notifyDataSetChanged()
        categoryListAdapter?.notifyDataSetChanged()
    }

    override fun addToCartActin(index: Int) {
        MyCart.addItemToCart(productList[index].getId().toInt())
        refreshAdapter()
    }

    override fun removeFromCartAction(index: Int) {
        MyCart.removeItemFromCart(productList[index].getId().toInt())
        refreshAdapter()
    }

    fun refreshAdapter(){

        productList.clear()
        if (selectedCategory.equals(ALL,true)){
            productList.addAll(searchResultAndFilterHandleList)
        }
        else{
            productList.addAll(searchResultAndFilterHandleList.filter { it.category.equals(selectedCategory) })

        }
        handleTotalView()
        adapter?.notifyDataSetChanged()
    }

    fun handleTotalView(){

        val totalAmount = MyCart.calculateTotal()
        totalMainTv.text = "${MyApplication.instance.getString(R.string.Rs)} $totalAmount"
        if (totalAmount > 0){
            totalMainLL.visibility = View.VISIBLE
        }
        else {
            totalMainLL.visibility = View.GONE
        }
    }

}




