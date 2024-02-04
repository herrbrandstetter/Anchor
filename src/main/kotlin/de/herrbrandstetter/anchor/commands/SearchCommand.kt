package de.herrbrandstetter.anchor.commands

import de.herrbrandstetter.anchor.core.Command
import de.herrbrandstetter.anchor.util.newResponse
import de.herrbrandstetter.anchor.util.newSearchRequest
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType

object SearchCommand : Command {
    override fun execute(event: SlashCommandInteractionEvent) {
        event.deferReply().queue()

        val options = event.getOptionsByType(OptionType.STRING)
        val (response, status) = newSearchRequest(options)

        event.hook.editOriginalEmbeds(newResponse(response, status)).queue()
    }
}