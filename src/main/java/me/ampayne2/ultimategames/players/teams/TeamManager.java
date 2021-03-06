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
package me.ampayne2.ultimategames.players.teams;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Manages teams in arenas.
 */
public class TeamManager {
    private final UltimateGames ultimateGames;
    private Map<Arena, List<Team>> teams = new HashMap<Arena, List<Team>>();
    private static final Random RANDOM = new Random();

    /**
     * Creates a new TeamManager.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     */
    public TeamManager(UltimateGames ultimateGames) {
        this.ultimateGames = ultimateGames;
    }

    /**
     * Checks if a team of an arena with a certain name exists.
     *
     * @param arena The arena.
     * @param name  The name of the team.
     * @return True if the team exists, else null.
     */
    public boolean teamExists(Arena arena, String name) {
        if (teams.containsKey(arena)) {
            for (Team team : teams.get(arena)) {
                if (name.equalsIgnoreCase(team.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets a team of an arena with a certain name.
     *
     * @param arena The arena.
     * @param name  The name of the team.
     * @return The team if it exists, else null.
     */
    public Team getTeam(Arena arena, String name) {
        if (teams.containsKey(arena)) {
            for (Team team : teams.get(arena)) {
                if (name.equalsIgnoreCase(team.getName())) {
                    return team;
                }
            }
        }
        return null;
    }

    /**
     * Gets the teams of an arena.
     *
     * @param arena The arena.
     * @return The teams of the arena.
     */
    public List<Team> getTeamsOfArena(Arena arena) {
        return teams.containsKey(arena) ? teams.get(arena) : new ArrayList<Team>();
    }

    /**
     * Removes all the teams of an arena.
     *
     * @param arena The arena.
     */
    public void removeTeamsOfArena(Arena arena) {
        if (teams.containsKey(arena)) {
            List<Team> arenaTeams = teams.get(arena);
            for (Team team : arenaTeams) {
                team.removePlayers();
            }
            teams.remove(arena);
        }
    }

    /**
     * Adds a team to the manager.
     *
     * @param team The team to add.
     * @return True if the team was added successfully, else false.
     */
    public boolean addTeam(Team team) {
        Arena arena = team.getArena();
        List<Team> arenaTeams = teams.containsKey(arena) ? teams.get(arena) : new ArrayList<Team>();
        if (!arenaTeams.contains(team)) {
            arenaTeams.add(team);
            teams.put(arena, arenaTeams);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a team from the manager.
     *
     * @param team The team to remove.
     */
    public void removeTeam(Team team) {
        Arena arena = team.getArena();
        if (teams.containsKey(arena)) {
            List<Team> arenaTeams = teams.get(arena);
            if (arenaTeams.contains(team)) {
                team.removePlayers();
                arenaTeams.remove(team);
                if (arenaTeams.isEmpty()) {
                    teams.remove(arena);
                } else {
                    teams.put(arena, arenaTeams);
                }
            }
        }
    }

    /**
     * Checks if a player is in an arena team.
     *
     * @param playerName The player to check.
     * @return True if the player is in a team, else false.
     */
    public boolean isPlayerInTeam(String playerName) {
        Arena arena = ultimateGames.getPlayerManager().getPlayerArena(playerName);
        if (arena != null && teams.containsKey(arena)) {
            for (Team team : teams.get(arena)) {
                if (team.hasPlayer(playerName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets a player's team.
     *
     * @param playerName The player's name.
     * @return The player's team. Null if the player isn't in a team.
     */
    public Team getPlayerTeam(String playerName) {
        Arena arena = ultimateGames.getPlayerManager().getPlayerArena(playerName);
        Team playerTeam = null;
        if (arena != null && teams.containsKey(arena)) {
            for (Team team : teams.get(arena)) {
                if (team.hasPlayer(playerName)) {
                    playerTeam = team;
                }
            }
        }
        return playerTeam;
    }

    /**
     * Sets a player's team. Removes them from their current team if they are in one.<br>
     * Only sets the player's team if its arena is the same as the one the player is currently in.
     *
     * @param player The player.
     * @param team   The team to add the player to.
     * @return True if the player is in an arena and the player's arena is the same as the team's arena, else false.
     */
    public boolean setPlayerTeam(Player player, Team team) {
        String playerName = player.getName();
        if (ultimateGames.getPlayerManager().isPlayerInArena(playerName) && ultimateGames.getPlayerManager().getArenaPlayer(playerName).getArena().equals(team.getArena())) {
            Team oldTeam = getPlayerTeam(playerName);
            if (oldTeam != null) {
                oldTeam.removePlayer(playerName);
                ultimateGames.getMessenger().sendMessage(player, "teams.leave", oldTeam.getColor() + oldTeam.getName());
            }
            team.addPlayer(player);
            ultimateGames.getMessenger().sendMessage(player, "teams.join", team.getColor() + team.getName());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a player from their team if they are in one.
     *
     * @param playerName Name of the player to remove from their team.
     */
    public void removePlayerFromTeam(String playerName) {
        Team team = getPlayerTeam(playerName);
        if (team != null) {
            team.removePlayer(playerName);
        }
    }

    /**
     * Evenly sorts players in an arena into the arena's teams.<br>
     * Kicks players from the arena to stop uneven teams.<br>
     * Players who haven't joined a team yet are kicked before players who have.
     * TODO: Make the player's unlocked classes/tiers influence the sorting.
     *
     * @param arena The arena to sort the players of.
     */
    public void sortPlayersIntoTeams(Arena arena) {
        List<Team> arenaTeams = getTeamsOfArena(arena);
        List<String> players = arena.getPlayers();
        List<String> playersInTeams = new ArrayList<String>();
        List<String> playersNotInTeams = new ArrayList<String>(players);

        // Populate the players in teams and players not in teams lists
        for (Team team : arenaTeams) {
            for (String playerName : team.getPlayers()) {
                playersInTeams.add(playerName);
                playersNotInTeams.remove(playerName);
            }
        }

        // Don't sort if there are no teams
        int teamAmount = arenaTeams.size();
        if (teamAmount <= 0 || players.size() < teamAmount) {
            return;
        }

        // Kick the last player(s) to join from the game if the teams cannot be balanced
        while (players.size() % teamAmount != 0) {
            Player playerToKick = Bukkit.getPlayerExact(playersNotInTeams.size() > 0 ? playersNotInTeams.get(playersNotInTeams.size() - 1) : playersInTeams.get(playersInTeams.size() - 1));
            ultimateGames.getPlayerManager().removePlayerFromArena(playerToKick, false);
            ultimateGames.getMessenger().sendMessage(playerToKick, "arenas.kick");
            playersInTeams.remove(playerToKick.getName());
            playersNotInTeams.remove(playerToKick.getName());
            players = arena.getPlayers();
        }

        // Kick the last player(s) to join from their team if the teams cannot be balanced
        for (Team team : arenaTeams) {
            List<String> teamPlayers = team.getPlayers();
            while ((players.size() / teamAmount) < teamPlayers.size()) {
                String playerName = teamPlayers.get(teamPlayers.size() - 1);
                team.removePlayer(playerName);
                ultimateGames.getMessenger().sendMessage(Bukkit.getPlayerExact(playerName), "teams.kick", team.getColor() + team.getName());
                playersNotInTeams.add(playerName);
                teamPlayers = team.getPlayers();
            }
        }

        // Add the players not in teams yet to the teams that need players.
        for (Team team : arenaTeams) {
            while ((players.size() / teamAmount) > team.getPlayers().size()) {
                Player player = Bukkit.getPlayerExact(playersNotInTeams.get(RANDOM.nextInt(playersNotInTeams.size())));
                team.addPlayer(player);
                ultimateGames.getMessenger().sendMessage(player, "teams.join", team.getColor() + team.getName());
                playersNotInTeams.remove(player.getName());
            }
        }
    }
}
