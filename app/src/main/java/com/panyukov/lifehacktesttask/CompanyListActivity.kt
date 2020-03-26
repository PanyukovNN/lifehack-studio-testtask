package com.panyukov.lifehacktesttask

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.panyukov.lifehacktesttask.model.Company
import com.panyukov.lifehacktesttask.service.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_company_list.*
import org.json.JSONException


class CompanyListActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)
        recycler_view.layoutManager = LinearLayoutManager(this)
        pullCompanies(object : AnswerListAsyncResponse {
            override fun processFinished(companies: List<Company>?) {
                if (companies == null) return
                val recyclerViewAdapter = RecyclerViewAdapter(companies)
                recycler_view.adapter = recyclerViewAdapter
                recyclerViewAdapter.onItemClick = { company ->
                    run {
                        val intent = Intent(this@CompanyListActivity, CompanyCardActivity::class.java)
                        intent.putExtra("company", company)
                        startActivity(intent)
                    }
                }
            }
        })
    }

    private fun pullCompanies(callBack: AnswerListAsyncResponse?) {
        val requestQueue = Volley.newRequestQueue(this)
        val companies: MutableList<Company> = ArrayList()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, getString(R.string.base_url) + "/test.php", null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)
                        val id = jsonObject.getLong("id")
                        val name = jsonObject.getString("name")
                        val imgUrl = jsonObject.getString("img")
                        val company = Company(id, name, imgUrl)
                        companies.add(company)
                    } catch (e: JSONException) {
                        Log.e(TAG, "onErrorResponse: $e")
                    }
                }
                callBack?.processFinished(companies)
            },
            Response.ErrorListener { error -> Log.e(TAG, "onErrorResponse: $error") }
        )
        requestQueue.add(jsonArrayRequest)
    }

    interface AnswerListAsyncResponse {
        fun processFinished(companies: List<Company>?)
    }
}
