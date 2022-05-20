package co.aikar.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class FabricRootCommand implements RootCommand, Command<ServerCommandSource> {

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
		execute(
				manager.getCommandIssuer(ctx.getSource()),
				ctx.getInput(),
				BrigadierArgumentAccessor.parseArguments(ctx)
		);

		return 1;
	}
}
