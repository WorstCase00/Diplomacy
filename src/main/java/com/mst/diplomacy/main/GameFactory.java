package com.mst.diplomacy.main;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mst.diplomacy.model.definition.Game;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.World;

import java.util.Map;
import java.util.Set;

public class GameFactory {
    
    public World createStandard() {
        Place bre = Place.createCapital("Brest");
        Place par = Place.createCapital("Paris");
        Place mar = Place.createCapital("Marseilles");
        Place ven = Place.createCapital("Venezia");
        Place rom = Place.createCapital("Roma");
        Place nap = Place.createCapital("Napoli");
        Place tri = Place.createCapital("Trieste");
        Place bud = Place.createCapital("Budapest");
        Place vie = Place.createCapital("Vienna");
        Place con = Place.createCapital("Constantinople");
        Place smy = Place.createCapital("Smyrna");
        Place ank = Place.createCapital("Ankara");
        Place sev = Place.createCapital("Sevastopol");
        Place mos = Place.createCapital("Moscow");
        Place war = Place.createCapital("Warsaw");
        Place stp = Place.createCapital("Saint Petersburg");
        Place mun = Place.createCapital("Munich");
        Place ber = Place.createCapital("Berlin");
        Place kie = Place.createCapital("Kiel");
        Place lon = Place.createCapital("London");
        Place lpl = Place.createCapital("Liverpool");
        Place edi = Place.createCapital("Edinburgh");
        Place por = Place.createCapital("Portugal");
        Place spa = Place.createCapital("Spain");
        Place ser = Place.createCapital("Serbia");
        Place gre = Place.createCapital("Greece");
        Place bul = Place.createCapital("Bulgaria");
        Place rum = Place.createCapital("Rumania");
        Place nwy = Place.createCapital("Norway");
        Place den = Place.createCapital("Denmark");
        Place swe = Place.createCapital("Sweden");
        Place tun = Place.createCapital("Tunis");
        Place bel = Place.createCapital("Belgium");
        Place hol = Place.createCapital("HolPlace");

        // Seas
        Place nat = Place.createSea("North Atlantic Sea");
        Place nrg = Place.createSea("Norwegian Sea");
        Place bar = Place.createSea("Barents Sea");
        Place iri = Place.createSea("Irish Sea");
        Place nth = Place.createSea("North Sea");
        Place eng = Place.createSea("English Channel");
        Place ska = Place.createSea("Skagerrak Strait");
        Place hel = Place.createSea("HelgoPlace Bight");
        Place bal = Place.createSea("Baltic Sea");
        Place bot = Place.createSea("Gulf of Bothnia");
        Place mid = Place.createSea("Mid Atlantic Sea");
        Place wes = Place.createSea("West Mediterranean Sea");
        Place lyo = Place.createSea("Gulf of Lyon");
        Place tyn = Place.createSea("Tyrrhenian Sea");
        Place adr = Place.createSea("Adriatic Sea");
        Place ion = Place.createSea("Ionian Sea");
        Place aeg = Place.createSea("Aegean Sea");
        Place eas = Place.createSea("East Mediterranean Sea");
        Place bla = Place.createSea("Black Sea");

        // Other Places
        Place cly = Place.createLand("Clyde");
        Place wal = Place.createLand("Wales");
        Place yor = Place.createLand("York");
        Place fin = Place.createLand("FinPlace");
        Place lvn = Place.createLand("Livonia");
        Place ukr = Place.createLand("Ukraine");
        Place pru = Place.createLand("Prussia");
        Place sil = Place.createLand("Silesia");
        Place gal = Place.createLand("Galicia");
        Place boh = Place.createLand("Bohemia");
        Place ruh = Place.createLand("Ruhr");
        Place pic = Place.createLand("Picardy");
        Place bur = Place.createLand("Burgundy");
        Place gas = Place.createLand("Gascony");
        Place pie = Place.createLand("Piedmont");
        Place app = Place.createLand("Apulia");
        Place tus = Place.createLand("Tuscany");
        Place naf = Place.createLand("North Africa");
        Place arm = Place.createLand("Armenia");
        Place syr = Place.createLand("Syria");
        Place alb = Place.createLand("Albania");
        Place trl = Place.createLand("Tyrolia");

        // Adds adjacents to all places

        Map<Place, Set<Place>> adjacentPlaces = Maps.newHashMap();
        
        adjacentPlaces.put(nat, Sets.newHashSet(mid, iri, lpl, cly, nrg));
        adjacentPlaces.put(nrg, Sets.newHashSet(cly, edi, nth, nwy, bar));
        adjacentPlaces.put(bar, Sets.newHashSet(stp, nwy));
        adjacentPlaces.put(iri, Sets.newHashSet(mid, eng, wal, lpl));
        adjacentPlaces.put(eng, Sets.newHashSet(mid, wal, lon, bre, pic, bel, nth));
        adjacentPlaces.put(nth, Sets.newHashSet(edi, yor, lon, bel, hol, hel, den, ska, nwy));
        adjacentPlaces.put(hel, Sets.newHashSet(hol, den, kie));
        adjacentPlaces.put(ska, Sets.newHashSet(nwy, swe, den, bal));
        adjacentPlaces.put(bal, Sets.newHashSet(swe, den, kie, ber, pru, lvn, bot));
        adjacentPlaces.put(bot, Sets.newHashSet(swe, fin, stp, lvn));
        adjacentPlaces.put(mid, Sets.newHashSet(bre, gas, spa, por, naf, wes));
        adjacentPlaces.put(wes, Sets.newHashSet(naf, spa, lyo, tyn, tun));
        adjacentPlaces.put(lyo, Sets.newHashSet(spa, mar, pie, tus, tyn));
        adjacentPlaces.put(tyn, Sets.newHashSet(tun, ion, nap, rom, tus));
        adjacentPlaces.put(ion, Sets.newHashSet(tun, nap, app, adr, alb, gre, aeg, eas));
        adjacentPlaces.put(adr, Sets.newHashSet(app, ven, tri, alb));
        adjacentPlaces.put(aeg, Sets.newHashSet(gre, smy, bul, con, eas));
        adjacentPlaces.put(eas, Sets.newHashSet(smy, syr));
        adjacentPlaces.put(bla, Sets.newHashSet(sev, rum, bul, con, ank, arm));
        adjacentPlaces.put(cly, Sets.newHashSet(edi, lpl));
        adjacentPlaces.put(edi, Sets.newHashSet(cly, lpl, yor));
        adjacentPlaces.put(lpl, Sets.newHashSet(cly, edi, yor, wal));
        adjacentPlaces.put(wal, Sets.newHashSet(lpl, yor, lon));
        adjacentPlaces.put(lon, Sets.newHashSet(wal, yor));
        adjacentPlaces.put(nwy, Sets.newHashSet(swe, fin, stp));
        adjacentPlaces.put(swe, Sets.newHashSet(nwy, den, fin));
        adjacentPlaces.put(fin, Sets.newHashSet(nwy, swe, stp));
        adjacentPlaces.put(stp, Sets.newHashSet(nwy, fin, lvn, mos));
        adjacentPlaces.put(mos, Sets.newHashSet(stp, lvn, war, ukr, sev));
        adjacentPlaces.put(lvn, Sets.newHashSet(stp, mos, war, pru));
        adjacentPlaces.put(war, Sets.newHashSet(mos, lvn, pru, sil, gal, ukr));
        adjacentPlaces.put(ukr, Sets.newHashSet(mos, war, gal, rum, sev));
        adjacentPlaces.put(sev, Sets.newHashSet(mos, ukr, rum, arm));
        adjacentPlaces.put(arm, Sets.newHashSet(sev, ank, syr, smy));
        adjacentPlaces.put(syr, Sets.newHashSet(arm, smy));
        adjacentPlaces.put(smy, Sets.newHashSet(con, ank, arm, syr));
        adjacentPlaces.put(ank, Sets.newHashSet(arm, smy, con));
        adjacentPlaces.put(con, Sets.newHashSet(ank, smy, bul));
        adjacentPlaces.put(gre, Sets.newHashSet(alb, ser, bul));
        adjacentPlaces.put(bul, Sets.newHashSet(gre, con, rum, ser));
        adjacentPlaces.put(rum, Sets.newHashSet(sev, ukr, gal, bud, ser, bul));
        adjacentPlaces.put(ser, Sets.newHashSet(rum, bul, gre, alb, tri, bud));
        adjacentPlaces.put(alb, Sets.newHashSet(gre, ser, tri));
        adjacentPlaces.put(tri, Sets.newHashSet(ven, trl, vie, bud, ser, alb));
        adjacentPlaces.put(bud, Sets.newHashSet(vie, tri, ser, rum, gal));
        adjacentPlaces.put(gal, Sets.newHashSet(rum, bud, vie, boh, sil, war, ukr));
        adjacentPlaces.put(vie, Sets.newHashSet(boh, gal, bud, tri, trl));
        adjacentPlaces.put(trl, Sets.newHashSet(ven, tri, vie, boh, mun));
        adjacentPlaces.put(ven, Sets.newHashSet(tri, trl, pie, tus, rom, app));
        adjacentPlaces.put(nap, Sets.newHashSet(app, rom));
        adjacentPlaces.put(rom, Sets.newHashSet(nap, app, ven, tus));
        adjacentPlaces.put(tus, Sets.newHashSet(rom, ven, pie));
        adjacentPlaces.put(pie, Sets.newHashSet(tus, ven, mar));
        adjacentPlaces.put(tun, Sets.newHashSet(naf));
        adjacentPlaces.put(naf, Sets.newHashSet(tun));
        adjacentPlaces.put(pru, Sets.newHashSet(ber, sil, war, lvn));
        adjacentPlaces.put(sil, Sets.newHashSet(ber, mun, boh, gal, war, pru));
        adjacentPlaces.put(boh, Sets.newHashSet(sil, mun, trl, vie, gal));
        adjacentPlaces.put(mun, Sets.newHashSet(trl, boh, sil, ber, kie, ruh, bur));
        adjacentPlaces.put(ber, Sets.newHashSet(kie, mun, sil, pru));
        adjacentPlaces.put(kie, Sets.newHashSet(den, hol, ruh, mun, ber));
        adjacentPlaces.put(den, Sets.newHashSet(swe, kie));
        adjacentPlaces.put(ruh, Sets.newHashSet(kie, mun, bur, bel, hol));
        adjacentPlaces.put(hol, Sets.newHashSet(kie, ruh, bel));
        adjacentPlaces.put(bel, Sets.newHashSet(hol, ruh, bur, pic));
        adjacentPlaces.put(bur, Sets.newHashSet(mun, ruh, bel, pic, par, gas, mar));
        adjacentPlaces.put(pic, Sets.newHashSet(bel, bur, par, bre));
        adjacentPlaces.put(par, Sets.newHashSet(bre, pic, bur, gas));
        adjacentPlaces.put(bre, Sets.newHashSet(pic, par, gas));
        adjacentPlaces.put(gas, Sets.newHashSet(bre, par, bur, mar, spa));
        adjacentPlaces.put(mar, Sets.newHashSet(bur, gas, spa, pie));
        adjacentPlaces.put(spa, Sets.newHashSet(por, gas, mar));
        adjacentPlaces.put(por, Sets.newHashSet(spa));

        return World.create(adjacentPlaces);
    }
    
    public Game createWestMedGame() {
        World theater = createWestMedTheater();
        return new Game(theater, createWestMedPlayers(theater));
    }
    
    public Set<Player> createWestMedPlayers(World world) {
        return Sets.newHashSet(
                new Player(
                        "France", 
                        "blue", 
                        world.getPlaces(Sets.newHashSet("Paris", "Picardy", "Brest", "Burgundy", "Gascony", "Marseilles")), 
                        world.getPlaces(Sets.newHashSet("Paris", "Marseilles")), 
                        world.getPlaces(Sets.newHashSet("Brest"))),
                new Player(
                        "Italy", 
                        "green", 
                        world.getPlaces(Sets.newHashSet("Piedmont", "Venezia", "Tuscany", "Apulia", "Roma", "Napoli")),
                        world.getPlaces(Sets.newHashSet("Venezia", "Roma")),
                        world.getPlaces(Sets.newHashSet("Napoli"))));
    }
    
    public World createWestMedTheater() {
        // Capital Cities
        Place bre = Place.createCapital("Brest");
        Place par = Place.createCapital("Paris");
        Place mar = Place.createCapital("Marseilles");
        Place ven = Place.createCapital("Venezia");
        Place rom = Place.createCapital("Roma");
        Place nap = Place.createCapital("Napoli");
        Place por = Place.createCapital("Portugal");
        Place spa = Place.createCapital("Spain");
        Place tun = Place.createCapital("Tunis");

        // Seas
        Place eng = Place.createSea("English Channel");
        Place mid = Place.createSea("Mid Atlantic Sea");
        Place wes = Place.createSea("West Mediterranean Sea");
        Place lyo = Place.createSea("Gulf of Lyon");
        Place tyn = Place.createSea("Tyrrhenian Sea");
        Place adr = Place.createSea("Adriatic Sea");
        Place ion = Place.createSea("Ionian Sea");

        // Other Places
        Place pic = Place.createLand("Picardy");
        Place bur = Place.createLand("Burgundy");
        Place gas = Place.createLand("Gascony");
        Place pie = Place.createLand("Piedmont");
        Place app = Place.createLand("Apulia");
        Place tus = Place.createLand("Tuscany");
        Place naf = Place.createLand("North Africa");

        // Adds adjacents to all places

        Map<Place, Set<Place>> adjacentPlaces = Maps.newHashMap();
        adjacentPlaces.put(eng, Sets.newHashSet(mid, bre, pic));
        adjacentPlaces.put(mid, Sets.newHashSet(bre, gas, spa, por, naf, wes));
        adjacentPlaces.put(wes, Sets.newHashSet(naf, spa, lyo, tyn, tun));
        adjacentPlaces.put(lyo, Sets.newHashSet(spa, mar, pie, tus, tyn));
        adjacentPlaces.put(tyn, Sets.newHashSet(tun, ion, nap, rom, tus));
        adjacentPlaces.put(ion, Sets.newHashSet(tun, nap, app, adr));
        adjacentPlaces.put(adr, Sets.newHashSet(app, ven));
        adjacentPlaces.put(ven, Sets.newHashSet(pie, tus, rom, app));
        adjacentPlaces.put(app, Sets.newHashSet(ven, rom, nap));
        adjacentPlaces.put(nap, Sets.newHashSet(app, rom));
        adjacentPlaces.put(rom, Sets.newHashSet(nap, app, ven, tus));
        adjacentPlaces.put(tus, Sets.newHashSet(rom, ven, pie));
        adjacentPlaces.put(pie, Sets.newHashSet(tus, ven, mar));
        adjacentPlaces.put(naf, Sets.newHashSet(tun));
        adjacentPlaces.put(bur, Sets.newHashSet(pic, par, gas, mar));
        adjacentPlaces.put(pic, Sets.newHashSet(bur, par, bre));
        adjacentPlaces.put(par, Sets.newHashSet(bre, pic, bur, gas));
        adjacentPlaces.put(bre, Sets.newHashSet(pic, par, gas));
        adjacentPlaces.put(gas, Sets.newHashSet(bre, par, bur, mar, spa));
        adjacentPlaces.put(mar, Sets.newHashSet(bur, gas, spa, pie));
        adjacentPlaces.put(spa, Sets.newHashSet(por, gas, mar));
        adjacentPlaces.put(por, Sets.newHashSet(spa));

        return World.create(adjacentPlaces);
    }
}
