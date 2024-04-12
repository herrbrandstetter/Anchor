package de.herrbrandstetter.anchor.util

import net.dv8tion.jda.api.EmbedBuilder

fun buildAboutEmbed() = EmbedBuilder()
    .setTitle("Anchor")
    .setDescription("""Your personal news anchor right inside Discord! Always be up-to-date about what's happening around the world, and stay informed about the topics you're interested in.
                Anchor provides several filtering options like language, country, date of publication and more to let you easily find what you need.
                Powered by [GNews API](https://gnews.io/) and [JDA](https://jda.wiki/introduction/jda/).
            """.trimMargin())
    .addField("`/headlines`", "See what matters currently and read the most relevant trending articles. You can also choose a category like business or international news.", false)
    .addField("`/search`", "Search through tens of millions of articles by keywords. Easily find coverage on a specific topic you want to learn about.", false)
    .setThumbnail("https://cdn.discordapp.com/avatars/1122537043856203776/12a8adf9c9ce4cda048fa558272cd015.png")
    .build()

fun buildGuideEmbed() = EmbedBuilder()
    .setTitle("Anchor guide")
    .setDescription("This short guide explains how to use the filtering options for Anchor's commands.")
    .addField("`search` option",
        """
            You can search by one or multiple keywords. Use a space or AND separator to include multiple words in your search: `Apple Microsoft / Apple AND Microsoft`. This will only yield you articles which include all keywords. 
            Separate keywords with OR to search for articles that match either keyword (or both). `Apple OR Microsoft` might return you articles about just Apple, just Microsoft or both. OR takes higher precedence than AND.
            To deal with operator precedence, group keywords in parentheses. `Apple AND iPhone OR Microsoft` behaves as expected as `(Apple AND iPhone) OR Microsoft`.
            To exclude certain keywords from the search, use NOT: `Apple NOT iPhone`
            You can also search for an exact phrase or sequence of words. In this case, use quotation marks: `"Apple iPhone"`
        """.trimMargin(), false)
    .addField("Language and country options",
        """
            Language and country filter options must be provided with their correct two-letter code.
            Supported languages are: `ar` (Arabic), `zh` (Chinese), `nl` (Dutch), `en` (English), `fr` (French), `de` (German), `el` (Greek), `he` (Hebrew), `hi` (Hindi), `it` (Italian), `ja` (Japanese), `ml` (Malayalam), `mr` (Marathi), `no` (Norwegian), `pt` (Portuguese), `ro` (Romanian), `ru` (Russian), `es` (Spanish), `sv` (Swedish), `ta` (Tamil), `te` (Telugu), `uk` (Ukrainian). 
            Supported countries are: `au` (Australia), `br` (Brazil), `ca` (Canada), `cn` (China), `eg` (Egypt), `fr` (France), `de` (Germany), `gr` (Greece), `hk` (Hong Kong), `in` (India), `ie` (Ireland), `il` (Israel), `it` (Italy), `jp` (Japan), `nl` (Netherlands), `no` (Norway), `pk` (Pakistan), `pe` (Peru), `ph` (Philippines), `pt` (Portugal), `ro` (Romania), `ru` (Russia), `sg` (Singapore), `es` (Spain), `se` (Sweden), `ch` (Switzerland), `tw` (Taiwan), `ua` (Ukraine), `gb` (United Kingdom), `us` (United States).
        """.trimIndent(), false)
    .addField("Date options", "Dates you provide for `to` or `from` filter options must respect the following format: `YYYY-MM-DD` or `YYYY-MM-DD-hh:mm:ss`. Note that the time will always be determined relative to UTC. If no time is provided, 12 am is the default.", false)
    .setThumbnail("https://cdn.discordapp.com/avatars/1122537043856203776/12a8adf9c9ce4cda048fa558272cd015.png")
    .build()