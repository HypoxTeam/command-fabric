package co.aikar.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

public class FabricCommandContexts extends CommandContexts<FabricCommandExecutionContext> {

	public FabricCommandContexts(FabricCommandManager manager) {
		super(manager);

		registerContext(Formatting.class, ctx -> Formatting.byName(ctx.popFirstArg()));

		registerIssuerAwareContext(ServerCommandSource.class, FabricCommandExecutionContext::source);
		registerIssuerAwareContext(ServerPlayerEntity.class, ctx -> {
			if (!ctx.getIssuer().isPlayer()) {
				throw new InvalidCommandArgument(MessageKeys.NOT_ALLOWED_ON_CONSOLE, false);
			}

			try {
				return ctx.source().getPlayer();
			} catch (CommandSyntaxException e) {
				throw new InvalidCommandArgument(MessageKeys.NOT_ALLOWED_ON_CONSOLE, false);
			}
		});

		//TODO REGISTER MORE CONTEXTS
	}

}
