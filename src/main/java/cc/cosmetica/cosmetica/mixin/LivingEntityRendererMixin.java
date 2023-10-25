/*
 * Copyright 2022, 2023 EyezahMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.cosmetica.cosmetica.mixin;

import cc.cosmetica.cosmetica.Cosmetica;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
	@Redirect(method = "setupRotations", at = @At(value = "INVOKE", target = "Lnet/minecraft/ChatFormatting;stripFormatting(Ljava/lang/String;)Ljava/lang/String;"))
	private String redirectPlayersToOnlyOurCheck(String name, LivingEntity entity) {
		return entity instanceof AbstractClientPlayer ? "Dinnerbone" : ChatFormatting.stripFormatting(name);
	}

	@Redirect(method = "setupRotations", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isModelPartShown(Lnet/minecraft/world/entity/player/PlayerModelPart;)Z"))
	private boolean checkAustralians(Player player, PlayerModelPart part) {
		String deformattedReal = ChatFormatting.stripFormatting(player.getName().getString());
		boolean real = (deformattedReal.equals("Dinnerbone") || deformattedReal.equals("Grumm")); // if they're dinnerbone or grumm use normal
		return real && player.isModelPartShown(part)
				|| !real && Cosmetica.shouldRenderUpsideDown(player); // TODO should this also use player.isModelPartShown?
	}

	@Inject(
			method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z",
			at = @At("HEAD"),
			cancellable = true
	)
	private void shouldShowName(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		boolean thirdPerson = Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON;
		if (thirdPerson && Cosmetica.getConfig().shouldShowNametagInThirdPerson() && entity == Minecraft.getInstance().getCameraEntity()) cir.setReturnValue(Minecraft.renderNames());
	}
}
