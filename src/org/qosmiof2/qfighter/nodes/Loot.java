package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.Filter;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;
import org.qosmiof2.qfighter.framework.Node;

public class Loot extends Node {

	public static String name;

	public Loot(ClientContext ctx) {
		super(ctx);
	}

	Filter<GroundItem> filter = new Filter<GroundItem>() {

		@Override
		public boolean accept(GroundItem loot) {
			if (loot.name().equals(name)) {
				return true;
			}
			return false;
		}

	};

	@Override
	public boolean activate() {
		final GroundItem loot = ctx.groundItems.select(filter).nearest()
				.first().poll();
		return loot.valid()
				&& !ctx.players.local().inCombat()
				&& ctx.movement.distance(loot.tile(), ctx.players.local()
						.tile()) < 5;
	}

	@Override
	public void execute() {
		final GroundItem loot = ctx.groundItems.select(filter).nearest()
				.first().poll();

		if (loot.inViewport()) {
			if (loot.interact("Take")) {
				// set status to looting...
			}
		}

	}

}
