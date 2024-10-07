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

package me.nelonn.coprolite.paper.mixin;

import com.google.common.collect.Lists;
import io.papermc.paper.command.PaperPluginsCommand;
import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.coprolite.api.PluginContainer;
import me.nelonn.coprolite.api.PluginMetadata;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Mixin(PaperPluginsCommand.class)
public abstract class MixinPaperPluginsCommand {

    @Shadow @Final private static Component PLUGIN_TICK;
    @Shadow @Final private static Component PLUGIN_TICK_EMPTY;

    @Unique
    private static final Component COPROLITE_HEADER = Component.text("Coprolite Plugins:", TextColor.color(153, 51, 255));

    @Unique
    private static List<Component> coprolite_paper$formatPlugins(TreeMap<String, PluginMetadata> plugins) {
        List<Component> components = new ArrayList<>(plugins.size());
        for (PluginMetadata metadata : plugins.values()) {
            components.add(Component.text(metadata.getName() != null ? metadata.getName() : metadata.getId(),
                    NamedTextColor.GREEN));
        }

        boolean isFirst = true;
        List<Component> formattedSublists = new ArrayList<>();
        /*
        Split up the plugin list for each 10 plugins to get size down

        Plugin List:
        - Plugin 1, Plugin 2, .... Plugin 10,
          Plugin 11, Plugin 12 ... Plugin 20,
         */
        for (List<Component> componentSublist : Lists.partition(components, 10)) {
            Component component = Component.space();
            if (isFirst) {
                component = component.append(PLUGIN_TICK);
                isFirst = false;
            } else {
                component = PLUGIN_TICK_EMPTY;
                //formattedSublists.add(Component.empty()); // Add an empty line, the auto chat wrapping and this makes it quite jarring.
            }

            formattedSublists.add(component.append(Component.join(JoinConfiguration.commas(true), componentSublist)));
        }

        return formattedSublists;
    }

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Ljava/lang/String;formatted([Ljava/lang/Object;)Ljava/lang/String;"))
    private String coprolite_paper$addCount(String instance, Object[] args) {
        if (args.length == 0 || !(args[0] instanceof Integer integer)) return instance.formatted(args);
        return instance.formatted(integer + CoproliteLoader.getInstance().getAllPlugins().size());
    }

    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lorg/bukkit/command/CommandSender;sendMessage(Lnet/kyori/adventure/text/Component;)V", ordinal = 0))
    private void coprolite_paper$sendPlugins(CommandSender sender, String currentAlias, String[] args, CallbackInfoReturnable<Boolean> cir) {
        TreeMap<String, PluginMetadata> coprolitePlugins = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (PluginContainer plugin : CoproliteLoader.getInstance().getAllPlugins()) {
            coprolitePlugins.put(plugin.getMetadata().getName(), plugin.getMetadata());
        }
        if (!coprolitePlugins.isEmpty()) {
            sender.sendMessage(COPROLITE_HEADER);
        }
        for (Component component : coprolite_paper$formatPlugins(coprolitePlugins)) {
            sender.sendMessage(component);
        }
    }
}
