package de.herrbrandstetter.anchor.core

import de.herrbrandstetter.anchor.commands.AboutCommand
import de.herrbrandstetter.anchor.commands.HeadlinesCommand
import de.herrbrandstetter.anchor.commands.SearchCommand
import de.herrbrandstetter.anchor.commands.StatusCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class CommandHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val command = when(event.name) {
            "status" -> StatusCommand
            "about" -> AboutCommand
            "search" -> SearchCommand
            "headlines" -> HeadlinesCommand
            else -> null
        }
        command?.execute(event)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val command = when(event.componentId) {
            "guide", "about" -> AboutCommand
            "next", "prev" -> SearchCommand
            else -> null
        }
        command?.buttonInteraction(event)
    }

    override fun onReady(event: ReadyEvent) {
        event.jda.guilds.forEach {
            it.updateCommands().addCommands(
                Commands.slash("status", "View status information about the bot, like ping, uptime etc."),
                Commands.slash("about", "Read general information about the bot and learn how to use its commands."),
                Commands.slash("search", "Search for news articles by keyword(s). Filter results using the other options.")
                    .addOption(OptionType.STRING, "search", "Set the keyword(s) to search articles by.", true)
                    .addOption(OptionType.STRING, "lang", "Specify the language that articles should be written in.")
                    .addOption(OptionType.STRING, "country", "Specify the country in which articles were published.")
                    .addOption(OptionType.STRING, "from", "Set the earliest possible publication date (for example: '2023-12-24-13:00:00).")
                    .addOption(OptionType.STRING, "to", "Set the latest possible publication date (for example: '2023-12-24-13:00:00.)")
                    .addOptions(
                        OptionData(OptionType.STRING, "sortby", "Specify the type of sorting that articles should be returned with.")
                            .addChoice("Publication date", "publishedAt")
                            .addChoice("Relevance", "relevance")
                    ),
                Commands.slash("headlines", "Get current trending articles. Filter results using the available options.")
                    .addOptions(
                        OptionData(OptionType.STRING, "category", "Specify a category to search articles by.")
                            .addChoice("General", "general")
                            .addChoice("World", "world")
                            .addChoice("Nation", "nation")
                            .addChoice("Business", "business")
                            .addChoice("Technology", "technology")
                            .addChoice("Entertainment", "entertainment")
                            .addChoice("Sports", "sports")
                            .addChoice("Science", "science")
                            .addChoice("Health", "health")
                    )
                    .addOption(OptionType.STRING, "lang", "Specify the language that articles should be written in.")
                    .addOption(OptionType.STRING, "country", "Specify the country in which articles were published.")
                    .addOption(OptionType.STRING, "from", "Set the earliest possible publication date (for example: '2023-12-24-13:00:00).")
                    .addOption(OptionType.STRING, "to", "Set the latest possible publication date (for example: 2023-12-24-13:00:00).")
                    .addOption(OptionType.STRING, "search", "Set optional keyword(s) to search articles by.")
            ).queue()
        }
    }
}