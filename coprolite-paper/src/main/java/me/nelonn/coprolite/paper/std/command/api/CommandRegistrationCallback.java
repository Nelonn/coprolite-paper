/*
 * Copyright 2024 Michael Neonov <two.nelonn at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.nelonn.coprolite.paper.std.command.api;

import com.mojang.brigadier.CommandDispatcher;
import me.nelonn.coprolite.paper.Event;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface CommandRegistrationCallback {
    Event<CommandRegistrationCallback> EVENT = new Event<>((callbacks) -> (dispatcher, environment, commandRegistryAccess) -> {
        for (CommandRegistrationCallback callback : callbacks) {
            callback.register(dispatcher, environment, commandRegistryAccess);
        }
    });

	void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment, CommandBuildContext commandRegistryAccess);
}