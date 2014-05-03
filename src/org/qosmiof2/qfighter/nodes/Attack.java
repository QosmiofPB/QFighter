package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Player;
import org.qosmiof2.qfighter.data.Food;
import org.qosmiof2.qfighter.framework.Node;

public class Attack extends Node {

	private Food food;
	public static String targetName;

	public Attack(ClientContext ctx, Food food) {
		super(ctx);
		this.food = food;
	}

	final Filter<Npc> npcFilter = new Filter<Npc>() {

		@Override
		public boolean accept(final Npc target) {
			if (target.name().equals(targetName) && !target.inCombat()) {
				return true;
			}
			return false;
		}

	};

	@Override
	public boolean activate() {

		final Npc target = ctx.npcs.select(npcFilter).nearest().first().poll();
		Player player = ctx.players.local();
		return !player.inCombat() && player.healthPercent() > 30
				&& player.animation() == -1
				&& ctx.movement.distance(target.tile(), player.tile()) < 10
				&& !ctx.backpack.select().id(food.getCookedId()).isEmpty();
	}

	@Override
	public void execute() {

		final Npc target = ctx.npcs.select().name(targetName).nearest().first()
				.poll();

		if (target.inViewport()) {
			if (target.interact("Attack")) {
				// set status to "Attacking" when there will be paint.
			}
		} else {
			ctx.camera.turnTo(target);
			ctx.camera.pitch(Random.nextInt(50, 99));
		}
	}

}