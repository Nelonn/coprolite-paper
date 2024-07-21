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

package me.nelonn.coprolite.paper.bridge;

import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.coprolite.paper.CoprolitePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    public Plugin() {
        CoproliteLoader.getInstance().getObjectShare().put(CoprolitePlugin.KEY, this);
    }

    @Override
    public void onLoad() {
        CoprolitePlugin.ON_LOAD.invoker().accept(this);
    }

    @Override
    public void onEnable() {
        CoprolitePlugin.ON_ENABLE.invoker().accept(this);
    }

    @Override
    public void onDisable() {
        CoprolitePlugin.ON_DISABLE.invoker().accept(this);
    }
}
