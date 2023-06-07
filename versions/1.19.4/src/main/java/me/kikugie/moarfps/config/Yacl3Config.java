package me.kikugie.moarfps.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import me.kikugie.moarfps.MoarFPS;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Yacl3Config {
    public static Screen createGui(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(MoarFPS.translated("moarfps.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(MoarFPS.translated("moarfps.config.category"))
                        .group(OptionGroup.createBuilder()
                                .option(optionForInt("fakefps",
                                        1000,
                                        () -> MoarFPS.getConfig().fakeFps,
                                        (fakeFps) -> MoarFPS.getConfig().fakeFps = fakeFps))
                                .option(optionForBool("fluctuate",
                                        true,
                                        () -> MoarFPS.getConfig().fluctuate,
                                        (fluctuate) -> MoarFPS.getConfig().fluctuate = fluctuate))
                                .option(optionForInt("amount",
                                        50,
                                        () -> MoarFPS.getConfig().fluctuateAmount,
                                        (fluctuateAmount) -> MoarFPS.getConfig().fluctuateAmount = fluctuateAmount))
                                .option(optionForBool("enabled",
                                        true,
                                        () -> MoarFPS.getConfig().enabled,
                                        (enabled) -> MoarFPS.getConfig().enabled = enabled))
                                .build())
                        .build())
                .save(MoarFPS.getConfig()::save)
                .build()
                .generateScreen(parent);
    }

    private static Option<Integer> optionForInt(String name, int defaultVal, @NotNull Supplier<Integer> getter, @NotNull Consumer<Integer> setter) {
        return Option.<Integer>createBuilder()
                .name(MoarFPS.translated("moarfps.config." + name))
                .description(OptionDescription.createBuilder()
                        .text(MoarFPS.translated("moarfps.config." + name + ".tooltip"))
                        .build())
                .binding(defaultVal,
                        getter,
                        setter)
                .controller(IntegerFieldControllerBuilder::create)
                .build();
    }

    private static Option<Boolean> optionForBool(String name, boolean defaultVal, @NotNull Supplier<Boolean> getter, @NotNull Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(MoarFPS.translated("moarfps.config." + name))
                .description(OptionDescription.createBuilder()
                        .text(MoarFPS.translated("moarfps.config." + name + ".tooltip"))
                        .build())
                .binding(defaultVal,
                        getter,
                        setter)
                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true).valueFormatter(MoarFPS.YES_NO))
                .build();
    }
}
