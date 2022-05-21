package co.aikar.commands;

import net.minecraft.server.command.ServerCommandSource;

public class FabricCommandContexts extends CommandContexts<FabricCommandExecutionContext> {

	public FabricCommandContexts(FabricCommandManager manager) {
		super(manager);

		registerIssuerAwareContext(ServerCommandSource.class, FabricCommandExecutionContext::source);

		//TODO REGISTER MORE CONTEXTS
	}

}
