package de.herrbrandstetter.anchor.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.json.JSONArray
import org.json.JSONObject
import java.awt.Color
import java.io.File

fun newResponse(response: JSONArray, status: Int): MessageEmbed {
    val builder = EmbedBuilder().setAuthor("Anchor").setColor(Color(223, 74, 68))

    with(builder) {
        when (status) {
            400 -> { setTitle("Invalid request"); setDescription("Your request was invalid. Please try modifying your search options, like language code or date format.") }
            403 -> { setTitle("Request limit reached"); setDescription("Daily request limit of 100 was reached. Please try again in X hours.") }
            429 -> { setTitle("Please try again"); setDescription("Too many requests were made at the same time. Please make another request.") }
            500, 503 -> { setTitle("Server error"); setDescription("An internal error occurred with the API. Please try again later.") }
            else -> {
                if (response.isEmpty) {
                    setTitle("No results found")
                    setDescription("No articles were found based on your search criteria.")
                    setImage("https://s3.envato.com/files/273549171/fr20191015_0087.jpg")
                    return@with
                }

                var image = ""
                for ((i, entry) in (response.take(5)).withIndex()) {
                    val article = JSONObject(entry.toString())
                    if (i == 0) {
                        image = article["image"].toString()
                        setFooter("Image: ${article["title"]} (${article.getJSONObject("source")["name"]})")
                    }

                    addField(
                        "${article["title"]} (${article.getJSONObject("source")["name"]} on ${article["publishedAt"].toString().substring(0, 10)})",
                        "${article["description"]} [**Read article**](${article["url"]})",
                        false
                    )
                }

                setImage(image)
                setTitle("Results (Page 1)")
            }
        }
    }

    return builder.build()
}