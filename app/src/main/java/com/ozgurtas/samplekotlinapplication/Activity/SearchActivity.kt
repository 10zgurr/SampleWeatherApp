package com.ozgurtas.samplekotlinapplication.Activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.ozgurtas.samplekotlinapplication.Adapter.SearchAdapter
import com.ozgurtas.samplekotlinapplication.Connection.RestControllerFactory
import com.ozgurtas.samplekotlinapplication.Model.Response.SearchResult
import com.ozgurtas.samplekotlinapplication.R
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.ozgurtas.samplekotlinapplication.Utils.Constants.Companion.API_KEY


/**
 * Created by Ozgur on 18.01.2018.
 */

class SearchActivity : BaseActivity() {

    private var resultList: ArrayList<SearchResult>? = null

    override fun onCreateFinished(savedInstanceState: Bundle?) {
        //EditText search listener on keyboard search icon
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = textView.text.toString()
                if (searchTerm.isNotEmpty()) {
                    //Hide keyboard after typing
                    val inputManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                    getResult(searchTerm)
                } else {
                    etSearch.error = "Please enter search term"
                }
                return@OnEditorActionListener true
            } else
                return@OnEditorActionListener false
        })
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_search
    }

    //Connect the Search API
    private fun getResult(searchTerm: String) {
        showLoadingDialog()
        val call = RestControllerFactory.getWeatherFactory()?.getSearchResult(API_KEY, searchTerm)
        call?.enqueue(object : Callback<List<SearchResult>> {

            override fun onResponse(call: Call<List<SearchResult>>?, response: Response<List<SearchResult>>?) {
                if (response != null) {
                    hideLoadingDialog()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            if (response.body()!!.isNotEmpty()) {
                                rvSearch.visibility = View.VISIBLE
                                tvResult.visibility = View.GONE
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
                            } else {
                                rvSearch.visibility = View.GONE
                                tvResult.visibility = View.VISIBLE
                                tvResult.text = "No result about " + searchTerm
                            }
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
