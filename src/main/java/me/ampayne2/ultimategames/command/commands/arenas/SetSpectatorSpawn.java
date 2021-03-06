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
package me.ampayne2.ultimategames.command.commands.arenas;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.ArenaManager;
import me.ampayne2.ultimategames.command.UGCommand;
import me.ampayne2.ultimategames.message.Messenger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * A command that sets the spectator spawnpoint of an arena.
 */
public class SetSpectatorSpawn extends UGCommand {
    private final UltimateGames ultimateGames;

    /**
     * Creates the SetSpectatorSpawn command.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     */
    public SetSpectatorSpawn(UltimateGames ultimateGames) {
        super(ultimateGames, "setspectatorspawn", "Sets the spectator spawn of an arena.", "/ug arena setspectatorspawn <arena> <game>", new Permission("ultimategames.arena.setspectatorspawn", PermissionDefault.OP), 2, true);
        this.ultimateGames = ultimateGames;
    }

    @Override
    public void execute(String command, CommandSender sender, String[] args) {
        String arenaName = args[0];
        String gameName = args[1];
        Messenger messageManager = ultimateGames.getMessenger();
        ArenaManager arenaManager = ultimateGames.getArenaManager();
        if (!ultimateGames.getGameManager().gameExists(gameName)) {
            messageManager.sendMessage(sender, "games.doesntexist");
            return;
        } else if (!arenaManager.arenaExists(arenaName, gameName)) {
            messageManager.sendMessage(sender, "arenas.doesntexist");
            return;
        }
        ultimateGames.getSpawnpointManager().setSpectatorSpawnPoint(arenaManager.getArena(arenaName, gameName), ((Player) sender).getLocation());
        messageManager.sendMessage(sender, "spawnpoints.setspectatorspawnpoint", arenaName, gameName);
    }
}
