package team.hypox.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.Texts;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import net.minecraft.server.command.ServerCommandSource;

@CommandAlias("test")
public class TestFabricCommand extends BaseCommand {

	@Subcommand("fabric")
	public void testSubCommand(ServerCommandSource sender) {
		sender.sendFeedback(Texts.from("HOLAAAA"), false);
	}

	@Default
	@HelpCommand
	public void help() {
		System.out.println("Help!");
	}

	@Subcommand("colorize")
	public void colorizeThis(ServerCommandSource sender, ArgTest arg) {
		System.out.println(arg + " HOLA " + sender.getName());
	}

	@Subcommand("one two")
	public void testSubCommandDouble(ServerCommandSource sender) {
		System.out.println("two subcommands");
	}

}
