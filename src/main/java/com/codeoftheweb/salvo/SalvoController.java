package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
     GameRepository repo;
    @Autowired
    GamePlayerRepositoy gpRepo;
    @Autowired
    PlayerRepository pRepo;
    @RequestMapping("/games")
    public List<Object> getGameData() {
        return repo.findAll().stream().map(e -> e.getJson()).collect(Collectors.toList());
    }
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping(path= "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame (Authentication authentication){
        if(isGuest(authentication)){
            return new ResponseEntity<>(createMap("error","You must login!"), HttpStatus.FORBIDDEN);
        }else{
            Game game = new Game(10);
            repo.save(game);
            Player ply = pRepo.findByuserName(authentication.getName());
            GamePlayer gPlayer = new GamePlayer(10, ply,game );
            gpRepo.save(gPlayer);
            return new ResponseEntity<>(createMap("gpid", gPlayer.getId()), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path= "/games/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame (Authentication authentication,
                                                           @PathVariable Long id){
        if(isGuest(authentication)){
            return new ResponseEntity<>(createMap("error","You must login!"), HttpStatus.UNAUTHORIZED);
        }
        Game gamecito = repo.findById(id).orElse(null);
        if(gamecito == null){
            return new ResponseEntity<>(createMap("error","Game doesn't exist"), HttpStatus.FORBIDDEN);
        }
        if(gamecito.getGamePlayers().size() >= 2){
            return new ResponseEntity<>(createMap("error","Game is full"), HttpStatus.FORBIDDEN);
        }
        Player ply = pRepo.findByuserName(authentication.getName());
        if(gamecito.getGamePlayers().stream().map(
                gamePlayer -> gamePlayer.getPlayer().getUserName()).collect(Collectors.toList()).contains(ply.getUserName())){
            return new ResponseEntity<>(createMap("error","You're already in"), HttpStatus.FORBIDDEN);
        }
        GamePlayer gPlayer = new GamePlayer(10, ply,gamecito);
        gpRepo.save(gPlayer);
        return new ResponseEntity<>(createMap("gpid", gPlayer.getId()), HttpStatus.CREATED);
    }
    public Map<String, Object> createMap (String str, Object obj){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(str, obj);
        return map;
    }

    @RequestMapping("/leaderboard")
    public List<Object> getLeaderboardList() {

        return pRepo.findAll().stream().map(e -> e.getLeaderBoardDTO()).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{nn}")
    public ResponseEntity<Map<String,Object>> getPlayerIdData(@PathVariable Long nn, Authentication authentication) {
        //GamePlayer gamePlayer = pRepo.findById(nn).get();
        if(isGuest(authentication)){
            return new ResponseEntity<>(createMap("error","You must login!"), HttpStatus.UNAUTHORIZED);
        }



        return new ResponseEntity<>(gpRepo.getOne(nn).getGameViewJson(),HttpStatus.ACCEPTED);
    }


}






