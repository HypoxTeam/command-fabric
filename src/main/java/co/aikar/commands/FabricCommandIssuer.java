package co.aikar.commands;

import net.minecraft.entity.Entity;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FabricCommandIssuer implements CommandIssuer {

	private final FabricCommandManager commandManager;
	private final ServerCommandSource source;

	public FabricCommandIssuer(FabricCommandManager commandManager, ServerCommandSource source) {
		this.commandManager = commandManager;
		this.source = source;
	}

	@Override
	public ServerCommandSource getIssuer() {
		return source;
	}

	@Override
	public CommandManager getManager() {
		return commandManager;
	}

	@Override
	public boolean isPlayer() {
		return source.getEntity() instanceof ServerPlayerEntity;
	}

	@Override
	public @NotNull UUID getUniqueId() {
		if (source.getEntity() != null) {
			return source.getEntity().getUuid();
		}

		return UUID.nameUUIDFromBytes(source.getName().getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public boolean hasPermission(String permission) {
		return source.hasPermissionLevel(Integer.parseInt(permission));
	}

	@Override
	public void sendMessageInternal(String message) {
		Entity entity = source.getEntity();

		if (entity != null) {
			source.getServer().getPlayerManager().broadcast(Texts.from(message), MessageType.CHAT, entity.getUuid());
		}
	}
}
