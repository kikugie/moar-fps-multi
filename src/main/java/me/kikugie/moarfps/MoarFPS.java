/*
 * This file is part of the MoarFPS project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * MoarFPS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoarFPS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MoarFPS.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.kikugie.moarfps;

import me.kikugie.moarfps.config.ConfigCommand;
import me.kikugie.moarfps.config.ConfigState;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//#if MC > 11802
//$$ import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
//#else
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
//#endif

public class MoarFPS implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    private static ConfigState configState;

    public static ConfigState getConfig() {
        return configState;
    }

    @Override
    public void onInitializeClient() {
        Reference.init();
        configState = ConfigState.load();
        //#if MC <= 11802
        ConfigCommand.register(ClientCommandManager.DISPATCHER);
        //#else
        //$$ ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> ConfigCommand.register(dispatcher));
        //#endif
    }
}
