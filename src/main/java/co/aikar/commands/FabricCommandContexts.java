package co.aikar.commands;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

public class FabricCommandContexts extends CommandContexts<FabricCommandExecutionContext>{

	public FabricCommandContexts(FabricCommandManager manager) {
		super(manager);

		registerIssuerAwareContext(ServerCommandSource.class, FabricCommandExecutionContext::source);

		registerContext(Formatting.class, ctx ->
			Formatting.byName(ctx.popFirstArg())
		);

		//TODO REGISTER MORE CONTEXTS
	}

}
