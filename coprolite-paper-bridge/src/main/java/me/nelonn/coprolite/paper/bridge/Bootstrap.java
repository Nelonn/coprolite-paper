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

package me.nelonn.coprolite.paper.bridge;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import me.nelonn.coprolite.paper.CoprolitePlugin;
import org.jetbrains.annotations.NotNull;

public class Bootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(@NotNull BootstrapContext bootstrapContext) {
        CoprolitePlugin.ON_BOOTSTRAP.invoker().run();
    }
}
