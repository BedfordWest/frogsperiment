package com.frogsperiment.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.frogsperiment.assets.Assets;
import com.frogsperiment.objects.Bot;
import com.frogsperiment.objects.Creep;
import com.frogsperiment.objects.Player;
import com.frogsperiment.objects.Terrain;
import com.frogsperiment.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bedford on 9/2/15.
 */
public class Level {

    public static final String TAG = Level.class.getName();
    // Store all the bots in the level in a list
    private List<Bot> bots = new ArrayList<Bot>();
    // Need to store the roaming creatures, also
    private List<Creep> creeps = new ArrayList<Creep>();
    // All of the terrain features need to be in a list, as well
    private List<Terrain> terrain = new ArrayList<Terrain>();

    private MapGenerator mapGen = new MapGenerator();
    private TiledMap map;
    private Player player;


    private float enemyChance = 0.03f;

    public Level () {
        Gdx.app.debug(TAG, "Seeding the level (no provided seed)...");
        Random rand = new Random();
        init(rand.nextLong());
    }

    public Level (long seed) {
        Gdx.app.debug(TAG, "Seeding the level with seed: " + seed);
        init(seed);
    }

    private void createTileObjects(long seed) {
        int[][] newMap = this.mapGen.getMap(
                seed,
                Constants.LEVEL_X_TILES,
                Constants.LEVEL_Y_TILES
        );

        for (int x = 0; x < Constants.LEVEL_X_TILES; x++) {
            for (int y = 0; y < Constants.LEVEL_Y_TILES; y++) {
                LevelTile ltile = new LevelTile();
                ltile.setCellPosition(x,y);
                ltile.setPosition(
                        (x * Constants.TILE_WIDTH/Constants.WORLD_SCALE) +
                                ltile.getDimension().x/2,
                        y * Constants.TILE_HEIGHT/Constants.WORLD_SCALE +
                                ltile.getDimension().y/2
                );
                if (newMap[x][y] == 1) {
                    ltile.setSolid(true);
                }
                else {
                    ltile.setSolid(false);
                }
                levelTiles.add(ltile);
            }
        }
    }

    private void init (long seed) {
        player = new Player();
        boolean isPlayerSet = false;
        this.createTileObjects(seed);
        map = new TiledMap();
        map.getLayers().add(new TiledMapTileLayer(
                Constants.LEVEL_X_TILES,
                Constants.LEVEL_Y_TILES,
                Constants.TILE_WIDTH,
                Constants.TILE_HEIGHT));

        for ( LevelTile tile : levelTiles) {
            if (!tile.isSolid()) {
                if (!isPlayerSet) {
                    player.setPosition(tile.getPosition().x,
                            tile.getPosition().y);
                    isPlayerSet = true;
                }
                else {
                    Random enemyRoll = new Random();
                    if(enemyRoll.nextFloat() < enemyChance) {
                        int typeRoll = enemyRoll.nextInt(3);
                        Constants.EnemyType eType;
                        switch (typeRoll) {
                            case 0:
                                eType = Constants.EnemyType.STANDARD;
                                break;
                            case 1:
                                eType = Constants.EnemyType.FLYER;
                                break;
                            case 2:
                                eType = Constants.EnemyType.DEFENDER;
                                break;
                            default:
                                eType = Constants.EnemyType.STANDARD;
                        }
                        Enemy newEnemy = new Enemy(eType);
                        newEnemy.setPosition(tile.getPosition().x,
                                tile.getPosition().y);
                        enemies.add(newEnemy);
                    }
                }
            }
        }
        updatePlayerLOS();
    }

    // Add a map layer to only represent what can be seen in a radius of sight
    public void updatePlayerLOS() {
        float sightDistance = player.getSightRadius();
        TiledMapTileLayer losLayer = (TiledMapTileLayer)map.getLayers().get(0);

        for (LevelTile tile : levelTiles) {

            if(player.getPosition().dst(tile.getPosition()) < sightDistance)
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (!tile.isSolid())
                {
                    cell.setTile(new StaticTiledMapTile
                            (Assets.instance.darkness.darkness));
                } else if (tile.isSolid())
                {
                    cell.setTile(new StaticTiledMapTile
                            (Assets.instance.wall.wall));
                }
                losLayer.setCell(
                        tile.getCellXPosition(),
                        tile.getCellYPosition(),
                        cell
                );
            }
            else {
                losLayer.setCell(
                        tile.getCellXPosition(),
                        tile.getCellYPosition(),
                        null
                );
            }
        }

        for (Enemy enemy : enemies) {
            if(player.getPosition().dst(enemy.getPosition()) < sightDistance)
            {
                enemy.setInSight(true);
            }
            else {
                enemy.setInSight(false);
            }
        }
    }

    // Getters
    public TiledMap getTiledMap() {
        return this.map;
    }
    public Player getPlayer() { return this.player; }
    public List<LevelTile> getLevelTiles() { return this.levelTiles; }
    public List<Enemy> getEnemies() { return this.enemies; }
    public LevelTile getTileAtPosition(Vector2 position) {
        int index;
        index = (int) (((position.x/ Constants.TILE_WIDTH)
                * Constants.LEVEL_Y_TILES) + position.y/Constants.TILE_HEIGHT);
        return levelTiles.get(index);
    }


}
