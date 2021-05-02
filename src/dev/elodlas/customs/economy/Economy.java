package dev.elodlas.customs.economy;

import java.util.List;

public interface Economy {

    public boolean isEnabled();

    public String getName();

    public boolean hasBankSupport();

    public int fractionalDigits();

    public String format(double amount);

    public String currencyNamePlural();

    public String currencyNameSingular();

    public boolean hasAccount(String playerName);

    public boolean hasAccount(String playerName, String worldName);

    public double getBalance(String playerName);

    public double getBalance(String playerName, String world);

    public boolean has(String playerName, double amount);

    public boolean has(String playerName, String worldName, double amount);

    public EconomyResponse withdrawPlayer(String playerName, double amount);

    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount);

    public EconomyResponse depositPlayer(String playerName, double amount);

    public EconomyResponse depositPlayer(String playerName, String worldName, double amount);

    public EconomyResponse createBank(String name, String player);

    public EconomyResponse deleteBank(String name);

    public EconomyResponse bankBalance(String name);

    public EconomyResponse bankHas(String name, double amount);

    public EconomyResponse bankWithdraw(String name, double amount);

    public EconomyResponse bankDeposit(String name, double amount);

    public EconomyResponse isBankOwner(String name, String playerName);

    public EconomyResponse isBankMember(String name, String playerName);

    public List<String> getBanks();

    public boolean createPlayerAccount(String playerName);

    public boolean createPlayerAccount(String playerName, String worldName);
}