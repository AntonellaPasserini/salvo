package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships;
    public Set<Ship> getShips() {  return ships; }

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvos;
    public Set<Salvo> getSalvoes() {  return salvos; }



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    private Date joinDate;

    public GamePlayer(){
        this.joinDate=new Date();
    }

    public GamePlayer ( int hours, Player player, Game game){
        int seconds = hours*3600;
        this.joinDate = Date.from(new Date().toInstant().plusSeconds(seconds));
        this.player=player;
        this.game=game;
    }


    public void setDate(int hours){
        int seconds = hours*3600;
        this.joinDate = Date.from(joinDate.toInstant().plusSeconds(seconds));
    }

    public Date getDate(){
        return this.joinDate;
    }

    public void setPlayer(Player player){
        this.player=player;
    }

    @JsonIgnore
    public Player getPlayer(){
        return player;
    }



    public void setGame(Game game){
        this.game=game;
    }

    @JsonIgnore
    public Game getGame(){
        return game;
    }

    public String getUserNames(){
        return player.getUserName();
    }

    public long getPlayerId(){
        return player.getId();
    }

    public long getGameId(){
        return game.getId();
    }

    public long getId(){
        return id;
    }




    public Map<String, Object> getPlayerDTO() {
        Map<String, Object> PlayerData= new HashMap<>();
        PlayerData.put("id",getPlayerId());
        PlayerData.put("email",getUserNames());



        return PlayerData;
}
    public Map<String, Object> getGamePlayersData() {
        Map<String, Object> gamesPlayerData = new HashMap<>();

        gamesPlayerData.put("id", this.getId());
        gamesPlayerData.put("player",this.getPlayerDTO());


        return gamesPlayerData;
    }

    public Map<String, Object> getGamePlayersShip(Ship ship) {
        Map<String, Object> gamesPlayerShip = new HashMap<>();

        gamesPlayerShip.put("type", ship.getShipType());
        gamesPlayerShip.put("shipLocations",ship.getLocations());


        return gamesPlayerShip;
    }
    public Map<String, Object> getSalvoesDto(Salvo salvoes) {
        Map<String, Object> salvoesDto = new HashMap<>();

        salvoesDto.put("turn", salvoes.GetTurnNumber());
        salvoesDto.put("player", salvoes.getGamePlayers().getPlayer().getId());
        salvoesDto.put("locations",salvoes.getLocations());


        return salvoesDto;
    }

    public Map<String, Object> getGameViewJson(){

        Map<String, Object> GameViewJson = new HashMap<>();
        GameViewJson.put("id",getGameId());
        GameViewJson.put("created",getDate());
        GameViewJson.put("gamePlayers", this.getGame().getGamePlayers().stream().map(gamePlayer -> gamePlayer.getGamePlayersData()).collect(Collectors.toList()) );
        GameViewJson.put("ships", this.getShips().stream().map(shipp -> getGamePlayersShip(shipp)).collect(Collectors.toList()));
        GameViewJson.put("salvoes",this.getGame().getGamePlayers()
                .stream()
                .flatMap(gp -> gp.getSalvoes()
                        .stream()
                        .sorted(Comparator.comparing(Salvo::GetTurnNumber))
                        .map(salvo -> this.getSalvoesDto(salvo))
                )
                .collect(Collectors.toList())
        );

        return GameViewJson;
    }


}
