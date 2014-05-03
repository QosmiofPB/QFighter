package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import org.qosmiof2.qfighter.framework.Node;

public class Walk extends Node {

	public Walk(ClientContext ctx) {
		super(ctx);
	}

	final Filter<Npc> npcFilter = new Filter<Npc>() {

		@Override
		public boolean accept(final Npc target) {
			if (target.name().equals(Attack.targetName) && !target.inCombat()) {
				return true;
			}
			return false;
		}

	};

	@Override
	public boolean activate() {
		final Npc npcToAttack = ctx.npcs.select(npcFilter).nearest().poll();
		return ctx.movement.distance(npcToAttack.tile(), ctx.players.local()
				.tile()) >= 10
				&& ctx.players.local().animation() == -1
				&& !ctx.players.local().inMotion();
	}

	@Override
	public void execute() {
		final Npc npcToAttack = ctx.npcs.select(npcFilter).nearest().poll();

		ctx.movement.step(npcToAttack.tile().derive(Random.nextInt(1, 5),
				Random.nextInt(1, 5)));

	}
}
