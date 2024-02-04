package de.herrbrandstetter.anchor

import de.herrbrandstetter.anchor.core.CommandHandler
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.io.File

fun main() {
    val token = File("tokenDiscord.txt").readText()
    val jda = JDABuilder.createDefault(token)
        .addEventListeners(CommandHandler)
        .setActivity(Activity.watching("the news!"))
        .build()

    println("Type 'stop' to shutdown the bot.")

    do {
        val input = readln()
    } while (input != "stop")

    jda.shutdown()
}