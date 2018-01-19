package com.ozgurtas.samplekotlinapplication.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.ozgurtas.samplekotlinapplication.Adapter.SearchAdapter
import com.ozgurtas.samplekotlinapplication.Connection.RestControllerFactory
import com.ozgurtas.samplekotlinapplication.Model.Response.SearchResult
import com.ozgurtas.samplekotlinapplication.R
import com.ozgurtas.samplekotlinapplication.Utils.Constants
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ozgur on 18.01.2018.
 */

class SearchActivity : BaseActivity() {

    private var resultList: ArrayList<SearchResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btnSearch.setOnClickListener {
            val searchTerm = etSearch.text.toString()
            if (!searchTerm.isEmpty()) {
                getResult(searchTerm)
            } else {
                etSearch.error = "Please enter search term"
            }
        }
    }

    //Connect the Search API
    private fun getResult(searchTerm: String) {
        showLoadingDialog()
        val call = RestControllerFactory().getInstance().getWeatherFactory()?.getSearchResult(Constants().apiKey, searchTerm)
        call?.enqueue(object : Callback<List<SearchResult>> {

            override fun onResponse(call: Call<List<SearchResult>>?, response: Response<List<SearchResult>>?) {
                if (response != null) {
                    hideLoadingDialog()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            if (resultList == null) {
                                resultList = ArrayList()
                                resultList?.addAll(response.body()!!)
                            } else {
                                resultList?.clear()
                                resultList?.addAll(response.body()!!)
                            }
                            val adapter = SearchAdapter(resultList)
                            rvSearch.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayout.VERTICAL, false)
                            rvSearch.adapter = adapter
                            etSearch.text.clear()
                        } else {
                            showToast(response.message()?.toString())
                        }
                    } else {
                        showToast(response.message()?.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<SearchResult>>?, t: Throwable?) {
                hideLoadingDialog()
                Log.e("onFailure", t.toString())
            }
        })
    }
}
