package co.aikar.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class FabricRootCommand extends LiteralArgumentBuilder<ServerCommandSource> implements RootCommand {

	private final FabricCommandManager manager;

	private final String name;
	private BaseCommand defCommand;
	private SetMultimap<String, RegisteredCommand> subCommands = HashMultimap.create();
	private List<BaseCommand> children = new ArrayList<>();
	boolean isRegistered = false;

	public FabricRootCommand(FabricCommandManager manager, String name) {
		super(name);

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
}
