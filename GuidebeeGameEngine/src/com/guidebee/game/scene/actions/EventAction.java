/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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
package com.guidebee.game.scene.actions;

//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.engine.scene.Actor;
import com.guidebee.game.ui.Event;
import com.guidebee.game.ui.EventListener;
import com.guidebee.game.engine.utils.reflect.ClassReflection;

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 * Adds a listener to the actor for a specific event type and does not
 * complete until {@link #handle(Event)} returns true.
 *
 * @author JavadocMD
 * @author Nathan Sweet
 */
abstract public class EventAction<T extends Event> extends Action {
    final Class<? extends T> eventClass;
    boolean result, active;

    private final EventListener listener = new EventListener() {
        public boolean handle(Event event) {
            if (!active || !ClassReflection.isInstance(eventClass, event))
                return false;
            result = EventAction.this.handle((T) event);
            return result;
        }
    };

    public EventAction(Class<? extends T> eventClass) {
        this.eventClass = eventClass;
    }

    public void restart() {
        result = false;
        active = false;
    }

    public void setActor(Actor actor) {
        if (getActor() != null) getActor().removeListener(listener);
        super.setActor(actor);
        if (actor != null) actor.addListener(listener);
    }

    /**
     * Called when the specific type of event occurs on the actor.
     *
     * @return true if the event should be considered
     * {@link Event#handle() handled} and this EventAction considered complete.
     */
    abstract public boolean handle(T event);

    public boolean act(float delta) {
        active = true;
        return result;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}