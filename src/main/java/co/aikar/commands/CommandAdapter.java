package co.aikar.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Map;

public class CommandAdapter {

	public static LiteralArgumentBuilder<ServerCommandSource> from(BaseCommand command) {
		LiteralArgumentBuilder<ServerCommandSource> builder = LiteralArgumentBuilder.literal(command.getName());

		for (Map.Entry<String, RootCommand> entry : command.registeredCommands.entrySet()) {
			FabricRootCommand subCommand = (FabricRootCommand) entry.getValue();

			builder.then(
					RequiredArgumentBuilder.<ServerCommandSource, String>argument("subcommand", StringArgumentType.word())
					.executes(subCommand)
			);
		}

		return builder;
	}

}
