package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import  java.util.Map;
import  java.util.HashMap;
import javax.persistence.*;
import java.util.Date;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    public Date gameDate = new Date();

    public Game() {}
    public Game(int hours) {
        int seconds= hours*3600;
        this.gameDate = Date.from(gameDate.toInstant().plusSeconds(seconds));
    }



    public void setDate(int hours){
        int seconds = hours*3600;
        this.gameDate = Date.from(gameDate.toInstant().plusSeconds(seconds));
    }

    public Date getDate(){
        return this.gameDate;
    }

    public long getId(){
        return id;
    }

    public void setGamePlayers (Set<GamePlayer> gamePlayers){
        this.gamePlayers =gamePlayers;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    @JsonIgnore

    public     Map<String, Object> getJson() {
        Map<String, Object> gamesData= new HashMap<>();
            gamesData.put("id",getId());
            gamesData.put("created",getDate());
            gamesData.put("gamePlayers", getGamePlayers().stream().map(e -> e.getGamePlayersData()));


            return gamesData;

    }
}
