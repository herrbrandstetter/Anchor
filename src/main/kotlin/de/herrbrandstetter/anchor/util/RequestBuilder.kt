package de.herrbrandstetter.anchor.util

import khttp.get
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import org.json.JSONArray
import java.io.File
import java.net.URLEncoder

val apiKey = File("tokenApi.txt").readText()

fun newSearchRequest(options: List<OptionMapping>): Pair<JSONArray, Int> {
    val url = "https://gnews.io/api/v4/search?%s&apikey=%s".format(buildParams(options), apiKey)
    val response = get(url)

    return response.jsonObject.getJSONArray("articles") to response.statusCode
}

fun newHeadlinesRequest(options: List<OptionMapping>): Pair<JSONArray, Int> {
    val url = "https://gnews.io/api/v4/top-headlines?%s&apikey=%s".format(buildParams(options), apiKey)
    val response = get(url)

    return response.jsonObject.getJSONArray("articles") to response.statusCode
}

fun buildParams(options: List<OptionMapping>): String {
    return options.joinToString("&") { option ->
        val name = option.name
        val value = option.asString

        when (name) {
            "search" -> "q=${URLEncoder.encode(value, "UTF-8")}"
            "from", "to" -> "$name=${ if (value.length == 10) "${value}T00:00:00Z" else "${value.substring(0, 9)}T${value.substring(11, 18)}Z" }"
            else -> "$name=$value"
        }
    }
}