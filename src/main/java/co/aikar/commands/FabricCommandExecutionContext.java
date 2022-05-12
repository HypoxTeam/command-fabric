package co.aikar.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.Map;

public class FabricCommandExecutionContext extends CommandExecutionContext<FabricCommandExecutionContext, FabricCommandIssuer> {

	public FabricCommandExecutionContext(RegisteredCommand cmd,
																			 CommandParameter param,
																			 FabricCommandIssuer sender,
																			 List<String> args,
																			 int index,
																			 Map<String, Object> passedArgs) {
		super(cmd, param, sender, args, index, passedArgs);
	}

	public ServerCommandSource source() {
		return issuer.getIssuer();
	}

	public PlayerEntity player() throws CommandSyntaxException {
		return issuer.getIssuer().getPlayer();
	}

}
