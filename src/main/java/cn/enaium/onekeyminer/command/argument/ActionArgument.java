package cn.enaium.onekeyminer.command.argument;

import cn.enaium.onekeyminer.model.Action;
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
public class ActionArgument implements ArgumentType<Action> {


    public static ActionArgument action() {
        return new ActionArgument();
    }

    @Override
    public Action parse(StringReader reader) {
        return Action.valueOf(reader.readUnquotedString());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(Arrays.stream(Action.values()).map(Enum::name), builder);
    }
}
