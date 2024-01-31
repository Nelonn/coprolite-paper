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

package me.nelonn.coprolite.paper.std.mixin;

import me.nelonn.coprolite.paper.std.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class CommandsMixin {

    //@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/AdvancementCommands;register(Lcom/mojang/brigadier/CommandDispatcher;)V"), method = "<init>(Lnet/minecraft/commands/Commands$CommandSelection;Lnet/minecraft/commands/CommandBuildContext;)V")

    //@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/commands/Commands$CommandSelection;Lnet/minecraft/commands/CommandBuildContext;)V")
    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/commands/CommandDispatcher$ServerType;Lnet/minecraft/commands/CommandBuildContext;)V")
    private void mimicry_addCommands(Commands.CommandSelection environment, CommandBuildContext commandRegistryAccess, CallbackInfo ci) {
        CommandRegistrationCallback.EVENT.invoker().register(((CommandsAccessor) (Commands) (Object) this).getDispatcher(), environment, commandRegistryAccess);
    }
}