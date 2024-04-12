package de.herrbrandstetter.anchor.core

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoClient
import org.bson.Document
import org.bson.types.ObjectId
import java.util.Date

object Database {
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString("mongodb://localhost:27017"))
        .retryWrites(true).build()
    private val client = MongoClient.create(settings)

    fun getDatabase() = client.getDatabase("anchor")
    fun getResults() = getDatabase().getCollection<Document>("results")

    fun insertResult(id: String, response: String) {
        val result = Document("_id", ObjectId())
            .append("id", id)
            .append("articles", response)
            .append("date", System.currentTimeMillis())

        getResults().insertOne(result)
    }

    fun purgeDatabase() {
        val results = getResults()
        val docs = results.find<Document>().toList()
        val now = Date(System.currentTimeMillis())
        var delCount = 0L

        for (doc in docs) {
            val created = Date(doc["date"] as Long)
            val expires = Date(created.time + 24 * 60 * 60 * 1000)

            if (now.after(expires)) {
                val del = results.deleteOne(Filters.eq("id", doc["id"]))
                delCount += del.deletedCount
            }

        }

        println("Purged $delCount article results from database.")
    }

    init {
        println("Database connected")
    }
}