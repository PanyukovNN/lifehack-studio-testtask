package com.panyukov.lifehacktesttask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.panyukov.lifehacktesttask.model.Company
import com.panyukov.lifehacktesttask.service.ImagePuller
import kotlinx.android.synthetic.main.activity_company_card.*
import org.json.JSONException

class CompanyCardActivity : AppCompatActivity() {

    private val TAG = "CompanyCardActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_card)
        val company: Company? = intent.getParcelableExtra("company")
        if (company == null) {
            Log.e(TAG, "onCreate: company not found", IllegalArgumentException())
            return
        }
        text_company_card_name.text = company.name
        ImagePuller.pullImage(company.imgUrl)?.into(image_company_card)
        pullAdditionalInfo(company.id)
    }

    private fun pullAdditionalInfo(id: Long) {
        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, getString(R.string.base_url) + "/test.php?id=" + id, null,
            Response.Listener { response ->
                try {
                    val jsonObject = response.getJSONObject(0)
                    text_company_card_description.text = jsonObject.getString("description")
                    text_company_card_lat.text = jsonObject.getDouble("lat").toString()
                    text_company_card_lon.text = jsonObject.getDouble("lon").toString()
                    text_company_card_website.text = jsonObject.getString("www")
                    text_company_card_phone.text = jsonObject.getString("phone")
                } catch (e: JSONException) {
                    Log.e(TAG, "onErrorResponse: $e")
                }
            },
            Response.ErrorListener { error -> Log.e(TAG, "onErrorResponse: $error") }
        )
        requestQueue.add(jsonArrayRequest)
    }
}
