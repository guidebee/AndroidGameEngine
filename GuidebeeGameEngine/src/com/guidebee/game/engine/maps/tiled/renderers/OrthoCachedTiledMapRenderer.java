/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//--------------------------------- PACKAGE ------------------------------------
package com.guidebee.game.engine.maps.tiled.renderers;

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine;
import com.guidebee.game.camera.OrthographicCamera;
import com.guidebee.game.engine.graphics.opengles.IGL20;
import com.guidebee.game.engine.maps.tiled.TiledMapRenderer;
import com.guidebee.game.graphics.*;
import com.guidebee.game.maps.MapLayer;
import com.guidebee.game.maps.MapLayers;
import com.guidebee.game.maps.MapObject;
import com.guidebee.game.maps.tiled.TiledMap;
import com.guidebee.game.maps.tiled.TiledMapTile;
import com.guidebee.game.maps.tiled.TiledMapTileLayer;
import com.guidebee.math.Matrix4;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.Disposable;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Renders ortho tiles by caching geometry on the GPU. How much is cached is
 * controlled by {@link #setOverCache(float)}. When the
 * view reaches the edge of the cached tiles, the cache is rebuilt at the new
 * view position.
 * <p/>
 * This class may have poor performance when tiles are often changed
 * dynamically, since the cache must be rebuilt after each
 * change.
 *
 * @author Justin Shapcott
 * @author Nathan Sweet
 */
public class OrthoCachedTiledMapRenderer implements TiledMapRenderer, Disposable {
    static private final float tolerance = 0.00001f;

    protected final TiledMap map;
    protected final SpriteCache spriteCache;
    protected final float[] vertices = new float[20];
    protected boolean blending;

    protected float unitScale;
    protected final Rectangle viewBounds = new Rectangle();
    protected final Rectangle cacheBounds = new Rectangle();

    protected float overCache = 0.50f;
    protected float maxTileWidth, maxTileHeight;
    protected boolean cached;
    protected int count;
    protected boolean canCacheMoreN, canCacheMoreE, canCacheMoreW, canCacheMoreS;

    /**
     * Creates a renderer with a unit scale of 1 and cache size of 2000.
     */
    public OrthoCachedTiledMapRenderer(TiledMap map) {
        this(map, 1, 2000);
    }

    /**
     * Creates a renderer with a cache size of 2000.
     */
    public OrthoCachedTiledMapRenderer(TiledMap map, float unitScale) {
        this(map, unitScale, 2000);
    }

    /**
     * @param cacheSize The maximum number of tiles that can be cached.
     */
    public OrthoCachedTiledMapRenderer(TiledMap map, float unitScale, int cacheSize) {
        this.map = map;
        this.unitScale = unitScale;
        spriteCache = new SpriteCache(cacheSize, true);
    }

    @Override
    public void setView(OrthographicCamera camera) {
        spriteCache.setProjectionMatrix(camera.combined);
        float width = camera.viewportWidth * camera.zoom
                + maxTileWidth * 2 * unitScale;
        float height = camera.viewportHeight * camera.zoom
                + maxTileHeight * 2 * unitScale;
        viewBounds.set(camera.position.x - width / 2,
                camera.position.y - height / 2, width, height);

        if ((canCacheMoreW && viewBounds.x < cacheBounds.x - tolerance) || //
                (canCacheMoreS && viewBounds.y < cacheBounds.y - tolerance) || //
                (canCacheMoreE
                        && viewBounds.x + viewBounds.width > cacheBounds.x
                        + cacheBounds.width + tolerance) || //
                (canCacheMoreN
                        && viewBounds.y + viewBounds.height > cacheBounds.y
                        + cacheBounds.height + tolerance) //
                ) cached = false;
    }

    @Override
    public void setView(Matrix4 projection, float x, float y, float width, float height) {
        spriteCache.setProjectionMatrix(projection);
        x -= maxTileWidth * unitScale;
        y -= maxTileHeight * unitScale;
        width += maxTileWidth * 2 * unitScale;
        height += maxTileHeight * 2 * unitScale;
        viewBounds.set(x, y, width, height);

        if ((canCacheMoreW && viewBounds.x < cacheBounds.x - tolerance) || //
                (canCacheMoreS && viewBounds.y < cacheBounds.y - tolerance) || //
                (canCacheMoreE
                        && viewBounds.x + viewBounds.width
                        > cacheBounds.x + cacheBounds.width + tolerance) || //
                (canCacheMoreN
                        && viewBounds.y + viewBounds.height
                        > cacheBounds.y + cacheBounds.height + tolerance) //
                ) cached = false;
    }

    @Override
    public void render() {
        if (!cached) {
            cached = true;
            count = 0;
            spriteCache.clear();

            final float extraWidth = viewBounds.width * overCache;
            final float extraHeight = viewBounds.height * overCache;
            cacheBounds.x = viewBounds.x - extraWidth;
            cacheBounds.y = viewBounds.y - extraHeight;
            cacheBounds.width = viewBounds.width + extraWidth * 2;
            cacheBounds.height = viewBounds.height + extraHeight * 2;

            for (MapLayer layer : map.getLayers()) {
                spriteCache.beginCache();
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                }
                spriteCache.endCache();
            }
        }

        if (blending) {
            GameEngine.gl.glEnable(IGL20.GL_BLEND);
            GameEngine.gl.glBlendFunc(IGL20.GL_SRC_ALPHA, IGL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        spriteCache.begin();
        MapLayers mapLayers = map.getLayers();
        for (int i = 0, j = mapLayers.getCount(); i < j; i++) {
            MapLayer layer = mapLayers.get(i);
            if (layer.isVisible()) {
                spriteCache.draw(i);
                for (MapObject object : layer.getObjects()) {
                    renderObject(object);
                }
            }
        }
        spriteCache.end();
        if (blending) GameEngine.gl.glDisable(IGL20.GL_BLEND);
    }

    @Override
    public void render(int[] layers) {
        if (!cached) {
            cached = true;
            count = 0;
            spriteCache.clear();

            final float extraWidth = viewBounds.width * overCache;
            final float extraHeight = viewBounds.height * overCache;
            cacheBounds.x = viewBounds.x - extraWidth;
            cacheBounds.y = viewBounds.y - extraHeight;
            cacheBounds.width = viewBounds.width + extraWidth * 2;
            cacheBounds.height = viewBounds.height + extraHeight * 2;

            for (MapLayer layer : map.getLayers()) {
                spriteCache.beginCache();
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                }
                spriteCache.endCache();
            }
        }

        if (blending) {
            GameEngine.gl.glEnable(IGL20.GL_BLEND);
            GameEngine.gl.glBlendFunc(IGL20.GL_SRC_ALPHA, IGL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        spriteCache.begin();
        MapLayers mapLayers = map.getLayers();
        for (int i : layers) {
            MapLayer layer = mapLayers.get(i);
            if (layer.isVisible()) {
                spriteCache.draw(i);
                for (MapObject object : layer.getObjects()) {
                    renderObject(object);
                }
            }
        }
        spriteCache.end();
        if (blending) GameEngine.gl.glDisable(IGL20.GL_BLEND);
    }

    @Override
    public void renderObject(MapObject object) {
    }

    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        final float color = Color.toFloatBits(1, 1, 1, layer.getOpacity());

        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * unitScale;
        final float layerTileHeight = layer.getTileHeight() * unitScale;

        final int col1 = Math.max(0, (int) (cacheBounds.x / layerTileWidth));
        final int col2 = Math.min(layerWidth,
                (int) ((cacheBounds.x + cacheBounds.width + layerTileWidth) / layerTileWidth));

        final int row1 = Math.max(0, (int) (cacheBounds.y / layerTileHeight));
        final int row2 = Math.min(layerHeight,
                (int) ((cacheBounds.y + cacheBounds.height + layerTileHeight) / layerTileHeight));

        canCacheMoreN = row2 < layerHeight;
        canCacheMoreE = col2 < layerWidth;
        canCacheMoreW = col1 > 0;
        canCacheMoreS = row1 > 0;

        float[] vertices = this.vertices;
        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col < col2; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) continue;

                final TiledMapTile tile = cell.getTile();
                if (tile == null) continue;

                count++;
                final boolean flipX = cell.getFlipHorizontally();
                final boolean flipY = cell.getFlipVertically();
                final int rotations = cell.getRotation();

                final TextureRegion region = tile.getTextureRegion();
                final Texture texture = region.getTexture();

                final float x1 = col * layerTileWidth + tile.getOffsetX() * unitScale;
                final float y1 = row * layerTileHeight + tile.getOffsetY() * unitScale;
                final float x2 = x1 + region.getRegionWidth() * unitScale;
                final float y2 = y1 + region.getRegionHeight() * unitScale;

                final float adjustX = 0.5f / texture.getWidth();
                final float adjustY = 0.5f / texture.getHeight();
                final float u1 = region.getU() + adjustX;
                final float v1 = region.getV2() - adjustY;
                final float u2 = region.getU2() - adjustX;
                final float v2 = region.getV() + adjustY;

                vertices[Batch.X1] = x1;
                vertices[Batch.Y1] = y1;
                vertices[Batch.C1] = color;
                vertices[Batch.U1] = u1;
                vertices[Batch.V1] = v1;

                vertices[Batch.X2] = x1;
                vertices[Batch.Y2] = y2;
                vertices[Batch.C2] = color;
                vertices[Batch.U2] = u1;
                vertices[Batch.V2] = v2;

                vertices[Batch.X3] = x2;
                vertices[Batch.Y3] = y2;
                vertices[Batch.C3] = color;
                vertices[Batch.U3] = u2;
                vertices[Batch.V3] = v2;

                vertices[Batch.X4] = x2;
                vertices[Batch.Y4] = y1;
                vertices[Batch.C4] = color;
                vertices[Batch.U4] = u2;
                vertices[Batch.V4] = v1;

                if (flipX) {
                    float temp = vertices[Batch.U1];
                    vertices[Batch.U1] = vertices[Batch.U3];
                    vertices[Batch.U3] = temp;
                    temp = vertices[Batch.U2];
                    vertices[Batch.U2] = vertices[Batch.U4];
                    vertices[Batch.U4] = temp;
                }
                if (flipY) {
                    float temp = vertices[Batch.V1];
                    vertices[Batch.V1] = vertices[Batch.V3];
                    vertices[Batch.V3] = temp;
                    temp = vertices[Batch.V2];
                    vertices[Batch.V2] = vertices[Batch.V4];
                    vertices[Batch.V4] = temp;
                }
                if (rotations != 0) {
                    switch (rotations) {
                        case TiledMapTileLayer.Cell.ROTATE_90: {
                            float tempV = vertices[Batch.V1];
                            vertices[Batch.V1] = vertices[Batch.V2];
                            vertices[Batch.V2] = vertices[Batch.V3];
                            vertices[Batch.V3] = vertices[Batch.V4];
                            vertices[Batch.V4] = tempV;

                            float tempU = vertices[Batch.U1];
                            vertices[Batch.U1] = vertices[Batch.U2];
                            vertices[Batch.U2] = vertices[Batch.U3];
                            vertices[Batch.U3] = vertices[Batch.U4];
                            vertices[Batch.U4] = tempU;
                            break;
                        }
                        case TiledMapTileLayer.Cell.ROTATE_180: {
                            float tempU = vertices[Batch.U1];
                            vertices[Batch.U1] = vertices[Batch.U3];
                            vertices[Batch.U3] = tempU;
                            tempU = vertices[Batch.U2];
                            vertices[Batch.U2] = vertices[Batch.U4];
                            vertices[Batch.U4] = tempU;
                            float tempV = vertices[Batch.V1];
                            vertices[Batch.V1] = vertices[Batch.V3];
                            vertices[Batch.V3] = tempV;
                            tempV = vertices[Batch.V2];
                            vertices[Batch.V2] = vertices[Batch.V4];
                            vertices[Batch.V4] = tempV;
                            break;
                        }
                        case TiledMapTileLayer.Cell.ROTATE_270: {
                            float tempV = vertices[Batch.V1];
                            vertices[Batch.V1] = vertices[Batch.V4];
                            vertices[Batch.V4] = vertices[Batch.V3];
                            vertices[Batch.V3] = vertices[Batch.V2];
                            vertices[Batch.V2] = tempV;

                            float tempU = vertices[Batch.U1];
                            vertices[Batch.U1] = vertices[Batch.U4];
                            vertices[Batch.U4] = vertices[Batch.U3];
                            vertices[Batch.U3] = vertices[Batch.U2];
                            vertices[Batch.U2] = tempU;
                            break;
                        }
                    }
                }
                spriteCache.add(texture, vertices, 0, 20);
            }
        }
    }

    /**
     * Causes the cache to be rebuilt the next time it is rendered.
     */
    public void invalidateCache() {
        cached = false;
    }

    /**
     * Returns true if tiles are currently cached.
     */
    public boolean isCached() {
        return cached;
    }

    /**
     * Sets the percentage of the view that is cached in each direction. Default is 0.5.
     * <p/>
     * Eg, 0.75 will cache 75% of the width of the view to the left and right of the view, and 75% of the height of the view above
     * and below the view.
     */
    public void setOverCache(float overCache) {
        this.overCache = overCache;
    }

    /**
     * Expands the view size in each direction, ensuring that tiles of this size or smaller are never culled from the visible
     * portion of the view. Default is 0,0.
     * <p/>
     * The amount of tiles cached is computed using <code>(view size + max tile size) * overCache</code>, meaning the max tile size
     * increases the amount cached and possibly {@link #setOverCache(float)} can be reduced.
     * <p/>
     * If the view size and {@link #setOverCache(float)} are configured so the size of the cached tiles is always larger than the
     * largest tile size, this setting is not needed.
     */
    public void setMaxTileSize(float maxPixelWidth, float maxPixelHeight) {
        this.maxTileWidth = maxPixelWidth;
        this.maxTileHeight = maxPixelHeight;
    }

    public void setBlending(boolean blending) {
        this.blending = blending;
    }

    public SpriteCache getSpriteCache() {
        return spriteCache;
    }

    @Override
    public void dispose() {
        spriteCache.dispose();
    }
}
