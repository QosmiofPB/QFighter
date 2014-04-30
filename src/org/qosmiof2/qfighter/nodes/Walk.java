package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.qfighter.data.Npc;
import org.qosmiof2.qfighter.framework.Node;

public class Walk extends Node {

	private Npc npc;

	public Walk(ClientContext ctx, Npc npc) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		final org.powerbot.script.rt6.Npc npcToAttack = ctx.npcs.select()
				.id(npc.getId()).nearest().poll();
		return ctx.movement.distance(npcToAttack.tile(), ctx.players.local()
				.tile()) >= 10
				&& ctx.players.local().animation() == -1
				&& !ctx.players.local().inMotion();
	}

	@Override
	public void execute() {
		final org.powerbot.script.rt6.Npc npcToAttack = ctx.npcs.select()
				.id(npc.getId()).nearest().poll();

		ctx.movement.step(npcToAttack.tile().derive(Random.nextInt(1, 5),
				Random.nextInt(1, 5)));

	}
}
