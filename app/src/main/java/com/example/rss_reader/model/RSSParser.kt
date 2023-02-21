package com.example.rss_reader.model

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
class RssParser {
    private val rssItems = ArrayList<Item>()
    private var rssItem : Item ?= null
    private var text: String? = null
    fun parse(inputStream: InputStream):List<Item> {
        try {


            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()

            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            var foundItem = false
            var linkFound = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {

                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        // create a new instance of item
                        foundItem = true
                        rssItem = Item()
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        // add item object to list
                        rssItem?.let { rssItems.add(it) }
                        foundItem = false
                        linkFound = false;
                    } else if ( foundItem && tagname.equals("title", ignoreCase = true)) {
                        rssItem!!.title = text.toString()
                    } else if (!linkFound && foundItem && tagname.equals("link", ignoreCase = true)) {
                        rssItem!!.link = text.toString()
                        linkFound = true;
                    } else if (foundItem && tagname.equals("pubDate", ignoreCase = true)) {
                        rssItem!!.pubDate = text.toString()
                    } else if (foundItem && tagname.equals("category", ignoreCase = true)) {
                        rssItem!!.categories.add(text.toString())
                    } else if (foundItem && tagname.equals("description", ignoreCase = true)) {
                        rssItem!!.description = text.toString()
                    }
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rssItems
    }
}