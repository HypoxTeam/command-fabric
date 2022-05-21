package co.aikar.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FabricRootCommand implements RootCommand, Command<ServerCommandSource>, SuggestionProvider<ServerCommandSource> {

	private final FabricCommandManager manager;

	private final String name;
	private BaseCommand defCommand;
	private SetMultimap<String, RegisteredCommand> subCommands = HashMultimap.create();
	private List<BaseCommand> children = new ArrayList<>();
	boolean isRegistered = false;

	public FabricRootCommand(FabricCommandManager manager, String name) {
		this.manager = manager;
		this.name = name;
	}

	@Override
	public void addChild(BaseCommand command) {
		if (this.defCommand == null || !command.subCommands.get(BaseCommand.DEFAULT).isEmpty()) {
			this.defCommand = command;
		}
		addChildShared(this.children, this.subCommands, command);
	}

	@Override
	public CommandManager getManager() {
		return manager;
	}

	@Override
	public SetMultimap<String, RegisteredCommand> getSubCommands() {
		return subCommands;
	}

	@Override
	public List<BaseCommand> getChildren() {
		return children;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> ctx) {
		runFabricCommand(
				manager.getCommandIssuer(ctx.getSource()),
				ctx.getInput(),
				ctx.getArgument("subcommand", String.class).split(" ")
		);

		return 1;
	}

	private void runFabricCommand(FabricCommandIssuer issuer, String commandLabel, String[] args) {
		if (commandLabel.contains(":")) commandLabel = ACFPatterns.COLON.split(commandLabel, 2)[1];

		execute(
				issuer,
				commandLabel,
				args
		);
	}

	@Override
	public BaseCommand getDefCommand() {
		return defCommand;
	}

	/*
			NOT FINISHED YET
	 */
	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> ctx, SuggestionsBuilder builder) throws CommandSyntaxException {
		return CompletableFuture.supplyAsync(() ->
				CommandAdapter.from(ctx.getInput(),
						getTabCompletions(
								manager.getCommandIssuer(ctx.getSource()),
								ctx.getInput(),
								ctx.getRange().get("").split(" ")
						)
				)
		);
	}
}
