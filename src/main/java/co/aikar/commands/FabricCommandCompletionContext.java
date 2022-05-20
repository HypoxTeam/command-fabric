package co.aikar.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricCommandCompletionContext extends CommandCompletionContext<FabricCommandIssuer> {
	public FabricCommandCompletionContext(RegisteredCommand command, FabricCommandIssuer issuer, String input, String config, String[] args) {
		super(command, issuer, input, config, args);
	}

	public ServerCommandSource source() {
		return this.issuer.getIssuer();
	}

	public ServerPlayerEntity player() throws CommandSyntaxException {
		return source().getPlayer();
	}
}
