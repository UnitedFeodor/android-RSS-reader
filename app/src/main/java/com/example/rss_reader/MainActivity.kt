@file:Suppress("DEPRECATION")

package com.example.rss_reader

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rss_reader.adapter.FeedAdapter
import com.example.rss_reader.common.HTTPDataHandler
import com.example.rss_reader.model.Item
import com.example.rss_reader.model.RSSObject
import com.example.rss_reader.model.RssParser
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var rssObject: RSSObject
    // https://www.woman.ru/rss/
    //   https://rss.nytimes.com/services/xml/rss/nyt/Science.xml
    private val RSS_LINK = "https://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "News"

        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        var linearLayoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager
        loadRSS()
    }
    private fun loadRSS() {
        var loadRSSAsync =
            @SuppressLint("StaticFieldLeak")
            object : AsyncTask<String, String, String>(){

                var mDialog: ProgressDialog = ProgressDialog(this@MainActivity)

                override fun onPreExecute() {
                    mDialog.setMessage("Please wait...")
                    mDialog.show()
                }

                override fun doInBackground(vararg p0: String?): String? {


                    var http = HTTPDataHandler()
                    return http.GetHTTPData(p0[0])

                }

                override fun onPostExecute(result: String?) {
                    mDialog.dismiss()
                    //rssObject = Gson().fromJson(result,RSSObject::class.java) // TODO parse xml
                    val parser = RssParser()
                    val targetStream: InputStream = result!!.byteInputStream()

                    rssObject = RSSObject(parser.parse(targetStream) as ArrayList<Item>?)

                    var adapter = FeedAdapter(rssObject,baseContext)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            }
        loadRSSAsync.execute(RSS_LINK)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh) {
            loadRSS()
        }
        return true
    }
}