package me.kikugie.moarfps.config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.kikugie.moarfps.MoarFPS;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class ConfigCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("moarfps-config")
                .then(getIntOption("fakeFps",
                        () -> MoarFPS.getConfig().fakeFps,
                        (fakeFps) -> MoarFPS.getConfig().fakeFps = fakeFps))
                .then(getIntOption("fluctuateAmount",
                        () -> MoarFPS.getConfig().fluctuateAmount,
                        (fluctuateAmount) -> MoarFPS.getConfig().fluctuateAmount = fluctuateAmount))
                .then(getBooleanOption("enabled",
                        () -> MoarFPS.getConfig().enabled,
                        (enabled) -> MoarFPS.getConfig().enabled = enabled))
                .then(getBooleanOption("fluctuate",
                        () -> MoarFPS.getConfig().fluctuate,
                        (fluctuate) -> MoarFPS.getConfig().fluctuate = fluctuate))
                .then(literal("reset").executes(ConfigCommand::reset))
        );
    }

    private static int reset(CommandContext<FabricClientCommandSource> context) {
        MoarFPS.getConfig().reset();
        return 1;
    }

    private static LiteralArgumentBuilder<FabricClientCommandSource> getIntOption(String name, @NotNull Supplier<Integer> getter, @NotNull Consumer<Integer> setter) {
        LiteralArgumentBuilder<FabricClientCommandSource> source = literal(name);
        return source
                .then(argument("value", IntegerArgumentType.integer()).executes(context -> {
                    setter.accept(context.getArgument("value", Integer.class));
                    MoarFPS.getConfig().save();
                    return 1;
                }));
    }

    private static LiteralArgumentBuilder<FabricClientCommandSource> getBooleanOption(String name, @NotNull Supplier<Boolean> getter, @NotNull Consumer<Boolean> setter) {
        LiteralArgumentBuilder<FabricClientCommandSource> source = literal(name);
        return source
                .then(argument("value", BoolArgumentType.bool()).executes(context -> {
                    setter.accept(context.getArgument("value", Boolean.class));
                    MoarFPS.getConfig().save();
                    return 1;
                }));
    }
}
