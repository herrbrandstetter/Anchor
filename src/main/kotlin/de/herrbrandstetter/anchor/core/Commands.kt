package de.herrbrandstetter.anchor.core

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

interface Command {
    fun execute(event: SlashCommandInteractionEvent)
}

interface ButtonInteraction {
    fun buttonInteraction(event: ButtonInteractionEvent)
}