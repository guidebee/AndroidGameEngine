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
package com.guidebee.game.scene.datatrait;

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.entity.DataTrait;
import com.guidebee.game.entity.DataTraitType;
import com.guidebee.game.scene.Actor;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Data trait mapper.
 *
 * @param <T>
 */
public final class DataTraitMapper<T extends DataTrait> {

    private com.guidebee.game.entity.DataTraitMapper internalDataTraitMapper;
    private final DataTraitType dataTraitType;


    public static <T extends DataTrait> DataTraitMapper<T> getFor(Class<T> dataTraitClass) {
        return new DataTraitMapper<T>(dataTraitClass);
    }

    private DataTraitMapper(Class<T> dataTraitClass) {
        dataTraitType
                = DataTraitType.getFor(dataTraitClass);
        internalDataTraitMapper = com.guidebee.game.entity.DataTraitMapper.getFor(dataTraitClass);
    }

    /**
     * @return The {@link DataTrait} of the specified class belonging to entity.
     */
    public T get(Actor actor) {
        return (T) internalDataTraitMapper.get(actor.entity);
    }

    /**
     * @return Whether or not entity has the dataTrait of the specified class.
     */
    public boolean has(Actor actor) {
        return internalDataTraitMapper.has(actor.entity);
    }
}
