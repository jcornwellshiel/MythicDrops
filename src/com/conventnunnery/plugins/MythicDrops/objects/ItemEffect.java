/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.plugins.MythicDrops.objects;

import com.conventnunnery.plugins.MythicDrops.api.EffectAPI;

public class ItemEffect {

	private final String name;
	private final boolean enabled;
	private final int intensity;
	private final long duration;
	private final ItemEffectType itemEffectType;
	private final EffectAPI.BaseEffectType baseEffectType;
	private final EffectAPI.BaseEffectTarget baseEffectTarget;

	public ItemEffect(String name, boolean enabled, int intensity, long duration,
	                  ItemEffectType itemEffectType, EffectAPI.BaseEffectType baseEffectType,
	                  EffectAPI.BaseEffectTarget baseEffectTarget) {
		this.name = name;
		this.enabled = enabled;
		this.intensity = intensity;
		this.duration = duration;
		this.itemEffectType = itemEffectType;
		this.baseEffectType = baseEffectType;
		this.baseEffectTarget = baseEffectTarget;
	}

	public EffectAPI.BaseEffectType getBaseEffectType() {
		return baseEffectType;
	}

	public EffectAPI.BaseEffectTarget getBaseEffectTarget() {
		return baseEffectTarget;
	}

	public ItemEffectType getItemEffectType() {
		return itemEffectType;
	}

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getIntensity() {
		return intensity;
	}

	public long getDuration() {
		return duration;
	}

	public enum ItemEffectType {
		ARMOR, TOOL
	}
}
