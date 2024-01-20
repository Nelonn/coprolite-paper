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

package me.nelonn.coprolite.paper.std.registryaccessor;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AttributeAccessor {
    @Nullable
    public static AttributeInstance getAttribute(@NotNull LivingEntity entity, @NotNull Attribute attribute, @NotNull Supplier<AttributeSupplier.Builder> builder) {
        try {
            return entity.getAttributes().getInstance(attribute);
        } catch (Exception e) {
            AttributeSupplier supplier = builder.get().build();
            for (Attribute attr : BuiltInRegistries.ATTRIBUTE) {
                try {
                    AttributeInstance instance = supplier.getAttributeInstance(attr);
                    entity.getAttributes().registerAttribute(attr);
                    instance.load(instance.save());
                } catch (Exception ignored) {
                }
            }
        }
        return entity.getAttributes().getInstance(attribute);
    }
}
