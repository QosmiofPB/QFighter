package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Player;
import org.qosmiof2.qfighter.data.Food;
import org.qosmiof2.qfighter.data.Npc;
import org.qosmiof2.qfighter.framework.Node;

public class Attack extends Node {

	private Food food;
	private Npc npc;

	public Attack(ClientContext ctx, Food food, Npc npc) {
		super(ctx);
		this.food = food;
		this.npc = npc;
	}

	@Override
	public boolean activate() {
		final org.powerbot.script.rt6.Npc npcToAttack = ctx.npcs.select()
				.id(npc.getId()).nearest().poll();
		Player player = ctx.players.local();
		return !player.inCombat()
				&& player.healthPercent() > 30
				&& player.animation() == -1
				&& ctx.movement.distance(npcToAttack.tile(), player.tile()) < 10;
	}

	@Override
	public void execute() {
		final org.powerbot.script.rt6.Npc npcToAttack = ctx.npcs.select()
				.id(npc.getId()).nearest().poll();

		if (npcToAttack.inViewport()) {
			if (npcToAttack.interact("Attack")) {
				// set status to "Attacking" when there will be paint.
			}
		} else {
			ctx.camera.turnTo(npcToAttack);
			ctx.camera.pitch(Random.nextInt(50, 99));
		}
	}

}
