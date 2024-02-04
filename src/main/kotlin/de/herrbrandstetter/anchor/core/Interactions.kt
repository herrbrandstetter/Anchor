package de.herrbrandstetter.anchor.core

import de.herrbrandstetter.anchor.commands.*
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

interface Command {
    fun execute(event: SlashCommandInteractionEvent)

    companion object {
        val commands = mapOf(
            "status" to StatusCommand,
            "about" to AboutCommand,
            "search" to SearchCommand,
            "headlines" to HeadlinesCommand
        )
    }
}

interface ButtonInteraction {
    fun buttonInteraction(event: ButtonInteractionEvent)

    companion object {
        val buttons = mapOf(
            "guide" to AboutCommand,
            "about" to AboutCommand
        )
    }
}