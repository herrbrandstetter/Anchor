package de.herrbrandstetter.anchor

import de.herrbrandstetter.anchor.core.CommandHandler
import de.herrbrandstetter.anchor.core.Database
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.io.File
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val token = File("tokenDiscord.txt").readText()
    JDABuilder.createDefault(token)
        .addEventListeners(CommandHandler())
        .setActivity(Activity.watching("the news!"))
        .build()

    Database.getDatabase()

    runBlocking {
        launch {
            while (true) {
                delay(24.toDuration(DurationUnit.HOURS))
                Database.purgeDatabase()
            }
        }
    }
}