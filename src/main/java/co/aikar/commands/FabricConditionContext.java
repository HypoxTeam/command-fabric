package co.aikar.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

public class FabricConditionContext extends ConditionContext<FabricCommandIssuer> {

	public FabricConditionContext(FabricCommandIssuer issuer, String config) {
		super(issuer, config);
	}

	public ServerCommandSource source() {
		return getIssuer().getIssuer();
	}

	public PlayerEntity player() throws CommandSyntaxException {
		return getIssuer().getIssuer().getPlayer();
	}
}
