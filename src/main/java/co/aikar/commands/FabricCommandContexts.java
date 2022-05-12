package co.aikar.commands;

import net.minecraft.util.Formatting;

public class FabricCommandContexts extends CommandContexts<FabricCommandExecutionContext>{

	public FabricCommandContexts(FabricCommandManager manager) {
		super(manager);

		registerContext(Formatting.class, ctx ->
			Formatting.byName(ctx.popFirstArg())
		);

		//TODO REGISTER MORE CONTEXTS
	}

}
