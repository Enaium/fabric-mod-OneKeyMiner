package cn.enaium.onekeyminer.command.argument;

import cn.enaium.onekeyminer.model.Tool;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * @author Enaium
 */
public class ToolArgument implements ArgumentType<Tool> {

    public static ToolArgument tool() {
        return new ToolArgument();
    }

    @Override
    public Tool parse(StringReader reader) {
        return Tool.valueOf(reader.readUnquotedString());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(Arrays.stream(Tool.values()).map(Enum::name), builder);
    }
}
