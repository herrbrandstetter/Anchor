package de.herrbrandstetter.anchor.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.json.JSONObject
import java.awt.Color

fun newResponse(response: List<Any>, status: Int, page: Int): MessageEmbed {
    val red = Color(223, 74, 68)
    val teal = Color(90, 189, 171)
    val builder = EmbedBuilder().setAuthor("Anchor").setColor(red)

    with(builder) {
        when (status) {
            400 -> { setTitle("\u274C Invalid request"); setDescription("Your request was invalid. Please try modifying your search options, like language code or date format.") }
            403 -> { setTitle("\u274C Request limit reached"); setDescription("Daily request limit of 100 was reached. Please try again later.") }
            429 -> { setTitle("\u274C Please try again"); setDescription("Too many requests were made at the same time. Please make another request.") }
            500, 503 -> { setTitle("\u274C Server error"); setDescription("An internal error occurred with the API. Please try again later.") }
            else -> {
                if (response.isEmpty()) {
                    setTitle("\u274C No results found")
                    setDescription("No articles were found based on your search criteria.")
                    setImage("https://s3.envato.com/files/273549171/fr20191015_0087.jpg")
                    return@with
                }

                var image = ""
                for ((i, entry) in response.withIndex()) {
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
                setTitle("\uD83D\uDCF0 Results (Page $page)")
                setColor(teal)
            }
        }
    }

    return builder.build()
}