/*
 * This file is part of UltimateGames.
 *
 * Copyright (c) 2013-2013, UltimateGames <http://github.com/ampayne2/>
 *
 * UltimateGames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * UltimateGames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with UltimateGames.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ampayne2.ultimategames.chests;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.Arena;
import org.bukkit.block.Chest;

/**
 * A type of UGChest that contains static contents.
 */
public class StaticChest extends UGChest {
    @SuppressWarnings("unused")
    private final UltimateGames ultimateGames;

    /**
     * Creates a new StaticChest.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     * @param chest         The chest.
     * @param arena         The arena.
     * @param label         The label.
     */
    public StaticChest(UltimateGames ultimateGames, Chest chest, Arena arena, String label) {
        super(chest, arena, ChestType.STATIC, label);
        this.ultimateGames = ultimateGames;
    }

    /**
     * Resets the contents of the chest.
     */
    @Override
    public void reset() {
        // TODO: Resetting static chests
    }
}
