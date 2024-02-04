package de.herrbrandstetter.anchor.commands

import de.herrbrandstetter.anchor.core.ButtonInteraction
import de.herrbrandstetter.anchor.core.Command
import de.herrbrandstetter.anchor.util.buildAboutEmbed
import de.herrbrandstetter.anchor.util.buildGuideEmbed
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button

object AboutCommand : Command, ButtonInteraction {
    private val guideButton = Button.secondary("guide", "Read guide").withEmoji(Emoji.fromUnicode("U+1F4D6"))
    private val aboutButton = Button.secondary("about", "Go back").withEmoji(Emoji.fromUnicode("U+23EA"))

    override fun execute(event: SlashCommandInteractionEvent) {
        event.replyEmbeds(buildAboutEmbed()).setActionRow(guideButton).setEphemeral(true).queue()
    }

    override fun buttonInteraction(event: ButtonInteractionEvent) {
        if (event.button.id == "guide") {
            event.editMessageEmbeds(buildGuideEmbed()).setActionRow(aboutButton).queue()
        } else {
            event.editMessageEmbeds(buildAboutEmbed()).setActionRow(guideButton).queue()
        }
    }
}