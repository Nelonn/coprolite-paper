/*
 * Copyright 2023 Michael Neonov
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

package me.nelonn.coprolite.paper;

import me.nelonn.coprolite.api.CoproliteLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public class CoprolitePlugin {
    public static final String KEY = "coprolite-paper:plugin";

    public static @Nullable JavaPlugin getInstance() {
        return (JavaPlugin) CoproliteLoader.getInstance().getObjectShare().get(CoprolitePlugin.KEY);
    }

    public static final Event<Runnable> ON_BOOTSTRAP = new Event<>((callbacks) -> () -> {
        for (Runnable callback : callbacks) {
            callback.run();
        }
    });

    private static final Function<Iterable<Consumer<JavaPlugin>>, Consumer<JavaPlugin>> INVOKER = (callbacks) -> (plugin) -> {
        for (Consumer<JavaPlugin> callback : callbacks) {
            callback.accept(plugin);
        }
    };
    public static final Event<Consumer<JavaPlugin>> ON_LOAD = new Event<>(INVOKER);
    public static final Event<Consumer<JavaPlugin>> ON_ENABLE = new Event<>(INVOKER);
    public static final Event<Consumer<JavaPlugin>> ON_DISABLE = new Event<>(INVOKER);
}
