package co.aikar.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandAdapter {

	public static LiteralArgumentBuilder<ServerCommandSource> from(BaseCommand command) {
		LiteralArgumentBuilder<ServerCommandSource> builder = LiteralArgumentBuilder.literal(command.getName());

		for (Map.Entry<String, RootCommand> entry : command.registeredCommands.entrySet()) {
			FabricRootCommand subCommand = (FabricRootCommand) entry.getValue();

			builder.then(
					RequiredArgumentBuilder.<ServerCommandSource, String>argument("subcommand", StringArgumentType.greedyString())
					.executes(subCommand)
					.suggests(subCommand)
			);
		}

		return builder;
	}

	public static Suggestions from(String command, List<String> stringList) {
		return Suggestions.create(command,
				stringList.stream()
						.map(CommandAdapter::provide)
						.collect(Collectors.toList())
		);
	}

	public static Suggestion provide(String str) {
		return new Suggestion(new StringRange(0, str.length()), str);
	}

}
