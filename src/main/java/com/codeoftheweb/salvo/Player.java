package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    private String userName;

    private String password;

    public Player() { }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password= password;
    }


    public String getUserName(){
        return userName;
    }

    public String getPassword() {return password;}

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
    public List<Player> getPlayer() {return gamePlayers.stream().map(sub -> sub.getPlayer()).collect(toList());}

    public long calcularWins(){

        return this.getScores().stream().filter(score -> score.getPuntaje()==1).count();
    }

    public long calcularTies(){

        return this.getScores().stream().filter(score -> score.getPuntaje()==0.5).count();
    }
    public long calcularLosses(){

        return this.getScores().stream().filter(score -> score.getPuntaje()==0).count();
    }


    public Map<String, Object> getScoresDTO() {
        Map<String, Object> scoresDTO = new HashMap<>();

        scoresDTO.put("totalScore",this.calcularLosses()*0+this.calcularTies()*0.5+this.calcularWins());
        scoresDTO.put("win",this.calcularWins());
        scoresDTO.put("tie",this.calcularTies());
        scoresDTO.put("losses",this.calcularLosses());

        return scoresDTO;

    }
    public Map<String, Object> getLeaderBoardDTO() {
        Map<String, Object> leaderBoardDTO = new HashMap<>();
        leaderBoardDTO.put("id", this.getId());
        leaderBoardDTO.put("player",this.getUserName());
        leaderBoardDTO.put("score",this.getScoresDTO());
        return leaderBoardDTO;


    }
    }

