package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Player;
import org.qosmiof2.qfighter.data.Food;
import org.qosmiof2.qfighter.framework.Node;

public class Eat extends Node {

	private Food food;

	public Eat(ClientContext ctx, Food food) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		Player player = ctx.players.local();
		return player.healthPercent() <= 30;
	}

	@Override
	public void execute() {
		Item item = ctx.backpack.select().id(food.getCookedId()).shuffle()
				.poll();

		if (!item.valid()) {
			if (item.interact("Eat")) {

			}
		}

	}
}
