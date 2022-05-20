package co.aikar.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;

import java.lang.reflect.Field;
import java.util.Map;

public class BrigadierArgumentAccessor {

	static final Class<?> CONTEXT_CLASS;
	static final Field PARSED_ARGUMENTS;

	static {
		try {
			CONTEXT_CLASS = CommandContext.class;
			PARSED_ARGUMENTS = CONTEXT_CLASS.getDeclaredField("arguments");

			PARSED_ARGUMENTS.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("Failed while getting argument field in Brigadier Context Class");
		}
	}

	public static <S> String[] parseArguments(CommandContext<S> ctx) {
		Map<String, ParsedArgument<S, ?>> arguments = accessFrom(ctx);

		return arguments.values().stream()
				.map(argument -> String.valueOf(argument.getResult()))
				.toArray(String[]::new);
	}

	public static <S> Map<String, ParsedArgument<S, ?>> accessFrom(CommandContext<S> ctx) {
		try {
			return (Map<String, ParsedArgument<S, ?>>) PARSED_ARGUMENTS.get(ctx);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed while accessing to argument field in Brigadier Context Class");
		}
	}

}
