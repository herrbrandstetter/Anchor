package de.herrbrandstetter.anchor.commands

import com.mongodb.client.model.Filters
import de.herrbrandstetter.anchor.core.ButtonInteraction
import de.herrbrandstetter.anchor.core.Command
import de.herrbrandstetter.anchor.core.Database
import de.herrbrandstetter.anchor.util.newResponse
import de.herrbrandstetter.anchor.util.newSearchRequest
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.components.buttons.Button
import org.json.JSONArray

object SearchCommand : Command, ButtonInteraction {
    private val nextButton = Button.secondary("next", "Next page").withEmoji(Emoji.fromUnicode("U+27A1"))
    private val prevButton = Button.secondary("prev", "Previous page").withEmoji(Emoji.fromUnicode("U+2B05"))

    override fun execute(event: SlashCommandInteractionEvent) {
        event.deferReply().queue()

        val options = event.getOptionsByType(OptionType.STRING)
        val (response, status) = newSearchRequest(options)

        event.hook.editOriginalEmbeds(newResponse(response.take(5), status, 1))
            .setActionRow(nextButton)
            .queue { message -> if (status == 200) Database.insertResult(message.id, response.toString()) }
    }

    override fun buttonInteraction(event: ButtonInteractionEvent) {
        val response = JSONArray(
            Database.getResults()
                .find(Filters.eq("id", event.messageId))
                .first()["articles"].toString()
        )

        if (event.componentId == "next") {
            event.editMessageEmbeds(newResponse(response.drop(5), 200, 2))
                .setActionRow(prevButton)
                .queue()
        } else {
            event.editMessageEmbeds(newResponse(response.take(5), 200, 1))
                .setActionRow(nextButton)
                .queue()
        }
    }
}