package com.example.rss_reader

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
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.StringReader


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var rssObject: RSSObject

    private val RSS_LINK = "http://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
    private val RSS_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("News")
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        var linearLayoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager
        loadRSS()
    }
    fun loadRSS() {
        var loadRSSAsync = object : AsyncTask<String, String, String>(){

            var mDialog: ProgressDialog = ProgressDialog(this@MainActivity)

            override fun onPreExecute() {
                mDialog.setMessage("Please wait...")
                mDialog.show()
            }

            override fun doInBackground(vararg p0: String?): String {
                var http: HTTPDataHandler = HTTPDataHandler()
                var result: String = http.GetHTTPData(p0[0])
                return result
            }

            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                //rssObject = Gson().fromJson(result,RSSObject::class.java) // TODO parse xml
                val parser = RssParser()
                val targetStream: InputStream = result!!.byteInputStream()

                rssObject = RSSObject(parser.parse(targetStream) as ArrayList<Item>?)

                var adapter: FeedAdapter = FeedAdapter(rssObject,baseContext)
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