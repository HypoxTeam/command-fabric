package co.aikar.commands;

import net.minecraft.util.Formatting;

public class FabricMessageFormatter extends MessageFormatter<Formatting> {

	public FabricMessageFormatter(Formatting... formatting) {
		super(formatting);
	}

	@Override
	public String format(Formatting color, String message) {
		return Texts.from(message).formatted(color).asString();
	}

}
