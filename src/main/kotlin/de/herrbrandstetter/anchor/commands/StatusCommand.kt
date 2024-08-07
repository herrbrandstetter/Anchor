package de.herrbrandstetter.anchor.commands

import de.herrbrandstetter.anchor.core.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

object StatusCommand : Command {
    override fun execute(event: SlashCommandInteractionEvent) {
        val time = System.currentTimeMillis()
        val servers = event.jda.guilds.size
        val builder = EmbedBuilder().setTitle("Anchor status")
            .setDescription("Information about the bots current status:")
            .addField("\uD83C\uDF10 Server count", "$servers ${if (servers == 1) "server" else "servers"}", false)
            .addField("\uD83D\uDD52 Uptime", "NYI", false)
            .setThumbnail("https://cdn-icons-png.flaticon.com/128/9437/9437983.png")

        event.replyEmbeds(builder.build()).setEphemeral(true)
            .flatMap { hook ->
                hook.editOriginalEmbeds(
                    builder
                        .addField("\u23F1 Ping", "${System.currentTimeMillis() - time} ms", false)
                        .build()
                )
            }.queue()
    }
}