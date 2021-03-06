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
package me.ampayne2.ultimategames.message;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.utils.UGUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Organizes a list of strings into multiple pages.
 */
public class PageList {
    private final UltimateGames ultimateGames;
    private final String name;
    private final int messagesPerPage;
    private List<String> strings;

    /**
     * Creates a new PageList.
     *
     * @param ultimateGames   The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     * @param name            The name of the PageList.
     * @param strings         The list of strings in the PageList.
     * @param messagesPerPage The strings that should be displayed per page.
     */
    public PageList(UltimateGames ultimateGames, String name, List<String> strings, int messagesPerPage) {
        this.ultimateGames = ultimateGames;
        this.name = name;
        this.messagesPerPage = messagesPerPage;
        this.strings = strings;
    }

    /**
     * Creates a new PageList.
     *
     * @param ultimateGames   The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     * @param name            The name of the PageList.
     * @param messagesPerPage The strings that should be displayed per page.
     */
    public PageList(UltimateGames ultimateGames, String name, int messagesPerPage) {
        this.ultimateGames = ultimateGames;
        this.name = name;
        this.messagesPerPage = messagesPerPage;
        this.strings = new ArrayList<String>();
    }

    /**
     * Gets the amount of pages in the PageList.
     *
     * @return The amount of pages.
     */
    public int getPageAmount() {
        return (strings.size() + messagesPerPage - 1) / messagesPerPage;
    }

    /**
     * Sends a page of the PageList to a recipient.
     *
     * @param pageNumber The page number.
     * @param recipient  The recipient.
     */
    public void sendPage(int pageNumber, Object recipient) {
        pageNumber = UGUtils.clamp(pageNumber, 1, getPageAmount());
        Messenger messenger = ultimateGames.getMessenger();
        messenger.sendRawMessage(recipient, ChatColor.DARK_GRAY + "<-------<| " + ChatColor.AQUA + name + ": Page " + pageNumber + " " + ChatColor.DARK_GRAY + "|>------->");
        int startIndex = messagesPerPage * (pageNumber - 1);
        for (String string : strings.subList(startIndex, Math.min(startIndex + messagesPerPage, strings.size()))) {
            messenger.sendRawMessage(recipient, string);
        }
    }

    /**
     * Sets the strings of a page list.
     *
     * @param strings The strings.
     */
    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
