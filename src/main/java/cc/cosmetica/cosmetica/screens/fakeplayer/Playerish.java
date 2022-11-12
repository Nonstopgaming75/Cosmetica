/*
 * Copyright 2022 EyezahMC
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

package cc.cosmetica.cosmetica.screens.fakeplayer;

/**
 * Something that's... playerish. Could be a player, could be a fake player.
 * Methods are amazingly named to avoid conflicts.
 */
public interface Playerish {
	/**
	 * Get the player's tick count, which is kinda like a lifetime I guess.
	 */
	int getLifetime();

	/**
	 * Get something that could be an entity id. Then again, maybe it isn't.
	 */
	int getPseudoId();

	/**
	 * Whether the player is sneaking.
	 */
	boolean isSneaking();
}
